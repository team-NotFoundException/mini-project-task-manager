package com.example.mini_project_task_manager.dto.notification.response;

import com.example.mini_project_task_manager.entity.Notification;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotiResponse {

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
            String title
        ) {
        public static NotiListResponse from(Notification notification) {
            if (notification == null) return null;

            return new NotiListResponse(
                    notification.getId(),
                    notification.getTitle()
            );
        }
    }
}
