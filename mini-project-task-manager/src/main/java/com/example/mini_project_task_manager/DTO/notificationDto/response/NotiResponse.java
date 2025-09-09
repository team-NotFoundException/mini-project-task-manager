package com.example.mini_project_task_manager.DTO.notificationDto.response;

import com.example.mini_project_task_manager.entity.Notification;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

public class NotiResponse {

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record NotiCreateResponse(
            Long id,
            Long projectId,
            String title,
            String content,
            String author,
            LocalDateTime createdAt
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record NotiUpdateResponse(
            Long id,
            Long projectId,
            String title,
            String content,
            LocalDateTime updatedAt
    ) {
        public static NotiUpdateResponse from(Notification notification) {
            if (notification == null) return null;

            return new NotiUpdateResponse(
                    notification.getId(),
                    notification.getProject() != null? notification.getProject().getId() : null,
                    notification.getTitle(),
                    notification.getContent(),
                    notification.getUpdatedAt()
            );
        }
    }
}
