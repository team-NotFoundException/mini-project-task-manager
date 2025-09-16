package com.example.mini_project_task_manager.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignRequest {
    public record SignInRequest (
            @NotBlank
            @Size(min = 4, max = 16, message = "로그인은 4자 이상 입력 해야 합니다.")
            String username,

            @NotBlank
            @Size(min = 8, max = 16, message = "비밀번호는 8자 이상 입력 해야 합니다.")
            String password
    ) {}
    public record SingUpRequest (
            @NotBlank
            @Size(min = 4, max = 16, message = "로그인 아이디는 4지 이상 입력 해야 합니다.")
            String username,

            @NotBlank
            @Size(min = 8, max = 16, message = "비밀번호는 8자 이상 입력 해야 합니다.")
            String password
    ) {}

}
