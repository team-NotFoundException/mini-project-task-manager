package com.example.mini_project_task_manager.dto.notification.response;

import com.example.mini_project_task_manager.common.utils.DateUtils;
import com.example.mini_project_task_manager.entity.Notification;
import com.example.mini_project_task_manager.entity.User;
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
            String username,
            String createdAtKst,
            String createdAtUtcIso
    ) {
        public static NotificationDetailResponse from(Notification notification) {
            if (notification == null) return null;
            return new NotificationDetailResponse(
                    notification.getId(),
                    notification.getTitle(),
                    notification.getContent(),
                    notification.getAuthor().getUsername() != null ? notification.getAuthor().getUsername() : null,
                    DateUtils.toKstString(notification.getCreatedAt()),
                    DateUtils.toUtcString(notification.getCreatedAt())
            );
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record NotificationListResponse(
            Long id,
            String title,
            String createdAtKst,
            String createdAtUtcIso
        ) {
        public static NotificationListResponse from(Notification notification) {
            if (notification == null) return null;
            return new NotificationListResponse(
                    notification.getId(),
                    notification.getTitle(),
                    DateUtils.toKstString(notification.getCreatedAt()),
                    DateUtils.toUtcString(notification.getCreatedAt())
            );
        }
    }
}
