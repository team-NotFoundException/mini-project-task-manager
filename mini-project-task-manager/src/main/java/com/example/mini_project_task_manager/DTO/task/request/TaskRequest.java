package com.example.mini_project_task_manager.DTO.task.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TaskRequest {

    /** 할일 생성 요청 Dto */
    public record TaskCreateRequest(

            @NotBlank(message = "제목 입력은 필수입니다.")
            @Size(max = 200, message = "제목은 200자 이하여야 합니다.")
            String title,
            String content,
            String priority
    ) {}

    /** 할일 수정 요청 Dto */
    public record TaskUpdateRequest(

            @NotBlank(message = "제목 입력은 필수입니다.")
            @Size(max = 200, message = "제목은 200자 이하여야 합니다.")
            String title,
            String content,
            String status,
            String priority
    ) {}
}