package com.example.mini_project_task_manager.DTO.projectDto.response;

import com.example.mini_project_task_manager.entity.Project;

import java.time.LocalDateTime;

public class ProjectResponse {

    /** 프로젝트 요약 응답 */
    public record ProjectSummaryResponse(
            Long id,
            String title,
            String content,
            LocalDateTime createdAt
    ) {
        public static ProjectSummaryResponse from(Project project) {
            if (project == null) return null;

            return new ProjectSummaryResponse(
                    project.getId(),
                    project.getTitle(),
                    project.getContent(),
                    project.getCreatedAt()
            );
        }
    }
}
