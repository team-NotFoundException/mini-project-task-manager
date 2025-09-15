package com.example.mini_project_task_manager.service.impl;

import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.project.request.ProjectRequest;
import com.example.mini_project_task_manager.dto.project.response.ProjectResponse;
import com.example.mini_project_task_manager.security.UserPrincipal;
import com.example.mini_project_task_manager.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {
    @Override
    public ResponseDto<ProjectResponse.ProjectSummaryResponse> createProject(UserPrincipal principal, ProjectRequest.ProjectCreateRequest request) {
        return null;
    }

    @Override
    public ResponseDto<List<ProjectResponse.ProjectSummaryResponse>> getAllProjects() {
        return null;
    }

    @Override
    public ResponseDto<List<ProjectResponse.ProjectSummaryResponse>> getProjectsByAuthorId(Long authorId) {
        return null;
    }

    @Override
    public ResponseDto<ProjectResponse.ProjectDetailResponse> getProjectById() {
        return null;
    }

    @Override
    public ResponseDto<List<ProjectResponse.ProjectSummaryResponse>> getProjectsByKeyword() {
        return null;
    }

    @Override
    public ResponseDto<ProjectResponse.ProjectDetailResponse> updateProject() {
        return null;
    }

    @Override
    public ResponseDto<Void> deleteProject(UserPrincipal principal, Long id) {
        return null;
    }

}
