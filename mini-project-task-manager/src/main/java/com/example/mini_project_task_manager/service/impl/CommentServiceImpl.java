package com.example.mini_project_task_manager.service.impl;

import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.comment.request.CommentRequest;
import com.example.mini_project_task_manager.dto.comment.response.CommentsResponse;
import com.example.mini_project_task_manager.entity.Comment;
import com.example.mini_project_task_manager.entity.Task;
import com.example.mini_project_task_manager.repository.CommentsRepository;
import com.example.mini_project_task_manager.repository.TaskRepository;
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
    private final CommentsRepository commentsRepository;
    private final TaskRepository taskRepository;


    @Override
    @Transactional
    public ResponseDto<CommentsResponse.CommentResponse> createComment(Long taskId, CommentRequest.CommentCreateRequest dto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 task를 찾을 수 없습니다."));
        Comment comment = Comment.create(dto.comment());

        task.addComment(comment);

        Comment saved = commentsRepository.save((comment));
        return ResponseDto.setSuccess("SUCCESS", CommentsResponse.CommentResponse.from(saved));
    }

    @Override
    public ResponseDto<List<CommentsResponse.CommentListResponse>> getAllComment() {
        // 1. List를 담을 그릇 만들기
        List<CommentsResponse.CommentListResponse> data = null;

        // 2. 전체 순환하면서 그릇에 담기
        data = commentsRepository.findAll().stream()
                .map(CommentsResponse.CommentListResponse::from)
                .toList();

        // 출력해주기
        return ResponseDto.setSuccess("SUCCESS", data);
    }


    @Override
    public ResponseDto<List<CommentsResponse.CommentListResponse>> searchCommentByKeyword(String keyword) {
        // 1. 내용값을 입력받는다
        String searchKeyword = (keyword == null)? "" : keyword.trim();

        // 2. 유효한 값인지 확인한다
        if (searchKeyword.isEmpty()) {
            throw new IllegalArgumentException("검색 키워드가 비워져있다니");
        } else if (searchKeyword.length() > 50) {
            return ResponseDto.setFailed("키워드는 50자 이내로 작성해주세요");
        }

        // 3. 해당 keyword를 가지고 있는 댓글이 있는지 확인한다
        var rows = commentsRepository.findByCommentKeyword(searchKeyword);
        // 4. 순환하면서 찾는다
        List<CommentsResponse.CommentListResponse> result = rows.stream()
                .map(CommentsResponse.CommentListResponse::from).toList();
        // 5. 담는다

        return ResponseDto.setSuccess("SUCCESS", result);

    }

    @Override
    public ResponseDto<List<CommentsResponse.CommentListResponse>> getCommentsByAuthor(String author) {
        List<Comment> comments = commentsRepository.findByAuthor(author);
        List<CommentsResponse.CommentListResponse> result = comments.stream()
                .map(CommentsResponse.CommentListResponse::from).toList();
        return ResponseDto.setSuccess("SUCCESS", result);
    }

    @Override
    @Transactional
    public ResponseDto<CommentsResponse.CommentResponse> updateComment(Long taskId, Long commentId, CommentRequest.CommentUpdateRequest dto) {
        Comment comment = commentsRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 댓글을 찾을 수 없어요"));

        if (!comment.getTask().getId().equals(taskId)) {
            throw new IllegalArgumentException("해당 댓글이 Task 안에 포함되어있지 않아요");
        }

        comment.changeContent(dto.comment());
        return ResponseDto.setSuccess("SUCCESS", CommentsResponse.CommentResponse.from(comment));

    }

    @Override
    @Transactional
    public ResponseDto<Void> deleteComment(Long taskId, Long commentId) {
        Comment comment = commentsRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 댓글을 찾을 수 없어요"));

        // 고아 객체 제거?
        // -> cascade 되야함 -> comment에서 지워지면 task에서도 지워야함
        Task task = comment.getTask();
        task.removeComment(comment);

        return ResponseDto.setSuccess("SUCCESS", null);
    }
}
