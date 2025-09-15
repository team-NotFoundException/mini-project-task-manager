package com.example.mini_project_task_manager.dto.Auth.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignInRequest(
        @NotBlank @Size(min = 4, max = 50)
        String username,

        @NotBlank @Size(min = 8, max = 100)
        String password
) {
}
