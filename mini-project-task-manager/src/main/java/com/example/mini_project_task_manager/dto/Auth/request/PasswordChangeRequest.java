package com.example.mini_project_task_manager.dto.Auth.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PasswordChangeRequest {
        public record PasswordRequest (
                @NotBlank(message =  "비밀번호 입력은 필수로 작성해야 해요.")
                @Size(min = 8, max = 16, message = "비밀번호는 8자 이상 입력 해야 해요. ")
                String newPassword
        ) {}
}
