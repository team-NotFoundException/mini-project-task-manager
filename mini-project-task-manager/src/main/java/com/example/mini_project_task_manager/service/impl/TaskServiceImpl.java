package com.example.mini_project_task_manager.service.impl;

import com.example.mini_project_task_manager.common.enums.Priority;
import com.example.mini_project_task_manager.common.enums.Status;
import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.task.request.TaskRequest;
import com.example.mini_project_task_manager.dto.task.response.TaskResponse;
import com.example.mini_project_task_manager.entity.Project;
import com.example.mini_project_task_manager.entity.Tag;
import com.example.mini_project_task_manager.entity.Task;
import com.example.mini_project_task_manager.entity.User;
import com.example.mini_project_task_manager.repository.ProjectRepository;
import com.example.mini_project_task_manager.repository.TagRepository;
import com.example.mini_project_task_manager.repository.TaskRepository;
import com.example.mini_project_task_manager.repository.UserRepository;
import com.example.mini_project_task_manager.security.UserPrincipal;
import com.example.mini_project_task_manager.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TagRepository tagRepository;

    @Override
    @Transactional
    public ResponseDto<TaskResponse.TaskDetailResponse> createTask(
            UserPrincipal principal, Long projectId, TaskRequest.@Valid TaskCreateRequest dto) {

        Project project = projectRepository.findById(projectId)
                        .orElseThrow(() -> new EntityNotFoundException("해당 id의 project를 찾을 수 없습니다."));

        User user = userRepository.findById(principal.getId())
                .orElseThrow(()-> new EntityNotFoundException("로그인 사용자를 찾을 수 없습니다."));

        Task task = Task.createTask(
                dto.title().trim(),
                dto.content().trim(),
                user,
                dto.status(),
                dto.priority(),
                dto.dueDate());

        project.addTask(task);
        // === 여기 까지는 Task 생성하고 project에 적용시키기

        /** 직접 입력한 태그 처리 하기 */
        // === 여러 태그 처리 ===
        if (dto.tagNames() != null && !dto.tagNames().isEmpty()){
            for (String tagName : dto.tagNames()) {
                if (tagName == null || tagName.isBlank()) continue;

                // 1. DB에서 기존 Tag 찾아서 없으면 새로만들기.
                Tag tag = tagRepository.findByTagName("#"+tagName.trim())
                        .orElseGet(() -> {
                            // 2. 없으면 새 Tag 생성 후 저장
                            Tag newTag = new Tag("#"+tagName.trim());
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
    public ResponseDto<List<TaskResponse.TaskListResponse>> getAllTasks(Long projectId) {
        List<Task> tasks = taskRepository.findByProjectIdOrderByIdDesc(projectId); // 최신순 반환

        List<TaskResponse.TaskListResponse> result = tasks.stream()
                .map(TaskResponse.TaskListResponse::from)
                .toList();

        return ResponseDto.setSuccess("SUCCESS", result);
    }

    @Override
    public ResponseDto<List<TaskResponse.TaskListResponse>> getTasksByFiltering(
            Long projectId, Status status, Priority priority) {

        return null;
    }

    @Override
    public ResponseDto<TaskResponse.TaskDetailResponse> getTaskById(Long projectId, Long taskId) {
        Task task = taskRepository.findByIdWithCommentsAndTaskTags(projectId, taskId)
                .orElseThrow(()-> new EntityNotFoundException("해당 id의 Task를 찾을 수 없습니다."));

        return ResponseDto.setSuccess("SUCCESS", TaskResponse.TaskDetailResponse.from(task));
    }

    @Override
    @Transactional
    public ResponseDto<TaskResponse.TaskDetailResponse> updateTask(
            UserPrincipal principal, Long projectId, Long taskId, TaskRequest.@Valid TaskUpdateRequest dto) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(()-> new EntityNotFoundException("해당 id의 Task를 찾을 수 없습니다."));

        userRepository.findById(principal.getId())
                .orElseThrow(()-> new EntityNotFoundException("작성자를 찾을 수 없습니다."));

        if (!task.getProject().getId().equals(projectId)){
            throw new IllegalArgumentException("해당 Task가 프로젝트 내에 속해있지 않습니다. ");
        }

        task.changeContent(dto.title(), dto.content(),dto.status(), dto.priority(), dto.dueDate());
//        task.addTag(dto.tagName());

        return ResponseDto.setSuccess("SUCCESS", TaskResponse.TaskDetailResponse.from(task));
    }

    @Override
    @Transactional
    public ResponseDto<Void> deleteTask(UserPrincipal principal, Long projectId, Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(()-> new EntityNotFoundException("해당 id의 Task를 찾을 수 없습니다."));

        if(!task.getUser().getId().equals(principal.getId())){
            throw new IllegalArgumentException("잘못된 사용자의 접근입니다.");
        }

        if (!task.getProject().getId().equals(projectId)){
            throw new IllegalArgumentException("해당 Task가 프로젝트 내에 속해있지 않습니다. ");
        }

        Project project = task.getProject();
        project.removeTask(task);

        return ResponseDto.setSuccess("SUCCESS", null);
    }
}
