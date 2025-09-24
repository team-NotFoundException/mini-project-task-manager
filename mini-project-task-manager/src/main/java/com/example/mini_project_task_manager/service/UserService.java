package com.example.mini_project_task_manager.service;

import com.example.mini_project_task_manager.dto.Auth.request.SignRequest;
import com.example.mini_project_task_manager.dto.Auth.response.SignInResponse;
import com.example.mini_project_task_manager.dto.Mail.MailRequest;
import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.user.request.UserProfileUpdateRequest;
import com.example.mini_project_task_manager.dto.user.response.UserProfileResponse;
import com.example.mini_project_task_manager.security.UserPrincipal;
import jakarta.validation.Valid;

public interface UserService {
    void signUp(SignRequest.@Valid SingUpRequest req);

    ResponseDto<SignInResponse> signIn(SignRequest.@Valid SignInRequest req);

    ResponseDto<UserProfileResponse.MyPageResponse> getMyInfo(UserPrincipal principal);

    ResponseDto<UserProfileResponse.MyPageResponse> updateMyInfo(UserPrincipal principal, @Valid UserProfileUpdateRequest request);

    void resetPassword(MailRequest.@Valid PasswordReset req);
}
