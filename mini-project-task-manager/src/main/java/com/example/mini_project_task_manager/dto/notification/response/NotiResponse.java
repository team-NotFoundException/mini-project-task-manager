package com.example.mini_project_task_manager.dto.notification.response;

import com.example.mini_project_task_manager.dto.pagenation.DomainSummarizable;
import com.example.mini_project_task_manager.dto.pagenation.PageMetaResponse;
import com.example.mini_project_task_manager.entity.Notification;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.hibernate.query.Page;

import java.time.LocalDateTime;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotiResponse extends PageMetaResponse {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record NotiDetailResponse(
            Long id,
            String title,
            String content,
            String author,
            LocalDateTime createdAt
    ) {
        public static NotiDetailResponse from(Notification notification) {
            if (notification == null) return null;

            return new NotiDetailResponse(
                    notification.getId(),
                    notification.getTitle(),
                    notification.getContent(),
                    notification.getUser() != null ? notification.getUser().getNickname() : null,
                    notification.getCreatedAt()
            );
        }
    }

    public record NotiListResponse(
            Long id,
            String title,
            LocalDateTime createdAt
        ) implements DomainSummarizable {
        public static NotiListResponse from(Notification notification) {
            if (notification == null) return null;

            return new NotiListResponse(
                    notification.getId(),
                    notification.getTitle(),
                    notification.getCreatedAt()
            );
        }

        // DomainSummarizable 재정의
        @Override
        public Long getId() {return id;}
        @Override
        public String getTitle() {return "";}
        @Override
        public LocalDateTime getCreatedAt() {return createdAt;}
    }

}
