package com.example.mini_project_task_manager.service.impl;

import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.user.request.SignRequest;
import com.example.mini_project_task_manager.dto.user.request.UserProfileUpdateRequest;
import com.example.mini_project_task_manager.dto.user.response.SignInResponse;
import com.example.mini_project_task_manager.dto.user.response.UserProfileResponse;
import com.example.mini_project_task_manager.security.UserPrincipal;
import com.example.mini_project_task_manager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// C: 권한X -> O
// R: USER 권한
// U: USER 권한
// D: USER 권한

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    @Override
    public void signUp(SignRequest.@Valid SingUpRequest req) {

    }

    @Override
    public ResponseDto<SignInResponse> signIn(SignRequest.@Valid SignInRequest req) {
        return null;
    }

    @Override
    public ResponseDto<UserProfileResponse.MyPageResponse> getMyInfo(UserPrincipal principal) {
        return null;
    }

    @Override
    public ResponseDto<UserProfileResponse.MyPageResponse> updateMyInfo(UserPrincipal principal, UserProfileUpdateRequest request) {
        return null;
    }
}
