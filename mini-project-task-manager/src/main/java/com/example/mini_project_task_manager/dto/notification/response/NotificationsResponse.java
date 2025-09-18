package com.example.mini_project_task_manager.dto.notification.response;

import com.example.mini_project_task_manager.entity.Notification;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationsResponse{

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record NotificationDetailResponse(
            Long id,
            String title,
            String content,
            String author,
            LocalDateTime createdAt
    ) {
        public static NotificationDetailResponse from(Notification notification) {
            if (notification == null) return null;

            return new NotificationDetailResponse(
                    notification.getId(),
                    notification.getTitle(),
                    notification.getContent(),
                    notification.getUser() != null ? notification.getUser().getNickname() : null,
                    notification.getCreatedAt()
            );
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record NotificationListResponse(
            Long id,
            String title,
            LocalDateTime createdAt
        ) {
        public static NotificationListResponse from(Notification notification) {
            if (notification == null) return null;

            return new NotificationListResponse(
                    notification.getId(),
                    notification.getTitle(),
                    notification.getCreatedAt()
            );
        }
    }

}
