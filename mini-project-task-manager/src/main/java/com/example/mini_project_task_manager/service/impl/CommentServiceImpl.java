package com.example.mini_project_task_manager.service.impl;

import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.comment.request.CommentRequest;
import com.example.mini_project_task_manager.dto.comment.response.CommentResponse;
import com.example.mini_project_task_manager.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// C: USER, AUTHOR, OWNER
// R: USER, AUTHOR, OWNER
// U: USER(댓글 작성자)
// D: USER(댓글 작성자), OWNER

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {
    @Override
    public ResponseDto<CommentResponse> createComment(Long taskId, CommentRequest.@Valid CommentCreateRequest dto) {
        return null;
    }

    @Override
    public ResponseDto<CommentResponse> updateComment(Long taskId, Long commentId, CommentRequest.@Valid CommentUpdateRequest dto) {
        return null;
    }

    @Override
    public ResponseDto<CommentResponse> deleteComment(Long taskId, Long commentId) {
        return null;
    }
}
