package com.example.mini_project_task_manager.service;

import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.admin.request.AdminAuthRoleRequest;
import com.example.mini_project_task_manager.dto.admin.response.AdminAuthRoleResponse;
import com.example.mini_project_task_manager.security.UserPrincipal;
import jakarta.validation.Valid;

public interface AdminService {
    ResponseDto<AdminAuthRoleResponse.UpdateRolesResponse> usersRoles(UserPrincipal principal, AdminAuthRoleRequest.@Valid UpdateRolesRequest req);

    ResponseDto<AdminAuthRoleResponse.RemoveRoleResponse> managerRoles(UserPrincipal principal, AdminAuthRoleRequest.@Valid RemoveRoleRequest req);
}
