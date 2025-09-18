package com.example.mini_project_task_manager.dto.comment.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CommentRequest {
    /** 댓글 생성 요청 DTO */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CommentCreateRequest (
            @NotBlank(message = "댓글을 작성해주세요.")
            @Size(max = 300, message = "댓글은 300자 이하여야 해요.")
            String comment
    ) {}

    /** 댓글 수정 요청 DTO */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CommentUpdateRequest(
            @NotBlank(message = "댓글 입력은 필수에요.")
            @Size(max = 300, message = "댓글은 300자 이하여야 해요.")
            String comment
    ) {}
}
