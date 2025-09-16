package com.example.mini_project_task_manager.dto.user.request;

import com.example.mini_project_task_manager.common.enums.RoleType;
import jakarta.validation.constraints.NotNull;


public record RoleModifyRequest(
        @NotNull
        RoleType role
) {}
