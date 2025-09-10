package com.example.mini_project_task_manager.DTO.commentDto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CommentRequest {
    /** 댓글 생성 요청 DTO */
    public record CommentCreateRequest (
            @NotBlank(message = "댓글 입력은 필 수 입니다.")
            @Size(max = 300, message = "댓글은 300자를 넘길 수 없습니다.")
            String content
    ) {}

    /** 댓글 수정 요청 DTO */
    public record CommentUpdateRequest(
            @NotBlank(message = "댓글 내용은 공백일 수 없습니다.")
            @Size(max = 300, message = "댓글은 300자를 넘길 수 없습니다.")
            String content
    ) {}
}
