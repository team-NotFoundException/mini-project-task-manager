package com.example.mini_project_task_manager.DTO.commentDto.response;

import com.example.mini_project_task_manager.entity.Comment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponse {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CommentCreateResponse(
            Long id,
            Long taskId,
            String content,
            String author,
            LocalDateTime createdAt
    ) {
        public static CommentCreateResponse from(Comment comment) {
            if (comment == null) return null;

            return new CommentCreateResponse(
                    comment.getId(),
                    comment.getTask() != null ? comment.getTask().getId() : null,
                    comment.getContent(),
                    comment.getUser() != null ? comment.getUser().getNickname() : null,
                    comment.getCreatedAt()
            );
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public record CommentUpdateResponse(
                Long id,
                Long taskId,
                String content,
                LocalDateTime updatedAt

        ) {
            public static CommentUpdateResponse from(Comment comment) {
                if (comment == null) return null;

                return new CommentUpdateResponse(
                        comment.getId(),
                        comment.getTask() != null ? comment.getTask().getId() : null,
                        comment.getContent(),
                        comment.getUpdatedAt()
                );
            }
        }

    }
}
