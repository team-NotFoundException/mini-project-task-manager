package com.example.mini_project_task_manager.dto.notification.response;

import com.example.mini_project_task_manager.entity.Notification;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotiResponse {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record NotiCreateResponse(
            Long id,
            String title,
            String content,
            String author,
            LocalDateTime createdAt
    ) {
        public static NotiCreateResponse from(Notification notification) {
            if (notification == null) return null;

            return new NotiCreateResponse(
                    notification.getId(),
                    notification.getTitle(),
                    notification.getContent(),
                    notification.getUser() != null ? notification.getUser().getNickname() : null,
                    notification.getCreatedAt()
            );
        }
    }
}
