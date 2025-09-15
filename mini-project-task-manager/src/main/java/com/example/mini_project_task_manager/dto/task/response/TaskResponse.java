package com.example.mini_project_task_manager.dto.task.response;

import com.example.mini_project_task_manager.common.enums.Priority;
import com.example.mini_project_task_manager.common.enums.Status;
import com.example.mini_project_task_manager.entity.Task;
import com.example.mini_project_task_manager.entity.TaskTag;

import java.time.LocalDateTime;
import java.util.Set;

public class TaskResponse {

    // Task 상세 보기
    public record TaskDetailResponse(
            Long id,
            //Long projectId,
            String title,
            String content,
            String author,
            Status status,
            Priority priority,
            Set<TaskTag> taskTags,
            LocalDateTime createdAt
    ) {
        public static TaskDetailResponse from(Task task) {
            if (task == null) return null;

            return new TaskDetailResponse(
                    task.getId(),
                    //task.getProject() != null ? task.getProject().getId() : null,
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

    public record TaskListResponse(
            Long id,
            String title,
            String content,
            Status status,
            Priority priority,
            Set<TaskTag> taskTags
    ) {
        public static TaskListResponse from(Task task) {
            if (task == null) return null;

            return new TaskListResponse(
                    task.getId(),
                    task.getTitle(),
                    task.getContent(),
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