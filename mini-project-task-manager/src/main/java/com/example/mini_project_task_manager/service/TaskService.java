//package com.example.mini_project_task_manager.service;
//
//import com.example.mini_project_task_manager.DTO.ResponseDto;
//import com.example.mini_project_task_manager.DTO.task.request.TaskRequest;
//import com.example.mini_project_task_manager.DTO.task.response.TaskResponse;
//import jakarta.validation.Valid;
//import jakarta.validation.constraints.Positive;
//
//import java.nio.file.attribute.UserPrincipal;
//
//public interface TaskService {
//    ResponseDto<TaskResponse> createTask(UserPrincipal userPrincipal, @Positive(message = "projectId는 1 이상이어야 합니다.") Long projectId, TaskRequest.@Valid TaskCreateRequest dto);
//    ResponseDto<TaskResponse> updateTask(UserPrincipal userPrincipal, @Positive(message = "projectId는 1 이상이어야 합니다.") Long projectId, @Positive(message = "taskId는 1 이상이어야 합니다.") Long taskId, TaskRequest.@Valid TaskUpdateRequest dto);
//    ResponseDto<Void> deleteTask(UserPrincipal userPrincipal, @Positive(message = "projectId는 1 이상이어야 합니다.") Long projectId, @Positive(message = "taskId는 1 이상이어야 합니다.") Long taskId);
//}
