package com.example.mini_project_task_manager.dto.notification.request;

import com.example.mini_project_task_manager.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class NotificationsRequest {

    public record NotificationCreateRequest(
            @NotBlank(message = "제목 입력은 필수에요.")
            @Size(max = 500, message = "제목은 최대 500자 이하여야 해요.")
            String title,

            @NotBlank(message = "내용 입력은 필수에요.")
            @Size(max = 1000, message = "내용은 1000자 이하여야해요.")
            String content
    ) {}
}
