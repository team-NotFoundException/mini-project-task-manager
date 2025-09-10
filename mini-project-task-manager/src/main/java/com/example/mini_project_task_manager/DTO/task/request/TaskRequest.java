package com.example.mini_project_task_manager.DTO.task.request;

public class TaskRequest {

    public record TaskCreateRequest(
            String title,
            String content
    ) {}

    public record TaskUpdateRequest(
            String title,
            String content
    ){}
}
