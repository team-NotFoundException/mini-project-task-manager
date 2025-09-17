package com.example.mini_project_task_manager.service;

import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.comment.request.CommentRequest;
import com.example.mini_project_task_manager.dto.comment.response.CommentResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import java.util.List;

public interface CommentService {
    ResponseDto<CommentResponse> createComment(@Positive(message = "taskId는 1 이상이어야합니당") Long taskId, CommentRequest.CommentCreateRequest dto);

    ResponseDto<List<CommentResponse.CommentListResponse>> getAllComment();

    ResponseDto<List<CommentResponse.CommentListResponse>> searchCommentByKeyword(String keyword);

    ResponseDto<List<CommentResponse.CommentListResponse>> getCommentsByAuthor(String author);

    ResponseDto<CommentResponse> updateComment(@Positive(message = "taskId는 1 이상이어야합니당") Long taskId, @Positive(message = "commentId는 1 이상이어야합니당") Long commentId, CommentRequest.@Valid CommentUpdateRequest dto);

    ResponseDto<Void> deleteComment(@Positive(message = "taskId는 1 이상이어야합니당") Long taskId, @Positive(message = "commentId는 1 이상이어야합니당") Long commentId);
}
