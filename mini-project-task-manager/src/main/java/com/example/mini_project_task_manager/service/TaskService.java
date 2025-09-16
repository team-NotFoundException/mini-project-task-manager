package com.example.mini_project_task_manager.service;

import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.task.request.TaskRequest;
import com.example.mini_project_task_manager.dto.task.response.TaskResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;

public interface TaskService {

    ResponseDto<TaskResponse.TaskDetailResponse> createTask( @Positive(message = "projectId는 1 이상이어야 합니다.") Long projectId, TaskRequest.@Valid TaskCreateRequest dto);
    ResponseDto<List<TaskResponse.TaskListResponse>> getAllTasks();

    ResponseDto<TaskResponse> updateTask(UserPrincipal userPrincipal, @Positive(message = "projectId는 1 이상이어야 합니다.") Long projectId, @Positive(message = "taskId는 1 이상이어야 합니다.") Long taskId, TaskRequest.@Valid TaskUpdateRequest dto);

    ResponseDto<TaskResponse.TaskDetailResponse> getTaskById();

    ResponseDto<Void> deleteTask(UserPrincipal userPrincipal, @Positive(message = "projectId는 1 이상이어야 합니다.") Long projectId, @Positive(message = "taskId는 1 이상이어야 합니다.") Long taskId);
}
