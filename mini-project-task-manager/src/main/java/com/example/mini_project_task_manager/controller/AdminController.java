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

    @PutMapping("/roles/replace")
    public ResponseEntity<ResponseDto<AdminAuthRoleResponse.UpdateRolesResponse>> replaceRoles(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody AdminAuthRoleRequest.UpdateRolesRequest req ) {
        ResponseDto<AdminAuthRoleResponse.UpdateRolesResponse> response = adminService.replaceRoles(principal, req);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/roles/add")
    public ResponseEntity<ResponseDto<AdminAuthRoleResponse.AddRoleResponse>> addRole(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody AdminAuthRoleRequest.AddRoleRequest req
    ) {
        ResponseDto<AdminAuthRoleResponse.AddRoleResponse> response = adminService.addRole(principal, req);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/roles/remove")
    public ResponseEntity<ResponseDto<AdminAuthRoleResponse.RemoveRoleResponse>> removeRole(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody AdminAuthRoleRequest.RemoveRoleRequest req
    ) {
        ResponseDto<AdminAuthRoleResponse.RemoveRoleResponse> response = adminService.removeRole(principal, req);
        return ResponseEntity.ok().body(response);
    }
}
