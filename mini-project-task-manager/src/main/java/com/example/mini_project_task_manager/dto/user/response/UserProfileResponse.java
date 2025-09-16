package com.example.mini_project_task_manager.dto.user.response;

import com.example.mini_project_task_manager.common.enums.Gender;

public class UserProfileResponse {
    public record MyPageResponse (
            Long username,
            String loginId,
            String email,
            String nickname,
            Gender gender
    ) {}
}
