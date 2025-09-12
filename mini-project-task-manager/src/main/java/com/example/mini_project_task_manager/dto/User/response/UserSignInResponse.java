package com.example.mini_project_task_manager.dto.User.response;

import com.example.mini_project_task_manager.common.enums.Gender;

import java.util.Set;

public record UserSignInResponse (
        String tokenType,
        String accessToken,
        long expiresAt,
        String username,
        Set<String> roles
) {}
