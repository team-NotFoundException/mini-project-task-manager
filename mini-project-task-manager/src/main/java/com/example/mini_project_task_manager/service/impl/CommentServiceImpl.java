package com.example.mini_project_task_manager.service.impl;

import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.comment.request.CommentRequest;
import com.example.mini_project_task_manager.dto.comment.response.CommentResponse;
import com.example.mini_project_task_manager.entity.Comment;
import com.example.mini_project_task_manager.entity.Task;
import com.example.mini_project_task_manager.repository.CommentsRepository;
import com.example.mini_project_task_manager.repository.TasksRepository;
import com.example.mini_project_task_manager.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// C: USER, AUTHOR, OWNER
// R: USER, AUTHOR, OWNER
// U: USER(댓글 작성자)
// D: USER(댓글 작성자), OWNER

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {
    private final CommentsRepository commentsRepository;
    private final TasksRepository tasksRepository;


    @Override
    @Transactional
    public ResponseDto<CommentResponse> createComment(Long taskId, CommentRequest.CommentCreateRequest dto) {
        Task task = tasksRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 task를 찾을 수 없습니다."));
        Comment comment = Comment.create(dto.content());

        task.addComment(comment);

        Comment saved = commentsRepository.save((comment));
        return ResponseDto.setSuccess("SUCCESS", CommentResponse.from(saved));
    }

    @Override
    public ResponseDto<List<CommentResponse.CommentListResponse>> getAllComment() {
        return null;
    }

    @Override
    public ResponseDto<List<CommentResponse.CommentListResponse>> getCommentByTaskId(Long taskId) {
        return null;
    }

    @Override
    public ResponseDto<List<CommentResponse.CommentListResponse>> searchCommentByKeyword(String keyword) {
        return null;
    }

    @Override
    public ResponseDto<List<CommentResponse.CommentListResponse>> getCommentsByAuthor(String author) {
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
