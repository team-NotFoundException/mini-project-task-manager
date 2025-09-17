package com.example.mini_project_task_manager.service.impl;

import com.example.mini_project_task_manager.common.enums.RoleType;
import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.admin.request.AdminAuthRoleRequest;
import com.example.mini_project_task_manager.dto.admin.response.AdminAuthRoleResponse;
import com.example.mini_project_task_manager.entity.Role;
import com.example.mini_project_task_manager.entity.User;
import com.example.mini_project_task_manager.repository.RoleRepository;
import com.example.mini_project_task_manager.repository.UserRepository;
import com.example.mini_project_task_manager.repository.UserRoleRepository;
import com.example.mini_project_task_manager.security.UserPrincipal;
import com.example.mini_project_task_manager.service.AdminService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Override
    public ResponseDto<AdminAuthRoleResponse.UpdateRolesResponse> usersRoles(
            UserPrincipal principal, AdminAuthRoleRequest.@Valid UpdateRolesRequest req
    ) {
        User user = userRepository.findWithRolesByUsername(String.valueOf(req.username()))
                .orElseThrow(() -> new EntityNotFoundException("해당 username이 없습니다."));

        user.getUserRoles().clear();

        userRepository.flush();

        AdminAuthRoleResponse.UpdateRolesResponse data = new AdminAuthRoleResponse.UpdateRolesResponse(
                user.getUsername(),
                user.getPassword(),
                Set.copyOf(user.getRoleTypes()),
                user.getUpdatedAt()
        );

        return ResponseDto.setSuccess("SUCCESS",data);

    }

    @Override
    @Transactional
    public ResponseDto<AdminAuthRoleResponse.RemoveRoleResponse> managerRoles(
            UserPrincipal principal, AdminAuthRoleRequest.@Valid RemoveRoleRequest req) {
        User user = userRepository.findWithRolesByUsername(String.valueOf(req.username()))
                .orElseThrow(() -> new EntityNotFoundException("해당 username의 사용자가 없습니다"));
        Role role = roleRepository.findById(req.role())
                .orElseThrow(() -> new EntityNotFoundException("해당 권한을 찾을 수 없습니다."));

        user.revokeRole(role);

        userRepository.flush();
        if (user.getUserRoles().isEmpty()) {
            user.grantRole(roleRepository.getReferenceById(RoleType.USER));
        }

        AdminAuthRoleResponse.RemoveRoleResponse date = new AdminAuthRoleResponse.RemoveRoleResponse(
                user.getUsername(),
                user.getPassword(),
                req.role(),
                Set.copyOf(user.getRoleTypes()),
                user.getUpdatedAt()
        );
        return ResponseDto.setSuccess("SUCCESS", date);
    }
}