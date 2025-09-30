package com.example.mini_project_task_manager.service.impl;

import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.comment.request.CommentRequest;
import com.example.mini_project_task_manager.dto.comment.response.CommentsResponse;
import com.example.mini_project_task_manager.dto.notification.response.NotificationsResponse;
import com.example.mini_project_task_manager.entity.Comment;
import com.example.mini_project_task_manager.entity.Task;
import com.example.mini_project_task_manager.entity.User;
import com.example.mini_project_task_manager.repository.CommentRepository;
import com.example.mini_project_task_manager.repository.TaskRepository;
import com.example.mini_project_task_manager.repository.UserRepository;
import com.example.mini_project_task_manager.security.UserPrincipal;
import com.example.mini_project_task_manager.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentsRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ResponseDto<CommentsResponse.CommentResponse> createComment(UserPrincipal userPrincipal, Long taskId, CommentRequest.CommentCreateRequest dto) {
        CommentsResponse.CommentResponse data = null;

        User author = userRepository.findByUsername(userPrincipal.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("AUTHOR NOT FOUND"));
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 task를 찾을 수 없습니다."));

        Comment comment = Comment.create(dto.comment(), author, task);
        Comment saved = commentsRepository.save((comment));
        data = CommentsResponse.CommentResponse.from(saved);
        task.addComment(comment);
        return ResponseDto.setSuccess("SUCCESS", data);
    }

    @Override
    public ResponseDto<List<CommentsResponse.CommentListResponse>> searchCommentByKeyword(String searchKeyword) {
        List<CommentsResponse.CommentListResponse> data = null;

        String keyword = (searchKeyword == null) ? "" : searchKeyword.trim();

        if (keyword.isEmpty()) {
            throw new IllegalArgumentException("검색 키워드가 비워져있어요.");

        } else if (keyword.length() > 50) {
            return ResponseDto.setFailed("키워드는 50자 이내로 작성해주세요.");
        }

        List<Comment> comments
                = commentsRepository.findByCommentKeyword(keyword);

        data = comments.stream()
                .map(CommentsResponse.CommentListResponse::from).toList();

        return ResponseDto.setSuccess("SUCCESS", data);
    }

    @Override
    public ResponseDto<List<CommentsResponse.CommentListResponse>> getCommentsByAuthor(String author) {
        List<CommentsResponse.CommentListResponse> data = null;

        List<Comment> comments
                = commentsRepository.findByAuthor(author);

        data = comments.stream()
                .map(CommentsResponse.CommentListResponse::from).toList();

        return ResponseDto.setSuccess("SUCCESS", data);
    }

    @Override
    @Transactional
    public ResponseDto<CommentsResponse.CommentResponse> updateComment(Long taskId, Long commentId, CommentRequest.CommentUpdateRequest dto) {
        CommentsResponse.CommentResponse data = null;
        Comment comment = commentsRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 댓글을 찾을 수 없어요"));

        if (!comment.getTask().getId().equals(taskId)) {
            throw new IllegalArgumentException("해당 댓글이 Task 안에 포함되어있지 않아요");
        }
        comment.changeContent(dto.comment());
        data = CommentsResponse.CommentResponse.from(comment);
        return ResponseDto.setSuccess("SUCCESS", data);
    }

    @Override
    @Transactional
    public ResponseDto<Void> deleteComment(Long taskId, Long commentId) {
        Comment comment = commentsRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 댓글을 찾을 수 없어요"));

        Task task = comment.getTask();
        task.removeComment(comment);

        return ResponseDto.setSuccess("SUCCESS", null);
    }
}
