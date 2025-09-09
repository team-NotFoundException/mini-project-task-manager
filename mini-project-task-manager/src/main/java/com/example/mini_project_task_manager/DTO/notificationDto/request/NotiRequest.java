package com.example.mini_project_task_manager.DTO.notificationDto.request;

import java.time.LocalDateTime;

public class NotiRequest {

    public record NotiCreateRequest(
            String title,
            String content

    ) {}

    public record NotiUpdateRequest(
            String title,
            String content
    ) {}
}
