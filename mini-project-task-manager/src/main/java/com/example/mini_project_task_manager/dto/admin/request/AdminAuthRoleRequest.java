package com.example.mini_project_task_manager.dto.admin.request;

import com.example.mini_project_task_manager.common.enums.RoleType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Set;

/** 특정 사용자 (username) 의 권한을 해당 Set으로 교체(갱신) */
public class AdminAuthRoleRequest {
    public record UpdateRolesRequest(
       @NotNull(message = "username는 필수 입니다.")
       @Positive(message = "username는 양수여야 합니다.")
       Long username,

       @NotEmpty(message = "roles는 비어있을 수 없습니다.")
       Set<@NotNull (message = " roles 항목은 null일 수 없습니다.") RoleType> role
    ) {}
    /** 특정 사용자(username)에 단일 권한 추가 */
    public record AddRoleRequest (
            @NotNull(message = "username는 필수 입니다.")
            @Positive(message = "username는 양수여야 합니다.")
            Long username,
            @NotNull(message = "role은 필수 입니다.")
            RoleType role
    ) {}
    /** 특정 사용자 (username)에서 단일 권한을 제거 */
    public record RemoveRoleRequest (
            @NotNull(message = "username는 필수 입니다.")
            @Positive(message = "username는 양수여야 합니다.")
            Long username,
            @NotNull(message = "role은 필수 입니다.")
            RoleType role
    ) {}
}
