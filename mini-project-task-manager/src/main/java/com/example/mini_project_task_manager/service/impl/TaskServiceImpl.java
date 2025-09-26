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
    private final CommentsRepository commentsRepository;

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
        // === 여러 태그 처리 ===
        if (dto.tagNames() != null && !dto.tagNames().isEmpty()) {
            for (String tagName : dto.tagNames()) {
                if (tagName == null || tagName.isBlank()) continue;

                String trimmedName = tagName.replaceAll("\\s+", "");

                // 기존 태그에서 찾아서 있는거 쓰면 그냥 taskTag에 추가
                Tag tag = existingTags.stream()
                        .filter(t -> t.getTagName().equals(trimmedName))
                        .findFirst()
                        .orElseGet(() -> {
                            Tag newTag = new Tag(trimmedName);
                            newTag.setProject(project);
                            // 새 태그 생성시, 관련 프로젝트에도 등록, public안하니까 런타임에러
                            // project 널일수없다고 나옴.
                            return tagRepository.save(newTag);
                        });
                // 3. Task에 Tag 추가 (TaskTag도 자동 생성)
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

        // 필터링 조건이 하나라도 있으면 조건 검색
        if (status != null || priority != null || from != null || to != null
                || dueFrom != null || dueTo != null) {
            tasks = taskRepository.searchTasks(projectId, status, priority, fromUtc, toUtc, dueFrom, dueTo);
            if (tasks == null || tasks.isEmpty()) {
                throw new EntityNotFoundException("원하는 조건에 맞는 Task를 찾을 수 없습니다.");
            }
        }

        // 조건이 전혀 없으면 전체 조회
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
        // Task + 태그 조회
        Task task = taskRepository.findByIdWithTaskTags(projectId, taskId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 Task를 찾을 수 없습니다."));

        // 댓글 조회 후 Task에 추가
        List<Comment> comments = commentsRepository.findByTaskId(taskId);
        for (Comment comment : comments) {
            task.addComment(comment); // 양방향 연관관계 유지
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

        // Task 수정완료 할 때 뜨는 메세지 (도중에 프로젝트가 삭제 되었을때)
        if (!task.getProject().getId().equals(projectId)) {
            throw new IllegalStateException("해당 Tasks는 지정된 프로젝트에 속하지 않습니다. ");
        }

        // 1. Task 내용 변경
        task.changeContent(dto.title(), dto.content(), dto.status(), dto.priority(), dto.dueDate());
        project.addTask(task);

        // 3. 기존 TaskTag 컬렉션 불러오기
        List<Tag> existingTags = tagRepository.findTagsByProjectId(projectId);

        Set<String> dtoTagNames = dto.tagNames() == null
                ? Collections.emptySet()
                : dto.tagNames().stream()
                .filter(name -> name != null && !name.isBlank())
                .map(name -> name.replaceAll("\\s+", ""))
                // 양옆공백/글자사이 스페이스/ tab으로인한 공백제거는 정규식만 가능
                .collect(Collectors.toSet());

        for (TaskTag oldTaskTag : new HashSet<>(task.getTaskTags())) {
            // Tag에서 TaskTag 제거
            oldTaskTag.getTag().getTaskTags().remove(oldTaskTag);
        }
        task.getTaskTags().clear();

        taskRepository.flush();

        // 5. 새 TaskTag 컬렉션 샌성
        for (String tagName : dtoTagNames) {
            // 기존 프로젝트 태그에서 찾거나 새로 생성
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

        // 고아 태그 삭제: 다른 Task와 연결되어 있지 않으면
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

        // new HashSet<> 방어적으로 복사본 만들어서 순회
        for (TaskTag taskTag : new HashSet<>(task.getTaskTags())) {
            taskTag.getTag().getTaskTags().remove(taskTag);
        }
        taskRepository.delete(task);

        // 고아 객체 제거 - tag에 연결된 Task가 없다면
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
