package com.example.mini_project_task_manager.dto.Admin.response;

import com.example.mini_project_task_manager.common.enums.RoleType;

import java.time.LocalDateTime;
import java.util.Set;



public final class RoleMangeResponse {
    public record UpdateRolesRequest(
        Long userId,
        String loginId,
        Set<RoleType> roles,
        LocalDateTime updateAt
    ) {}

    public record AddRoleRequest(
        Long userId,
        String loginId,
        RoleType added,
        Set<RoleType> roles,
        LocalDateTime updateAt
    ) {}

    public record RemoveRoleRequest(
        Long userId,
        String loginId,
        RoleType removed,
        Set<RoleType> roles,
        LocalDateTime updateAt
    ) {}
}
