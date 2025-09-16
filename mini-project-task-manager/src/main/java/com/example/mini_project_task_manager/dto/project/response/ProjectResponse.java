package com.example.mini_project_task_manager.dto.project.response;

import com.example.mini_project_task_manager.entity.Project;
import com.example.mini_project_task_manager.entity.User;

import java.time.LocalDateTime;

public class ProjectResponse {

    /** 프로젝트 요약 응답 */
    public record ProjectSummaryResponse(
            Long id,
            String title,
            String author,
            LocalDateTime createdAt

    ) {
        public static ProjectSummaryResponse from(Project project) {
            if (project == null) return null;

            return new ProjectSummaryResponse(
                    project.getId(),
                    project.getTitle(),
                    project.getUser().getNickname(),
                    project.getCreatedAt()

            );
        }
    }

    public record ProjectDetailResponse(
            Long id,
            String title,
            String content,
            String author,
            LocalDateTime createdAt

    ) {
        public static ProjectDetailResponse from(Project project) {
            if (project == null) return null;

            return new ProjectDetailResponse(
                    project.getId(),
                    project.getTitle(),
                    project.getContent(),
                    project.getUser().getNickname(),
                    project.getCreatedAt()

            );
        }
    }
}
