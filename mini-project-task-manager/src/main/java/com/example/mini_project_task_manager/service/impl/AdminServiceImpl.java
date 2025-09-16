package com.example.mini_project_task_manager.service.impl;

import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.admin.request.AdminAuthRoleRequest;
import com.example.mini_project_task_manager.dto.admin.response.AdminAuthRoleResponse;
import com.example.mini_project_task_manager.repository.RoleRepository;
import com.example.mini_project_task_manager.repository.UserRepository;
import com.example.mini_project_task_manager.repository.UserRoleRepository;
import com.example.mini_project_task_manager.security.UserPrincipal;
import com.example.mini_project_task_manager.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public ResponseDto<AdminAuthRoleResponse.UpdateRolesResponse> replaceRoles(UserPrincipal principal, AdminAuthRoleRequest.@Valid UpdateRolesRequest req) {
        return null;
    }

    @Override
    public ResponseDto<AdminAuthRoleResponse.AddRoleResponse> addRole(UserPrincipal principal, AdminAuthRoleRequest.@Valid AddRoleRequest req) {
        return null;
    }

    @Override
    public ResponseDto<AdminAuthRoleResponse.RemoveRoleResponse> removeRole(UserPrincipal principal, AdminAuthRoleRequest.@Valid RemoveRoleRequest req) {
        return null;
    }
}
