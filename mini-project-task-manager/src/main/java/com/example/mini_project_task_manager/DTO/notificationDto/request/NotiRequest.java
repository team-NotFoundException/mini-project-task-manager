package com.example.mini_project_task_manager.DTO.notificationDto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class NotiRequest {

    public record NotiCreateRequest(
            @NotBlank(message = "제목을 비워둘 수 없어요")
            @Size(max = 500, message = "제목은 최대 500자 까지만 적을 수 있습니다.")
            String title,
            @NotBlank(message = "내용을 비워둘 수 없어요")
            @Size(max = 1000, message = "아무튼 1000자 이내")
            String content

    ) {}

    public record NotiUpdateRequest(
            @NotBlank(message = "제목을 비워둘 수 없어요")
            @Size(max = 500, message = "제목은 최대 500자 까지만 적을 수 있습니다.")
            String title,

            @NotBlank(message = "내용을 비워둘 수 없어요")
            @Size(max = 1000, message = "아무튼 1000자 이내")
            String content
    ) {}
}
