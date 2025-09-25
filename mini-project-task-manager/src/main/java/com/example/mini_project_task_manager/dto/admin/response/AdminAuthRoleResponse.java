package com.example.mini_project_task_manager.dto.admin.response;

import com.example.mini_project_task_manager.common.enums.RoleType;

import java.time.LocalDateTime;
import java.util.Set;

public class AdminAuthRoleResponse {
    public record UpdateRolesResponse (
            String username,
            Set<RoleType> roles,
            LocalDateTime updatedAt
    ) {}
    public record AddRoleResponse (
            String username,
            RoleType added,
            Set<RoleType> roles,
            LocalDateTime updatedAt
    ) {}
    public record RemoveRoleResponse (
            String username,
            RoleType removed,
            Set<RoleType> roles,
            LocalDateTime updatedAt
    ) {}
}
