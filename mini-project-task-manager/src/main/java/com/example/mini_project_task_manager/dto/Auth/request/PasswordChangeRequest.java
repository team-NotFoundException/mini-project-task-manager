package com.example.mini_project_task_manager.dto.Auth.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PasswordChangeRequest {
        public record PasswordRequest (
                @NotBlank(message =  "비밀번호 입력은 필수 입니다.")
                @Size(min = 8, max = 16, message = "비밀번호는 8자 이상 입력 해야 합니다. ")
                String newPassword
        ) {}
}
