package com.example.mini_project_task_manager.dto.admin.response;

import com.example.mini_project_task_manager.common.enums.RoleType;

import java.time.LocalDateTime;
import java.util.Set;

public class AdminAuthRoleResponse {
    public record UpdateRolesResponse (
            Long username,
            String loginId,
            Set<RoleType> roles,
            LocalDateTime updatedAt
    ) {}
    public record AddRoleResponse (
            Long username,
            String loginId,
            Set<RoleType> roles,
            LocalDateTime updatedAt
    ) {}
    public record RemoveRoleResponse (
            Long username,
            String loginId,
            Set<RoleType> roles,
            LocalDateTime updatedAt
    ) {}
}
