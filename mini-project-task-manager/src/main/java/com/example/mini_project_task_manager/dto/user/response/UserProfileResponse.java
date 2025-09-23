package com.example.mini_project_task_manager.dto.user.response;

import com.example.mini_project_task_manager.common.enums.Gender;
import com.example.mini_project_task_manager.entity.User;

public class UserProfileResponse {

    public record MyPageResponse (
            Long id,        // PK (users.id)
            String username, // 로그인 아이디
            String email,
            String nickname,
            Gender gender
    ) {
        public static MyPageResponse from(User user) {
            return new MyPageResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getNickname(),
                    user.getGender()
            );
        }
    }
}
