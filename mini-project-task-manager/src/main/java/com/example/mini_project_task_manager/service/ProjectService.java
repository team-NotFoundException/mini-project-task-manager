package com.example.mini_project_task_manager.service;

import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.project.request.ProjectRequest;
import com.example.mini_project_task_manager.dto.project.response.ProjectResponse;
import com.example.mini_project_task_manager.security.UserPrincipal;
import jakarta.validation.Valid;

import java.util.List;

public interface ProjectService {
    ResponseDto<ProjectResponse.ProjectDetailResponse> createProject(UserPrincipal principal, ProjectRequest.ProjectCreateRequest request);

    ResponseDto<List<ProjectResponse.ProjectSummaryResponse>> getAllProjectsOrderByCreatedAtDesc();

    ResponseDto<List<ProjectResponse.ProjectSummaryResponse>> getAllProjectsOrderByCreatedAtAsc();

    ResponseDto<List<ProjectResponse.ProjectSummaryResponse>> getProjectsByAuthorId(Long authorId);

//    ResponseDto<ProjectResponse.ProjectDetailResponse> getProjectByTitle(String title);

    ResponseDto<List<ProjectResponse.ProjectSummaryResponse>> getProjectsByKeyword(String keyword);

    ResponseDto<Void> deleteProject(UserPrincipal principal, Long id);

    ResponseDto<ProjectResponse.ProjectDetailResponse> updateProject(UserPrincipal principal, Long id, ProjectRequest.@Valid ProjectUpdateRequest request);
}
