package com.example.mini_project_task_manager.dto.Auth.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordChangeRequest(
        @NotBlank @Size(min = 8, max = 100)
        String newPassword
) {}
