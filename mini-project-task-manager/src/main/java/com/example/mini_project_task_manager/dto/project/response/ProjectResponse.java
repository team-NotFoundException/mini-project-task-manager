package com.example.mini_project_task_manager.dto.project.response;

import com.example.mini_project_task_manager.common.utils.DateUtils;
import com.example.mini_project_task_manager.entity.Project;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectResponse {

    /** 프로젝트 요약 응답 */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ProjectSummaryResponse(
            Long id,
            String title,
            String author,
            String createdAtKst,
            String createdUtcIso,
            String updatedAtKst,
            String updatedUtcIso
    ) {
        public static ProjectSummaryResponse from(Project project) {
            if (project == null) return null;

            return new ProjectSummaryResponse(
                    project.getId(),
                    project.getTitle(),
                    project.getUser().getNickname(),
                    DateUtils.toKstString(project.getCreatedAt()),
                    DateUtils.toUtcString(project.getCreatedAt()),
                    DateUtils.toKstString(project.getUpdatedAt()),
                    DateUtils.toUtcString(project.getUpdatedAt())
            );
        }
    }

    /** 프로젝트 상세 응답 */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ProjectDetailResponse(
            Long id,
            String title,
            String content,
            String author

    ) {
        public static ProjectDetailResponse from(Project project) {
            if (project == null) return null;

            return new ProjectDetailResponse(
                    project.getId(),
                    project.getTitle(),
                    project.getContent(),
                    project.getUser().getNickname()
            );
        }
    }
}
