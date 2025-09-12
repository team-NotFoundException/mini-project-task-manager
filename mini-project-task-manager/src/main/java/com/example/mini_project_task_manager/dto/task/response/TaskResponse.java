package com.example.mini_project_task_manager.dto.task.response;

import com.example.mini_project_task_manager.entity.Task;
import java.time.LocalDateTime;

public class TaskResponse {

    public record TaskCreateResponse(
            Long id,
            Long projectId,
            String title,
            String content,
            String author,
            LocalDateTime createdAt
    ) {
        public static TaskCreateResponse from(Task task) {
            if (task == null) return null;

            return new TaskCreateResponse(
                    task.getId(),
                    task.getProject() != null ? task.getProject().getId() : null,
                    task.getTitle(),
                    task.getContent(),
                    task.getUser() != null ? task.getUser().getNickname() : null,
                    task.getCreatedAt()
            );
        }
    }

    public record TaskUpdateResponse(
            Long id,
            Long ProjectId,
            String title,
            String content,
            LocalDateTime updatedAt
    ) {
        public static TaskUpdateResponse from(Task task) {
            if (task == null) return null;

            return new TaskUpdateResponse(
                    task.getId(),
                    task.getProject() != null ? task.getProject().getId() : null,
                    task.getTitle(),
                    task.getContent(),
                    task.getUpdatedAt()
            );
        }
    }
}

/*
    null 체크로 NullPointerException 방지
 */