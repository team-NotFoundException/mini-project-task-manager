package com.example.mini_project_task_manager.DTO.User.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserSignInRequest (
        @NotBlank @Size(min = 4, max = 50)
        String loginId,

        @NotBlank @Size(min =  8, max = 100)
        String password
) {


}
