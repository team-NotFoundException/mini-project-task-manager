package com.example.mini_project_task_manager.service;

import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.comment.request.CommentRequest;
import com.example.mini_project_task_manager.dto.comment.response.CommentsResponse;
import com.example.mini_project_task_manager.security.UserPrincipal;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import java.util.List;

public interface CommentService {
    ResponseDto<CommentsResponse.CommentResponse> createComment(UserPrincipal userPrincipal, @Positive(message = "taskId는 1 이상이어야합니당") Long taskId, CommentRequest.CommentCreateRequest dto);

    ResponseDto<List<CommentsResponse.CommentListResponse>> searchCommentByKeyword(String searchKeyword);
    ResponseDto<List<CommentsResponse.CommentListResponse>> getCommentsByAuthor(String author);
    ResponseDto<CommentsResponse.CommentResponse> updateComment(@Positive(message = "taskId는 1 이상이어야합니당") Long taskId,  Long commentId, CommentRequest.@Valid CommentUpdateRequest dto);
    ResponseDto<Void> deleteComment(@Positive(message = "taskId는 1 이상이어야합니당") Long taskId, @Positive(message = "commentId는 1 이상이어야합니당") Long commentId);
}
