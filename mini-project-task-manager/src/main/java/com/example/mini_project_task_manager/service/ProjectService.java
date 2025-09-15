package com.example.mini_project_task_manager.service;

import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.project.request.ProjectRequest;
import com.example.mini_project_task_manager.dto.project.response.ProjectResponse;
import com.example.mini_project_task_manager.security.UserPrincipal;

import java.util.List;

public interface ProjectService {
    ResponseDto<ProjectResponse.ProjectSummaryResponse> createProject(UserPrincipal principal, ProjectRequest.ProjectCreateRequest request);

    ResponseDto<List<ProjectResponse.ProjectSummaryResponse>> getAllProjects();

    ResponseDto<List<ProjectResponse.ProjectSummaryResponse>> getProjectsByAuthorId(Long authorId);

    ResponseDto<ProjectResponse.ProjectDetailResponse> getProjectById();

    ResponseDto<List<ProjectResponse.ProjectSummaryResponse>> getProjectsByKeyword();

    ResponseDto<ProjectResponse.ProjectDetailResponse> updateProject();

    ResponseDto<Void> deleteProject(UserPrincipal principal, Long id);

}
