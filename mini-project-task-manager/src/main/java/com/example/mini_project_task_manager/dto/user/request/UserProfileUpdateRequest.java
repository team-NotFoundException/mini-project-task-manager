package com.example.mini_project_task_manager.dto.user.request;

import com.example.mini_project_task_manager.common.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserProfileUpdateRequest (
        @NotBlank @Size(min = 1, max = 16)
        String nickname,
        @NotBlank @Email String email,
        Gender gender
) { }
