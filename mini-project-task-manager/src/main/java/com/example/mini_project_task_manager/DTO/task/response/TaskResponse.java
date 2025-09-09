package com.example.mini_project_task_manager.DTO.task.response;

import java.time.LocalDateTime;

public class TaskResponse {

    public record TaskCreateResponse(
            Long id,
            Long projectId,
            String title,
            String content,
            String author,
            LocalDateTime createdAt
    ) {}

    public record TaskUpdateResponse(
            Long id,
            Long ProjectId,
            String title,
            String content,
            LocalDateTime updatedAt
    ){}
}
