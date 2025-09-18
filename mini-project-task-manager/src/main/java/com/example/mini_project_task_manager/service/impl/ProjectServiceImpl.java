package com.example.mini_project_task_manager.service.impl;

import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.project.request.ProjectRequest;
import com.example.mini_project_task_manager.dto.project.response.ProjectResponse;
import com.example.mini_project_task_manager.entity.User;
import com.example.mini_project_task_manager.repository.UserRepository;
import com.example.mini_project_task_manager.security.UserPrincipal;
import com.example.mini_project_task_manager.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {
    private final UserRepository userRepository;

    @Override
    public ResponseDto<ProjectResponse.ProjectDetailResponse> createProject(UserPrincipal principal, ProjectRequest.ProjectCreateRequest request) {

        validateTitle(request.title());

        final String loginId = principal.getUsername();
        User author = userRepository.findByUsername(loginId)
                .orElseThrow(() -> new IllegalArgumentException("AUTHOR_NOT_FOUND"));

        return null;
    }

    @Override
    public ResponseDto<List<ProjectResponse.ProjectSummaryResponse>> getAllProjectsOrderByCreatedAt(boolean sortedBy) {
        return null;
    }


    @Override
    public ResponseDto<List<ProjectResponse.ProjectSummaryResponse>> getProjectsByAuthorId(Long authorId) {
        return null;
    }


    @Override
    public ResponseDto<List<ProjectResponse.ProjectSummaryResponse>> getProjectsByKeyword(String keyword) {
        return null;
    }

    @Override
    public ResponseDto<ProjectResponse.ProjectDetailResponse> updateProject(UserPrincipal principal, Long projectId, ProjectRequest.@Valid ProjectUpdateRequest request) {
        return null;
    }

    @Override
    public ResponseDto<Void> deleteProject(UserPrincipal principal, Long projectId) {
        return null;
    }

    private void validateTitle(String title) {
        throw new IllegalArgumentException("TITLE_REQUIRED");
    }
}