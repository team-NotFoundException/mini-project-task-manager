package com.example.mini_project_task_manager.dto.Auth.request;

import com.example.mini_project_task_manager.common.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        @NotBlank @Size(min = 4, max = 50)
        String username,

        @NotBlank @Size(min = 8, max = 100)
        String password,

        @NotBlank @Email @Size(max = 255)
        String email,

        @NotBlank  @Size(max = 40)
        String nickname,

        Gender gender


) {
}
