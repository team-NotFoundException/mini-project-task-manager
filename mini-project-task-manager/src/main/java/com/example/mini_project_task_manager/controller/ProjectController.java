package com.example.mini_project_task_manager.controller;


import com.example.mini_project_task_manager.common.constants.ApiMappingPattern;
import com.example.mini_project_task_manager.common.enums.Sorted;
import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.project.request.ProjectRequest;
import com.example.mini_project_task_manager.dto.project.response.ProjectResponse;
import com.example.mini_project_task_manager.security.UserPrincipal;
import com.example.mini_project_task_manager.service.ProjectService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.Projects.ROOT)
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<ResponseDto<ProjectResponse.ProjectDetailResponse>> createProject(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody ProjectRequest.ProjectCreateRequest request
            ) {
        ResponseDto<ProjectResponse.ProjectDetailResponse> response = projectService.createProject(principal, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping(ApiMappingPattern.Projects.SORTED)
    public ResponseEntity<ResponseDto<List<ProjectResponse.ProjectSummaryResponse>>> getAllProjectsOrderByCreatedAt(
            @RequestParam("sortedBy") Sorted sortedBy
    ) {
        ResponseDto<List<ProjectResponse.ProjectSummaryResponse>> response = projectService.getAllProjectsOrderByCreatedAt(sortedBy);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping(ApiMappingPattern.Projects.MY_PROJECT)
    public ResponseEntity<ResponseDto<List<ProjectResponse.ProjectSummaryResponse>>> getProjectsByAuthorId(
            @PathVariable("authorId") @Positive(message = "authorId는 1이상이어야 합니다.") Long authorId
    ) {
        ResponseDto<List<ProjectResponse.ProjectSummaryResponse>> response = projectService.getProjectsByAuthorId(authorId);
        return ResponseEntity.ok().body(response);
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping(ApiMappingPattern.Projects.SEARCH)
    public ResponseEntity<ResponseDto<List<ProjectResponse.ProjectSummaryResponse>>> getProjectsByKeyword(
            @RequestParam("keyword") String keyword
    ) {
        ResponseDto<List<ProjectResponse.ProjectSummaryResponse>> response = projectService.getProjectsByKeyword(keyword);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PutMapping(ApiMappingPattern.Projects.BY_ID)
    public ResponseEntity<ResponseDto<ProjectResponse.ProjectDetailResponse>> updateProject(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable("projectId") @Positive(message = "projectId는 1이상이어야 합니다.") Long projectId,
            @RequestBody ProjectRequest.ProjectUpdateRequest request
            ) {
        ResponseDto<ProjectResponse.ProjectDetailResponse> response = projectService.updateProject(principal, projectId, request);
        return ResponseEntity.ok().body(response);
    }

    @PreAuthorize(("hasAnyRole('MANAGER', 'ADMIN')"))
    @DeleteMapping(ApiMappingPattern.Projects.BY_ID)
    public ResponseEntity<ResponseDto<Void>> deleteProject(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable("projectId") Long projectId
    ) {
        ResponseDto<Void> response = projectService.deleteProject(principal, projectId);
        return ResponseEntity.ok().body(response);
    }
}
