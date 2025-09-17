package com.example.mini_project_task_manager.controller;

import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.admin.request.AdminAuthRoleRequest;
import com.example.mini_project_task_manager.dto.admin.response.AdminAuthRoleResponse;
import com.example.mini_project_task_manager.dto.user.request.RoleModifyRequest;
import com.example.mini_project_task_manager.security.UserPrincipal;
import com.example.mini_project_task_manager.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vi/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final AdminService adminService;

    @PutMapping("/roles/users")
    public ResponseEntity<ResponseDto<AdminAuthRoleResponse.UpdateRolesResponse>> usersRoles(
            @AuthenticationPrincipal UserPrincipal Principal,
            @Valid @RequestBody AdminAuthRoleRequest.UpdateRolesRequest req ) {
        ResponseDto<AdminAuthRoleResponse.UpdateRolesResponse> response = adminService.usersRoles(Principal, req);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/roles/manager")
    public ResponseEntity<ResponseDto<AdminAuthRoleResponse.RemoveRoleResponse>> managerRoles(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody AdminAuthRoleRequest.RemoveRoleRequest req
    ) {
        ResponseDto<AdminAuthRoleResponse.RemoveRoleResponse> response = adminService.managerRoles(principal, req);
        return ResponseEntity.ok().body(response);
    }
}
