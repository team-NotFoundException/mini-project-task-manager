package com.example.mini_project_task_manager.dto.task.response;

import com.example.mini_project_task_manager.common.enums.Priority;
import com.example.mini_project_task_manager.common.enums.Status;
import com.example.mini_project_task_manager.common.utils.DateUtils;
import com.example.mini_project_task_manager.dto.comment.response.CommentsResponse;
import com.example.mini_project_task_manager.dto.tasktag.response.TaskTagResponse;
import com.example.mini_project_task_manager.entity.Comment;
import com.example.mini_project_task_manager.entity.Task;
import com.example.mini_project_task_manager.entity.TaskTag;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class TaskResponse {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record TaskDetailResponse(
            Long id,
            String title,
            String content,
            String author,
            Status status,
            Priority priority,
            LocalDate dueDate,
            Set<TaskTagResponse> tags,
            List<CommentsResponse.CommentListResponse> comments,
            String createdAt,
            String updatedAt
    ) {
        public static TaskDetailResponse from(Task task) {
            if (task == null) return null;

            List<Comment> comments
                    = task.getComments() != null ? task.getComments() : Collections.emptyList();

            List<CommentsResponse.CommentListResponse> commentDtos = comments.stream()
                    .filter(Objects::nonNull)
                    .map(CommentsResponse.CommentListResponse::from)
                    .toList();
            Set<TaskTag> tags
                    = task.getTaskTags() != null? task.getTaskTags() : Collections.emptySet();
            Set<TaskTagResponse> taskTagsDtos = tags.stream()
                    .filter(Objects::nonNull)
                    .map(TaskTagResponse::from)
                    .collect(Collectors.toSet());
            return new TaskDetailResponse(
                    task.getId(),
                    task.getTitle(),
                    task.getContent(),
                    task.getUser() != null ? task.getUser().getNickname() : null,
                    task.getStatus(),
                    task.getPriority(),
                    task.getDueDate(),
                    taskTagsDtos,
                    commentDtos,
                    DateUtils.toKstString(task.getCreatedAt()),
                    DateUtils.toKstString(task.getUpdatedAt())
            );
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record TaskListResponse(
            Long id,
            String title,
            String author,
            Status status,
            Priority priority,
            LocalDate dueDate,
            String createdAt
    ) {
        public static TaskListResponse from(Task task) {
            if (task == null) return null;
            return new TaskListResponse(
                    task.getId(),
                    task.getTitle(),
                    task.getUser().getNickname(),
                    task.getStatus(),
                    task.getPriority(),
                    task.getDueDate(),
                    DateUtils.toKstString(task.getCreatedAt())
            );
        }
    }
}