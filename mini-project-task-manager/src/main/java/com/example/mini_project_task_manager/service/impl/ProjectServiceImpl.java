package com.example.mini_project_task_manager.service.impl;

import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.project.request.ProjectRequest;
import com.example.mini_project_task_manager.dto.project.response.ProjectResponse;
import com.example.mini_project_task_manager.entity.Project;
import com.example.mini_project_task_manager.entity.User;
import com.example.mini_project_task_manager.repository.ProjectRepository;
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
    private final ProjectRepository projectRepository;

    @Override
    @Transactional
    public ResponseDto<ProjectResponse.ProjectDetailResponse> createProject(UserPrincipal principal, ProjectRequest.ProjectCreateRequest request) {

        validateTitle(request.title());

        User author = userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Project project = Project.builder()
                .title(request.title())
                .content(request.content())
                .build();

        Project saved = projectRepository.save(project);

        ProjectResponse.ProjectDetailResponse result = ProjectResponse.ProjectDetailResponse.from(saved);

        return ResponseDto.setSuccess("프로젝트가 생성되었습니다.", result);
    }

    @Override
    public ResponseDto<List<ProjectResponse.ProjectSummaryResponse>> getAllProjectsOrderByCreatedAt(boolean sortedBy) {
        List<Project> projects = projectRepository.findAllProjectsByCreatedAt(sortedBy);

        List<ProjectResponse.ProjectSummaryResponse> result = projects.stream()
                .map(ProjectResponse.ProjectSummaryResponse::from)
                .toList();

        return ResponseDto.setSuccess("조회 완료", result);
    }


    @Override
    public ResponseDto<List<ProjectResponse.ProjectSummaryResponse>> getProjectsByAuthorId(Long authorId) {
        List<Project> projects = projectRepository.findProjectsByAuthorId(authorId);

        if (authorId == null) throw new IllegalArgumentException("로그인 후 이용해주세요.");

        List<ProjectResponse.ProjectSummaryResponse> result = projects.stream()
                .map(ProjectResponse.ProjectSummaryResponse::from)
                .toList();

        if (result.isEmpty()) throw new IllegalArgumentException("프로젝트가 없습니다.");

        return ResponseDto.setSuccess("조회 완료", result);
    }


    @Override
    public ResponseDto<List<ProjectResponse.ProjectSummaryResponse>> getProjectsByKeyword(String keyword) {

        String clean = (keyword == null) ? " " : keyword.trim();

        if (clean.isEmpty()) {
            throw new IllegalArgumentException("검색 키워드는 비어 있을 수 없습니다.");
        }

        if (clean.length() > 100) {
            throw new IllegalArgumentException("검색 키워드는 100자 이하여야 합니다.");
        }

        var rows = projectRepository.searchProjectsByKeyword(clean);

        List<ProjectResponse.ProjectSummaryResponse> result = rows.stream()
                .map(ProjectResponse.ProjectSummaryResponse::from)
                .toList();

        return ResponseDto.setSuccess("조회 완료", result);
    }

    @Override
    @Transactional
    public ResponseDto<ProjectResponse.ProjectDetailResponse> updateProject(UserPrincipal principal, Long projectId, ProjectRequest.@Valid ProjectUpdateRequest request) {
        validateTitle(request.title());

        User author = userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (projectId == null) throw new IllegalArgumentException("프로젝트 아이디가 필요합니다.");

        Project project = projectRepository.findProjectById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없습니다."));

        project.setTitle(request.title().trim());
        project.setContent(request.content().trim());

        projectRepository.flush();
        ProjectResponse.ProjectDetailResponse data = ProjectResponse.ProjectDetailResponse.from(project);

        return ResponseDto.setSuccess("업데이트 완료", data);
    }

    @Override
    @Transactional
    public ResponseDto<Void> deleteProject(UserPrincipal principal, Long projectId) {

        User author = userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (projectId == null) throw new IllegalArgumentException("프로젝트 아이디가 필요합니다.");

        Project project = projectRepository.findProjectById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없습니다."));

        projectRepository.delete(project);

        return ResponseDto.setSuccess("프로젝트가 삭제되었습니다.", null);
    }

    private void validateTitle(String title) {
        throw new IllegalArgumentException("제목을 입력해주세요");
    }
}