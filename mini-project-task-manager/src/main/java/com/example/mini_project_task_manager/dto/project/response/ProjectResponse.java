package com.example.mini_project_task_manager.dto.project.response;

import com.example.mini_project_task_manager.entity.Project;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
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

    /** 프로젝트 상세 응답 */
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
