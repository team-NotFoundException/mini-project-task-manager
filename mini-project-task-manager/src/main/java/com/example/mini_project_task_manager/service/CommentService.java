package com.example.mini_project_task_manager.service;

import com.example.mini_project_task_manager.DTO.ResponseDto;
import com.example.mini_project_task_manager.DTO.commentDto.request.CommentRequest;
import com.example.mini_project_task_manager.DTO.commentDto.response.CommentResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

public interface CommentService {
    ResponseDto<CommentResponse> createComment(@Positive(message = "taskId는 1 이상이어야합니당") Long taskId, CommentRequest.@Valid CommentCreateRequest dto);

    ResponseDto<CommentResponse> updateComment(@Positive(message = "taskId는 1 이상이어야합니당") Long taskId, @Positive(message = "commentId는 1 이상이어야합니당") Long commentId, CommentRequest.@Valid CommentUpdateRequest dto);

    ResponseDto<CommentResponse> deleteComment(@Positive(message = "taskId는 1 이상이어야합니당") Long taskId, @Positive(message = "commentId는 1 이상이어야합니당") Long commentId);
}
