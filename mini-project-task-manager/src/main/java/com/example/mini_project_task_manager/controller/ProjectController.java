package com.example.mini_project_task_manager.controller;


import com.example.mini_project_task_manager.DTO.ResponseDto;
import com.example.mini_project_task_manager.DTO.projectDto.request.ProjectRequest;
import com.example.mini_project_task_manager.DTO.projectDto.response.ProjectResponse;
import com.example.mini_project_task_manager.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    // 1) 프로젝트 생성
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<ResponseDto<ProjectResponse.ProjectSummaryResponse>> createProject(
            @Valid @RequestBody ProjectRequest.ProjectCreateRequest request
            ) {
        ResponseDto<ProjectResponse.ProjectSummaryResponse> response = projectService.createProject(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2) 프로젝트 조회 (전체 조회)
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<ProjectResponse.ProjectSummaryResponse>>> getAllProjects() {
        ResponseDto<List<ProjectResponse.ProjectSummaryResponse>> response = projectService.getAllProjects();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 3) 프로젝트 조회 (작성자 id로 조회)
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping("/{author_id}")
    public ResponseEntity<ResponseDto<List<ProjectResponse.ProjectSummaryResponse>>> getProjectsByAuthorId(
            @PathVariable Long author_id
    ) {
        ResponseDto<List<ProjectResponse.ProjectSummaryResponse>> response = projectService.getProjectsByAuthorId(author_id);
        return ResponseEntity.ok().body(response);
    }


}
