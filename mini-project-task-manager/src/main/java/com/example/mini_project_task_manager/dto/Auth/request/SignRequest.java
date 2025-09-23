package com.example.mini_project_task_manager.dto.Auth.request;

import com.example.mini_project_task_manager.common.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
            String password,

            @NotBlank @Email (message = "email은 비워둘수 없습니다.")
            String email,

            @NotBlank
            @Size(min = 1, max = 16, message = "nickname은 비워둘수 없습니다.")
            String nickname,

            Gender gender


    ) { }

}
