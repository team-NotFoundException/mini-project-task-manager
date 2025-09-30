package com.example.mini_project_task_manager.controller;

import com.example.mini_project_task_manager.common.constants.ApiMappingPattern;
import com.example.mini_project_task_manager.common.enums.Priority;
import com.example.mini_project_task_manager.common.enums.Status;
import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.task.request.TaskRequest;
import com.example.mini_project_task_manager.dto.task.response.TaskResponse;
import com.example.mini_project_task_manager.security.UserPrincipal;
import com.example.mini_project_task_manager.service.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.mini_project_task_manager.common.constants.ApiMappingPattern.Tasks.*;

@RestController
@RequestMapping(ApiMappingPattern.Tasks.ROOT)
@RequiredArgsConstructor
@Validated
public class TaskController {
    private final TaskService taskService;

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<ResponseDto<TaskResponse.TaskDetailResponse>> createTask(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable("projectId") @Positive(message = "projectId는 1 이상이어야 해요.") Long projectId,
            @Valid @RequestBody TaskRequest.TaskCreateRequest dto
    ) {
        ResponseDto<TaskResponse.TaskDetailResponse> response = taskService.createTask(principal, projectId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasAnyRole('USER','MANAGER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<ResponseDto<List<TaskResponse.TaskListResponse>>> getAllTasks(
            @PathVariable("projectId") @Positive(message = "projectId는 1 이상이어야 해요.") Long projectId
    ) {
        ResponseDto<List<TaskResponse.TaskListResponse>> response = taskService.getAllTasks(projectId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasAnyRole('USER','MANAGER', 'ADMIN')")
    @GetMapping(FILTER_OPTION)
    public ResponseEntity<ResponseDto<List<TaskResponse.TaskListResponse>>> getTasksByFiltering(
            @PathVariable("projectId") @Positive(message = "projectId는 1 이상이어야 해요.") Long projectId,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueFrom,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueTo
    ) {
        ResponseDto<List<TaskResponse.TaskListResponse>> response = taskService.getTasksByFiltering(projectId, status, priority, from, to, dueFrom, dueTo);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping(BY_ID)
    public ResponseEntity<ResponseDto<TaskResponse.TaskDetailResponse>> getTaskById(
            @PathVariable("projectId") @Positive(message = "projectId는 1 이상이어야 합니다.") Long projectId,
            @PathVariable("taskId") @Positive(message = "taskId는 1 이상이어야 합니다.") Long taskId
    ) {
        ResponseDto<TaskResponse.TaskDetailResponse> response = taskService.getTaskById(projectId, taskId);
        return ResponseEntity.ok().body(response);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PutMapping(BY_ID)
    public ResponseEntity<ResponseDto<TaskResponse.TaskDetailResponse>> updateTask(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable("projectId") @Positive(message = "projectId는 1 이상이어야 합니다.") Long projectId,
            @PathVariable("taskId") @Positive(message = "taskId는 1 이상이어야 합니다.") Long taskId,
            @Valid @RequestBody TaskRequest.TaskUpdateRequest dto
    ) {
        ResponseDto<TaskResponse.TaskDetailResponse> response = taskService.updateTask(principal, projectId, taskId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @DeleteMapping(BY_ID)
    public ResponseEntity<ResponseDto<Void>> deleteTask(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable("projectId") @Positive(message = "projectId는 1 이상이어야 합니다.") Long projectId,
            @PathVariable("taskId") @Positive(message = "taskId는 1 이상이어야 합니다.") Long taskId
    ) {
        ResponseDto<Void> response = taskService.deleteTask(principal, projectId, taskId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}