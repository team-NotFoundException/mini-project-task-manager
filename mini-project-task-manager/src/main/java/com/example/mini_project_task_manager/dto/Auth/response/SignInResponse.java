package com.example.mini_project_task_manager.dto.Auth.response;

import java.util.Set;

public record SignInResponse(
        String tokenType,
        String accessToken,
        long expiresAt,
        String username,
        Set<String> roles
) {}
