package com.example.mini_project_task_manager.DTO.User.request;

import com.example.mini_project_task_manager.common.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserSignUpRequest (
        @NotBlank @Size(min = 4, max = 50)
        String loginId,

        @NotBlank @Size(min = 8, max = 100)
        String password,

        @NotBlank @Email @Size(max = 50)
        String nickname,

        Gender gender
) { }
