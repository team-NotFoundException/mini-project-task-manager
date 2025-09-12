package com.example.mini_project_task_manager.service.impl;

import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.task.request.TaskRequest;
import com.example.mini_project_task_manager.dto.task.response.TaskResponse;
import com.example.mini_project_task_manager.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.attribute.UserPrincipal;

// C: USER, OWNER
// R: 권한 x
// U: AUTHOR, OWNER
// D: Status가 COMPLETE면 권한 X | Status가 COMPLETE가 아니라면 AUTHOR, OWNER


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {
    @Override
    public ResponseDto<TaskResponse> createTask(UserPrincipal userPrincipal, Long projectId, TaskRequest.@Valid TaskCreateRequest dto) {
        return null;
    }

    @Override
    public ResponseDto<TaskResponse> updateTask(UserPrincipal userPrincipal, Long projectId, Long taskId, TaskRequest.@Valid TaskUpdateRequest dto) {
        return null;
    }

    @Override
    public ResponseDto<Void> deleteTask(UserPrincipal userPrincipal, Long projectId, Long taskId) {
        return null;
    }
}
