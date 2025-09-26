package com.example.mini_project_task_manager.dto.Auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FindUsernameRequest(
        @NotBlank(message = "값 입력은 필수에요. ")
        @Size(min = 1)
        String nickname,

        @NotBlank(message = "이메일 형식으로 작성 입력 해주세요.")
        @Email
        String email
) {}
