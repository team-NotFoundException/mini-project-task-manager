package com.example.mini_project_task_manager.dto.comment.response;

import com.example.mini_project_task_manager.entity.Comment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public class CommentsResponse {
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record CommentResponse(
                Long id,
                Long taskId,
                String content,
                String author,
                LocalDateTime createdAt,
                LocalDateTime updatedAt
        ) {
            public static CommentResponse from(Comment comment) {
                if (comment == null) return null;

                return new CommentResponse(
                        comment.getId(),
                        comment.getTask() != null ? comment.getTask().getId() : null,
                        comment.getComment(),
                        comment.getUser() != null ? comment.getUser().getNickname() : null,
                        comment.getCreatedAt(),
                        comment.getUpdatedAt()
                );
            }
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public record CommentListResponse(
                Long taskId,
                String content,
                String author
        ) {
            public static CommentListResponse from(Comment comment) {
                if (comment == null) return null;

                return new CommentListResponse(
                        comment.getTask() != null ? comment.getTask().getId() : null,
                        comment.getComment(),
                        comment.getUser() != null ? comment.getUser().getNickname() : null
                );
            }
        }
    }

