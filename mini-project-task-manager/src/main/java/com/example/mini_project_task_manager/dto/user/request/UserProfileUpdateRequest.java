package com.example.mini_project_task_manager.dto.user.request;

import com.example.mini_project_task_manager.common.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserProfileUpdateRequest (
        @NotBlank @Size(max = 50)
        String nickname,
        Gender gender
) { }
