package com.example.mini_project_task_manager.service.impl;

import com.example.mini_project_task_manager.common.enums.Priority;
import com.example.mini_project_task_manager.common.enums.Status;
import com.example.mini_project_task_manager.common.utils.DateUtils;
import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.task.request.TaskRequest;
import com.example.mini_project_task_manager.dto.task.response.TaskResponse;
import com.example.mini_project_task_manager.entity.*;
import com.example.mini_project_task_manager.repository.*;
import com.example.mini_project_task_manager.security.UserPrincipal;
import com.example.mini_project_task_manager.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TagRepository tagRepository;
    private final CommentRepository commentsRepository;

    @Override
    @Transactional
    public ResponseDto<TaskResponse.TaskDetailResponse> createTask(
            UserPrincipal principal, Long projectId, TaskRequest.@Valid TaskCreateRequest dto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 project를 찾을 수 없습니다."));
        User user = userRepository.findById(principal.getId())
                .orElseThrow(() -> new EntityNotFoundException("로그인 사용자를 찾을 수 없습니다."));
        Task task = Task.createTask(
                dto.title().trim(),
                dto.content().trim(),
                user,
                dto.status(),
                dto.priority(),
                dto.dueDate());
        project.addTask(task);

        /** 기존 프로젝트 태그 불러오기 */
        List<Tag> existingTags = tagRepository.findTagsByProjectId(projectId);

        /** DTO에서 넘어온 태그 처리 하기 */
        if (dto.tagNames() != null && !dto.tagNames().isEmpty()) {
            for (String tagName : dto.tagNames()) {
                if (tagName == null || tagName.isBlank()) continue;
                String trimmedName = tagName.replaceAll("\\s+", "");
                Tag tag = existingTags.stream()
                        .filter(t -> t.getTagName().equals(trimmedName))
                        .findFirst()
                        .orElseGet(() -> {
                            Tag newTag = new Tag(trimmedName);
                            newTag.setProject(project);
                            return tagRepository.save(newTag);
                        });
                task.addTag(tag);
            }
        }
        Task saved = taskRepository.save(task);
        return ResponseDto.setSuccess("SUCCESS", TaskResponse.TaskDetailResponse.from(saved));
    }

    @Override
    public ResponseDto<List<TaskResponse.TaskListResponse>> getAllTasks(
            Long projectId, Status status, Priority priority, LocalDateTime from, LocalDateTime to, LocalDate dueFrom, LocalDate dueTo) {
        LocalDateTime fromUtc = DateUtils.kstToUtc(from);
        LocalDateTime toUtc = DateUtils.kstToUtc(to);
        List<Task> tasks;

        if (status != null || priority != null || from != null || to != null
                || dueFrom != null || dueTo != null) {
            tasks = taskRepository.searchTasks(projectId, status, priority, fromUtc, toUtc, dueFrom, dueTo);
            if (tasks == null || tasks.isEmpty()) {
                throw new EntityNotFoundException("원하는 조건에 맞는 Task를 찾을 수 없습니다.");
            }
        }
        else {
            tasks = taskRepository.findTasksByProjectId(projectId);
            if (tasks == null || tasks.isEmpty()) {
                throw new EntityNotFoundException("해당 projectId의 Task를 찾을 수 없습니다.");
            }
        }
        List<TaskResponse.TaskListResponse> result = tasks.stream()
                .map(TaskResponse.TaskListResponse::from)
                .toList();
        return ResponseDto.setSuccess("SUCCESS", result);
    }

    @Override
    public ResponseDto<TaskResponse.TaskDetailResponse> getTaskById(Long projectId, Long taskId) {
        Task task = taskRepository.findByIdWithTaskTags(projectId, taskId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 Task를 찾을 수 없습니다."));
        List<Comment> comments = commentsRepository.findByTaskId(taskId);
        for (Comment comment : comments) {
            task.addComment(comment);
        }
        return ResponseDto.setSuccess("SUCCESS", TaskResponse.TaskDetailResponse.from(task));
    }

    @Override
    @Transactional
    public ResponseDto<TaskResponse.TaskDetailResponse> updateTask(
            UserPrincipal principal, Long projectId, Long taskId, TaskRequest.@Valid TaskUpdateRequest dto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 project를 찾을 수 없습니다."));
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 Task를 찾을 수 없습니다."));
        userRepository.findById(principal.getId())
                .orElseThrow(() -> new EntityNotFoundException("작성자를 찾을 수 없습니다."));
        if (!task.getProject().getId().equals(projectId)) {
            throw new IllegalStateException("해당 Tasks는 지정된 프로젝트에 속하지 않습니다. ");
        }
        task.changeContent(dto.title(), dto.content(), dto.status(), dto.priority(), dto.dueDate());
        project.addTask(task);
        List<Tag> existingTags = tagRepository.findTagsByProjectId(projectId);
        Set<String> dtoTagNames = dto.tagNames() == null
                ? Collections.emptySet()
                : dto.tagNames().stream()
                .filter(name -> name != null && !name.isBlank())
                .map(name -> name.replaceAll("\\s+", ""))
                .collect(Collectors.toSet());

        for (TaskTag oldTaskTag : new HashSet<>(task.getTaskTags())) {
            oldTaskTag.getTag().getTaskTags().remove(oldTaskTag);
        }
        task.getTaskTags().clear();
        taskRepository.flush();
        for (String tagName : dtoTagNames) {
            Tag tag = existingTags.stream()
                    .filter(t -> t.getTagName().equals(tagName))
                    .findFirst()
                    .orElseGet(() -> {
                        Tag newTag = new Tag(tagName);
                        newTag.setProject(project);
                        return tagRepository.save(newTag);
                    });
            task.addTag(tag);
        }

        for (Tag tag : existingTags) {
            if (tag.getTaskTags().isEmpty()) {
                tagRepository.delete(tag);
            }
        }
        Task saved = taskRepository.save(task);
        return ResponseDto.setSuccess("SUCCESS", TaskResponse.TaskDetailResponse.from(saved));
    }

    @Override
    @Transactional
    public ResponseDto<Void> deleteTask(UserPrincipal principal, Long projectId, Long taskId) {
        projectRepository.findProjectById((projectId))
                .orElseThrow(() -> new EntityNotFoundException("해당 프로젝트를 찾을 수 없어요."));
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 Task를 찾을 수 없어요."));
        if (!task.getProject().getId().equals(projectId)) {
            throw new IllegalArgumentException("해당 Task가 프로젝트 내에 속해있지 않습니다. ");
        }
        if (!task.getUser().getId().equals(principal.getId())) {
            throw new IllegalArgumentException("잘못된 사용자의 접근입니다.");
        }
        for (TaskTag taskTag : new HashSet<>(task.getTaskTags())) {
            taskTag.getTag().getTaskTags().remove(taskTag);
        }
        taskRepository.delete(task);

        for (TaskTag taskTag2 : new HashSet<>(task.getTaskTags())) {
            if (taskTag2.getTag().getTaskTags().isEmpty()) {
                tagRepository.delete(taskTag2.getTag());
            }
        }
        Project project = task.getProject();
        project.removeTask(task);

        return ResponseDto.setSuccess("SUCCESS", null);
    }
}
