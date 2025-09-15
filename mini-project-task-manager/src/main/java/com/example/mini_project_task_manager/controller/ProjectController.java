package com.example.mini_project_task_manager.controller;


import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.project.request.ProjectRequest;
import com.example.mini_project_task_manager.dto.project.response.ProjectResponse;
import com.example.mini_project_task_manager.security.UserPrincipal;
import com.example.mini_project_task_manager.service.ProjectService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody ProjectRequest.ProjectCreateRequest request
            ) {
        ResponseDto<ProjectResponse.ProjectSummaryResponse> response = projectService.createProject(principal, request);
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

    // 4) 프로젝트 조회 (단건 조회)
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<ProjectResponse.ProjectDetailResponse>> getProjectById(
            @PathVariable Long id
    ) {
        ResponseDto<ProjectResponse.ProjectDetailResponse> response = projectService.getProjectById();
        return ResponseEntity.ok().body(response);
    }

    // 5) 프로젝트 조회 (키워드 조회)
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping("/search-project")
    public ResponseEntity<ResponseDto<List<ProjectResponse.ProjectSummaryResponse>>> getProjectsByKeyword(
            @RequestParam("keyword") @NotBlank(message = "검색 키워드는 비어있을 수 없습니다.") String keyword
    ) {
        ResponseDto<List<ProjectResponse.ProjectSummaryResponse>> response = projectService.getProjectsByKeyword();
        return ResponseEntity.ok().body(response);

    }

    // 5) 프로젝트 수정
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<ProjectResponse.ProjectDetailResponse>> updateProject(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long id,
            @Valid @RequestBody ProjectRequest.ProjectUpdateRequest request
            ) {
        ResponseDto<ProjectResponse.ProjectDetailResponse> response = projectService.updateProject();
        return ResponseEntity.ok().body(response);
    }

    // 6) 프로젝트 삭제
    @PreAuthorize(("hasAnyRole('MANAGER', 'ADMIN')"))
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteProject(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long id
    ) {
        ResponseDto<Void> response = projectService.deleteProject(principal, id);
        return ResponseEntity.ok().body(response);
    }
}
