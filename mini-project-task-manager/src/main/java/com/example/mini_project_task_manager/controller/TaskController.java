package com.example.mini_project_task_manager.controller;


import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.task.request.TaskRequest;
import com.example.mini_project_task_manager.dto.task.response.TaskResponse;
import com.example.mini_project_task_manager.service.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;

@RestController
@RequestMapping("/api/v1/projects/{projectId}")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    // Task 생성 - 인증된 사용자만
    @PostMapping
    public ResponseEntity<ResponseDto<TaskResponse>> createTask(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable("projectId") @Positive(message = "projectId는 1 이상이어야 합니다.") Long projectId,
            @Valid @RequestBody TaskRequest.TaskCreateRequest dto
    ) {
        ResponseDto<TaskResponse> response = taskService.createTask(userPrincipal, projectId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Task 수정 - 인증된 사용자만
    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<ResponseDto<TaskResponse>> updateTask(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable("projectId") @Positive(message = "projectId는 1 이상이어야 합니다.") Long projectId,
            @PathVariable("taskId") @Positive(message = "taskId는 1 이상이어야 합니다.") Long taskId,
            @Valid @RequestBody TaskRequest.TaskUpdateRequest dto
    ) {
        ResponseDto<TaskResponse> response = taskService.updateTask(userPrincipal, projectId, taskId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Task 삭제 - 인증된 사용자만
    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<ResponseDto<Void>> deleteTask(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable("projectId") @Positive(message = "projectId는 1 이상이어야 합니다.") Long projectId,
            @PathVariable("taskId") @Positive(message = "taskId는 1 이상이어야 합니다.") Long taskId
    ) {
        ResponseDto<Void> response = taskService.deleteTask(userPrincipal, projectId, taskId);
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