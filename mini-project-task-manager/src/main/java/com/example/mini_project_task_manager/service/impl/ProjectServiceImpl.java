package com.example.mini_project_task_manager.service.impl;

import com.example.mini_project_task_manager.common.enums.Sorted;
import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.project.request.ProjectRequest;
import com.example.mini_project_task_manager.dto.project.response.ProjectResponse;
import com.example.mini_project_task_manager.entity.Project;
import com.example.mini_project_task_manager.entity.User;
import com.example.mini_project_task_manager.repository.ProjectRepository;
import com.example.mini_project_task_manager.repository.UserRepository;
import com.example.mini_project_task_manager.security.UserPrincipal;
import com.example.mini_project_task_manager.service.ProjectService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Override
    @Transactional
    public ResponseDto<ProjectResponse.ProjectDetailResponse> createProject(UserPrincipal principal, ProjectRequest.ProjectCreateRequest request) {
        validateTitle(request.title());

        User author = userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("권한을 찾을 수가 없어요."));

        Project project = Project.builder()
                .title(request.title().trim())
                .content(request.content())
                .user(author)
                .build();

        Project saved = projectRepository.save(project);
        ProjectResponse.ProjectDetailResponse data = ProjectResponse.ProjectDetailResponse.from(saved);
        return ResponseDto.setSuccess("SUCCESS", data);
    }

    @Override
    public ResponseDto<List<ProjectResponse.ProjectSummaryResponse>> getAllProjectsOrderByCreatedAt(Sorted sortedBy) {
        List<ProjectResponse.ProjectSummaryResponse> data = null;

        data = projectRepository.findAllProjectsByCreatedAt(sortedBy).stream()
                .map(ProjectResponse.ProjectSummaryResponse::from)
                .toList();

        if (data.isEmpty()) throw new EntityNotFoundException("프로젝트를 찾을 수 없어요.");

        return ResponseDto.setSuccess("SUCCESS", data);
    }

    @Override
    public ResponseDto<List<ProjectResponse.ProjectSummaryResponse>> getProjectsByAuthorId(Long authorId) {
        List<ProjectResponse.ProjectSummaryResponse> data = null;

        if (authorId == null) throw new IllegalArgumentException("프로젝트를 불러올 수 없어요.");

        data = projectRepository.findProjectsByAuthorId(authorId).stream()
                .map(ProjectResponse.ProjectSummaryResponse::from)
                .toList();

        if (data.isEmpty()) throw new EntityNotFoundException("프로젝트를 찾을 수 없어요.");

        return ResponseDto.setSuccess("SUCCESS", data);
    }

    @Override
    public ResponseDto<List<ProjectResponse.ProjectSummaryResponse>> getProjectsByKeyword(String keyword) {
        String clean = (keyword == null) ? "" : keyword.trim();

        if (clean.isEmpty()) {
            throw new IllegalArgumentException("키워드는 비어 있을 수 없어요.");
        }

        if (clean.length() > 50) {
            throw new IllegalArgumentException("키워드는 50자 이하여야 해요.");
        }

        List<ProjectResponse.ProjectSummaryResponse> data = null;

        List<Project> projects = projectRepository.searchProjectsByKeyword(clean);

         data = projects.stream()
                .map(ProjectResponse.ProjectSummaryResponse::from)
                .toList();

         if (data.isEmpty()) throw new IllegalArgumentException("프로젝트를 찾을 수 없어요.");

        return ResponseDto.setSuccess("SUCCESS", data);
    }

    @Override
    @Transactional
    public ResponseDto<ProjectResponse.ProjectDetailResponse> updateProject(UserPrincipal principal, Long projectId, ProjectRequest.@Valid ProjectUpdateRequest request) {
        ProjectResponse.ProjectDetailResponse data = null;

        validateTitle(request.title());

        User author = userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (projectId == null) throw new IllegalArgumentException("프로젝트를 불러올 수 없어요.");

        Project project = projectRepository.findProjectById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없어요."));

        project.setTitle(request.title().trim());

        project.setContent(request.content() == null ? "" :request.content().trim());

        projectRepository.flush();

        data = ProjectResponse.ProjectDetailResponse.from(project);

        return ResponseDto.setSuccess("SUCCESS", data);
    }

    @Override
    @Transactional
    public ResponseDto<Void> deleteProject(UserPrincipal principal, Long projectId) {
        User author = userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (projectId == null) throw new IllegalArgumentException("프로젝트를 불러올 수 없어요.");

        Project project = projectRepository.findProjectById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 프로젝트가 없어요."));
        projectRepository.delete(project);

        return ResponseDto.setSuccess("SUCCESS", null);
    }

    private void validateTitle(String title) {
        if (!StringUtils.hasText(title)) {
            throw new IllegalArgumentException("제목은 빌 수 없어요.");
        }
    }
}