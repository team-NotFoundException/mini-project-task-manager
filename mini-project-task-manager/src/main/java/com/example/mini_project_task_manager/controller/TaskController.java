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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.mini_project_task_manager.common.constants.ApiMappingPattern.Tasks.*;

@RestController
@RequestMapping(ApiMappingPattern.Tasks.ROOT)
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    // Task 생성 - ADMIN/ MANAGER
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<ResponseDto<TaskResponse.TaskDetailResponse>> createTask(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable("projectId") @Positive(message = "projectId는 1 이상이어야 해요.") Long projectId,
            @RequestBody TaskRequest.TaskCreateRequest dto
    ) {
        ResponseDto<TaskResponse.TaskDetailResponse> response = taskService.createTask(principal, projectId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Task 조회 (전체 조회) + 상태, 우선 순위에 따른 조건 필터링 정렬
    @PreAuthorize("hasAnyRole('USER','MANAGER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<ResponseDto<List<TaskResponse.TaskListResponse>>> getAllTasks(
            @PathVariable("projectId") @Positive(message = "projectId는 1 이상이어야 해요.") Long projectId,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Priority priority,
//            @RequestParam(required = false) String author,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueFrom,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueTo
    ) {
        ResponseDto<List<TaskResponse.TaskListResponse>> response = taskService.getAllTasks(projectId, status, priority, from, to, dueFrom, dueTo);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Task 조회 (단건 조회) - 댓글 포함
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping(BY_ID)
    public ResponseEntity<ResponseDto<TaskResponse.TaskDetailResponse>> getTaskById(
            @PathVariable("projectId") @Positive(message = "projectId는 1 이상이어야 합니다.") Long projectId,
            @PathVariable("taskId") @Positive(message = "taskId는 1 이상이어야 합니다.") Long taskId
    ) {
        ResponseDto<TaskResponse.TaskDetailResponse> response = taskService.getTaskById(projectId, taskId);
        return ResponseEntity.ok().body(response);
    }

    // Task 수정 - ADMIN/ MANAGER
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PutMapping(BY_ID)
    public ResponseEntity<ResponseDto<TaskResponse.TaskDetailResponse>> updateTask(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable("projectId") @Positive(message = "projectId는 1 이상이어야 합니다.") Long projectId,
            @PathVariable("taskId") @Positive(message = "taskId는 1 이상이어야 합니다.") Long taskId,
            @RequestBody TaskRequest.TaskUpdateRequest dto
    ) {
        ResponseDto<TaskResponse.TaskDetailResponse> response = taskService.updateTask(principal, projectId, taskId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Task 삭제 - ADMIN/ MANAGER
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

/*
    @Valid
    // : DTO 객체에 대한 검증을 수행하는 어노테이션
    // - 사용자가 클라이언트로부터 전달한 데이터가 미리 정의된 규칙에 맞는지 확인(검증)

    @Positive
    // 값이 null 제외하고 양수만 허용 vs PositiveOrZero 0포함 양수 허용

    - @PathVariable("taskId") Long taskId
    → URL 변수명과 메서드 변수명이 같을 때

    @PathVariable Long taskId
    → 메서드 변수명을 URL 변수명과 다르게 쓰고 싶을 때 명시적으로 연결
 */