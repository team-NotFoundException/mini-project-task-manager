package com.example.mini_project_task_manager.service;

import com.example.mini_project_task_manager.common.enums.Priority;
import com.example.mini_project_task_manager.common.enums.Status;
import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.task.request.TaskRequest;
import com.example.mini_project_task_manager.dto.task.response.TaskResponse;
import com.example.mini_project_task_manager.security.UserPrincipal;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TaskService {

    ResponseDto<TaskResponse.TaskDetailResponse> createTask(UserPrincipal principal, @Positive(message = "projectId는 1 이상이어야 합니다.") Long projectId, TaskRequest.@Valid TaskCreateRequest dto);
    ResponseDto<List<TaskResponse.TaskListResponse>> getAllTasks(@Positive(message = "projectId는 1 이상이어야 합니다.") Long projectId, Status status, Priority priority, LocalDateTime from, LocalDateTime to, LocalDate dueFrom, LocalDate dueTo);
    ResponseDto<TaskResponse.TaskDetailResponse> getTaskById(Long projectId, Long taskId);
    ResponseDto<TaskResponse.TaskDetailResponse> updateTask(UserPrincipal principal, @Positive(message = "projectId는 1 이상이어야 합니다.") Long projectId, @Positive(message = "taskId는 1 이상이어야 합니다.") Long taskId, TaskRequest.@Valid TaskUpdateRequest dto);
    ResponseDto<Void> deleteTask(UserPrincipal principal, @Positive(message = "projectId는 1 이상이어야 합니다.") Long projectId, @Positive(message = "taskId는 1 이상이어야 합니다.") Long taskId);
}
