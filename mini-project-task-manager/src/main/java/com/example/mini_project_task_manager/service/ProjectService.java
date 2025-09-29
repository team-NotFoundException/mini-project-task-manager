package com.example.mini_project_task_manager.service;

import com.example.mini_project_task_manager.common.enums.Sorted;
import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.project.request.ProjectRequest;
import com.example.mini_project_task_manager.dto.project.response.ProjectResponse;
import com.example.mini_project_task_manager.security.UserPrincipal;
import jakarta.validation.Valid;

import java.util.List;

public interface ProjectService {
    ResponseDto<ProjectResponse.ProjectDetailResponse> createProject(UserPrincipal principal, ProjectRequest.ProjectCreateRequest request);
    ResponseDto<List<ProjectResponse.ProjectSummaryResponse>> getAllProjectsOrderByCreatedAt(Sorted sortedBy);
    ResponseDto<List<ProjectResponse.ProjectSummaryResponse>> getProjectsByAuthorId(Long authorId);
    ResponseDto<List<ProjectResponse.ProjectSummaryResponse>> getProjectsByKeyword(String keyword);
    ResponseDto<Void> deleteProject(UserPrincipal principal, Long projectId);
    ResponseDto<ProjectResponse.ProjectDetailResponse> updateProject(UserPrincipal principal, Long projectId, ProjectRequest.@Valid ProjectUpdateRequest request);
}
