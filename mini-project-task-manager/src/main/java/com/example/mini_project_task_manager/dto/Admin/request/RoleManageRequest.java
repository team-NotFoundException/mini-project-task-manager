package com.example.mini_project_task_manager.dto.Admin.request;

import com.example.mini_project_task_manager.common.enums.RoleType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


import java.util.Set;

public class RoleManageRequest {
    
    public record UpdateRolesRequest(
        @NotNull(massage = "User Id는 필수입니다.")
        @Positive(massage = "User Id는 양수여야 합니다.")
        Long userId,

        @NotEmpty(message = "roles는 비어 있을 수 없습니다.")
        Set<@NotNull(message = "roles는 null일 수 없습니다.") RoleType> roles
    
        ) {}

        public record AddRoleRequest(
            @NotNull(massage = "User Id는 필수입니다.")
            @Positive(massage = "User Id는 양수여야 합니다.")
            Long userId,

            @NotNull(message = "role은 필수입니다.") 
            RoleType role
        ) {}

        public record RemoveRoleRequest(
            @NotNull(massage = "User Id는 필수입니다.")
            @Positive(massage = "User Id는 양수여야 합니다.")
            Long userId,

            @NotNull(massage = "role은 필수입니다.") 
            RoleType role
        ) {}
}
