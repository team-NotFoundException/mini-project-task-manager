package com.example.mini_project_task_manager.controller;

import com.example.mini_project_task_manager.common.constants.ApiMappingPattern;
import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.admin.request.AdminAuthRoleRequest;
import com.example.mini_project_task_manager.dto.admin.response.AdminAuthRoleResponse;
import com.example.mini_project_task_manager.security.UserPrincipal;
import com.example.mini_project_task_manager.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiMappingPattern.Admin.ROOT)
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final AdminService adminService;

    @PostMapping(ApiMappingPattern.Admin.ADD_ROLE)
    public ResponseEntity<ResponseDto<AdminAuthRoleResponse.AddRoleResponse>> addRoles(
            @AuthenticationPrincipal UserPrincipal Principal,
            @Valid @RequestBody AdminAuthRoleRequest.AddRoleRequest req ) {
        ResponseDto<AdminAuthRoleResponse.AddRoleResponse> response = adminService.addRoles(Principal, req);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(ApiMappingPattern.Admin.REMOVE_ROLE)
    public ResponseEntity<ResponseDto<AdminAuthRoleResponse.RemoveRoleResponse>> removeRoles(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody AdminAuthRoleRequest.RemoveRoleRequest req
    ) {
        ResponseDto<AdminAuthRoleResponse.RemoveRoleResponse> response = adminService.removeRoles(principal, req);
        return ResponseEntity.ok().body(response);
    }
}
