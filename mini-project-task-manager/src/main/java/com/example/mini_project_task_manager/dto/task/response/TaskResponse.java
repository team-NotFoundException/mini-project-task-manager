package com.example.mini_project_task_manager.dto.task.response;

import com.example.mini_project_task_manager.common.enums.Priority;
import com.example.mini_project_task_manager.common.enums.Status;
import com.example.mini_project_task_manager.entity.Task;
import com.example.mini_project_task_manager.entity.TaskTag;

import java.time.LocalDateTime;
import java.util.Set;

public class TaskResponse {

    // Task 처음 생성후 단건 조회
    public record TaskDetailResponse(
            Long id,
            String title,
            String content,
            String author,
            Status status,
            Priority priority,
            Set<TaskTag> tags, // 여기 좀 찾아봐야함.
            LocalDateTime createdAt
    ) {
        public static TaskDetailResponse from(Task task) {
            if (task == null) return null;

            return new TaskDetailResponse(
                    task.getId(),
                    task.getTitle(),
                    task.getContent(),
                    task.getUser() != null ? task.getUser().getNickname() : null,
                    task.getStatus(),
                    task.getPriority(),
                    task.getTaskTags() != null ? task.getTaskTags() : null,
                    task.getCreatedAt()
            );
        }
    }

    // Task 수정후 단건 조회
    public record TaskUpdatedDetailResponse(
            Long id,
            String title,
            String content,
            String author,
            Status status,
            Priority priority,
            Set<TaskTag> tags, // 여기 좀 찾아봐야함.
            LocalDateTime updatedAt
    ) {
        public static TaskUpdatedDetailResponse from(Task task) {
            if (task == null) return null;

            return new TaskUpdatedDetailResponse(
                    task.getId(),
                    task.getTitle(),
                    task.getContent(),
                    task.getUser() != null ? task.getUser().getNickname() : null,
                    task.getStatus(),
                    task.getPriority(),
                    task.getTaskTags() != null ? task.getTaskTags() : null,
                    task.getUpdatedAt()
            );
        }
    }

    // Task 전체 조회
    public record TaskListResponse(
            Long id,
            String title,
            Status status,
            Priority priority,
            Set<TaskTag> tags // 여기 좀 찾아봐야함.
    ) {
        public static TaskListResponse from(Task task) {
            if (task == null) return null;

            return new TaskListResponse(
                    task.getId(),
                    task.getTitle(),
                    task.getStatus(),
                    task.getPriority(),
                    task.getTaskTags()
            );
        }
    }
}
//

/*
    null 체크로 NullPointerException 방지
 */