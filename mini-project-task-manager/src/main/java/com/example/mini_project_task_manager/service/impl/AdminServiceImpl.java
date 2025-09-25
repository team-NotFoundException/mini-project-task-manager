package com.example.mini_project_task_manager.service.impl;

import com.example.mini_project_task_manager.common.enums.RoleType;
import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.admin.request.AdminAuthRoleRequest;
import com.example.mini_project_task_manager.dto.admin.response.AdminAuthRoleResponse;
import com.example.mini_project_task_manager.entity.Role;
import com.example.mini_project_task_manager.entity.User;
import com.example.mini_project_task_manager.repository.RoleRepository;
import com.example.mini_project_task_manager.repository.UserRepository;
import com.example.mini_project_task_manager.security.UserPrincipal;
import com.example.mini_project_task_manager.service.AdminService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    // 여러 권한 추가
    @Override
    @Transactional
    public ResponseDto<AdminAuthRoleResponse.AddRoleResponse> addRoles(UserPrincipal principal, AdminAuthRoleRequest.@Valid AddRoleRequest req) {
        User user = userRepository.findByUsername(req.username())
                .orElseThrow(() -> new EntityNotFoundException("해당 유저의 사용자가 없습니다."));
        Role role = roleRepository.findById(req.role())
                .orElseThrow(() -> new EntityNotFoundException("해당 권한을 찾을 수 없어요."));

        user.grantRole(role);

        userRepository.flush();

        AdminAuthRoleResponse.AddRoleResponse data = new AdminAuthRoleResponse.AddRoleResponse(
                user.getUsername(),
                req.role(),
                Set.copyOf(user.getRoleTypes()),
                user.getUpdatedAt()
        );
        return ResponseDto.setSuccess("SUCCESS", data);
    }

    @Override
    @Transactional
    public ResponseDto<AdminAuthRoleResponse.RemoveRoleResponse> removeRoles(UserPrincipal principal, AdminAuthRoleRequest.@Valid RemoveRoleRequest req) {
        User user = userRepository.findByUsername(req.username())
                .orElseThrow(() -> new EntityNotFoundException("해당 유저의 사용자가 없어요"));
        Role role = roleRepository.findById(req.role())
                .orElseThrow(() -> new EntityNotFoundException("해당 권한을 찾을 수 없어요"));
        user.revokeRole(role);

        userRepository.flush();

        if (user.getUserRoles().isEmpty()) {
            user.grantRole(roleRepository.getReferenceById(RoleType.USER));
        }

        AdminAuthRoleResponse.RemoveRoleResponse data = new AdminAuthRoleResponse.RemoveRoleResponse(
                user.getUsername(),
                req.role(),
                Set.copyOf(user.getRoleTypes()),
                user.getUpdatedAt()
        );
        return ResponseDto.setSuccess("SUCCESS", data);
    }


}


