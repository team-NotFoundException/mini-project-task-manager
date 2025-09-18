package com.example.mini_project_task_manager.service.impl;

import com.example.mini_project_task_manager.common.enums.Priority;
import com.example.mini_project_task_manager.common.enums.Status;
import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.task.request.TaskRequest;
import com.example.mini_project_task_manager.dto.task.response.TaskResponse;
import com.example.mini_project_task_manager.entity.Task;
import com.example.mini_project_task_manager.repository.TaskRepository;
import com.example.mini_project_task_manager.security.UserPrincipal;
import com.example.mini_project_task_manager.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

// C: USER, OWNER
// R: 권한 x
// U: AUTHOR, OWNER
// D: Status가 COMPLETE면 권한 X | Status가 COMPLETE가 아니라면 AUTHOR, OWNER


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    @Transactional
    public ResponseDto<TaskResponse.TaskDetailResponse> createTask(UserPrincipal principal, Long projectId, TaskRequest.@Valid TaskCreateRequest dto) {

        Objects.requireNonNull(dto, "TaskCreateRequestDto must not be null");

        String title = dto.title().trim();
        String content = dto.content().trim();

        Task task = Task.createTask(title, content, author, status, priority, dueDate);
        Task saved = taskRepository.save(task);
        return ResponseDto.setSuccess("SUCCESS", TaskResponse.TaskDetailResponse.from(saved));
    }

    @Override
    public ResponseDto<List<TaskResponse.TaskListResponse>> getAllTasks(Long projectId) {

        Task task = taskRepository.findAllByIdOrderByIdDesc(projectId)
                .orElseThrow(()-> new EntityNotFoundException("해당 프로젝트의 TODO를 찾을 수 없습니다. "));
        return null;
    }

    @Override
    public ResponseDto<List<TaskResponse.TaskListResponse>> getTasksByFiltering(Long projectId, Status status, Priority priority) {
        return null;
    }
    @Override
    public ResponseDto<TaskResponse.TaskDetailResponse> getTaskById(Long projectId, Long taskId) {
        return null;
    }


    @Override
    @Transactional
    public ResponseDto<TaskResponse.TaskDetailResponse> updateTask(UserPrincipal principal, Long projectId, Long taskId, TaskRequest.@Valid TaskUpdateRequest dto) {
        return null;
    }

    @Override
    @Transactional
    public ResponseDto<Void> deleteTask(UserPrincipal principal, Long projectId, Long taskId) {
        return null;
    }
}
