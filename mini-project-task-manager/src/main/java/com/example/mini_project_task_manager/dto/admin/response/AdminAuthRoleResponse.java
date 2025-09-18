package com.example.mini_project_task_manager.dto.admin.response;

import com.example.mini_project_task_manager.common.enums.RoleType;

import java.time.LocalDateTime;
import java.util.Set;

public class AdminAuthRoleResponse {
    public record UpdateRolesResponse (
            String username,
            String loginId,
            Set<RoleType> roles,
            LocalDateTime updatedAt
    ) {}
    public record AddRoleResponse (
            String username,
            String loginId,
            Set<RoleType> roles,
            LocalDateTime updatedAt
    ) {}
    public record RemoveRoleResponse (
            String username,
            String loginId,
            RoleType role, Set<RoleType> roles,
            LocalDateTime updatedAt
    ) {}
}
