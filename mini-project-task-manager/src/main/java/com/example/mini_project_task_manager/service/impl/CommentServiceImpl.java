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
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

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
        // 1. List를 담을 그릇 만들기
        List<CommentResponse.CommentListResponse> data = null;

        // 2. 전체 순환하면서 그릇에 담기
        data = commentsRepository.findAll().stream()
                .map(CommentResponse.CommentListResponse::from)
                .toList();

        // 출력해주기
        return ResponseDto.setSuccess("SUCCESS", data);
    }

    @Override
    public ResponseDto<List<CommentResponse.CommentListResponse>> getCommentByTaskId(Long taskId) {
        // 1. List를 담을 그릇 만들기
        List<CommentResponse.CommentListResponse> data = null;

        // 2. 전체 순환해서 commentId = taskId랑 일치하는 애들 담기
        // 2-1. commentId순환 + taskId순환 -> 입력한 taskId와 입력하는 값 찾기

        // 출력해주기
        return null;
    }

    @Override
    public ResponseDto<List<CommentResponse.CommentListResponse>> searchCommentByKeyword(String keyword) {
        // 1. 내용값을 입력받는다
        String searchKeyword = (keyword == null)? "" : keyword.trim();

        // 2. 유효한 값인지 확인한다
        if (searchKeyword.isEmpty()) {
            throw new IllegalArgumentException("검색 키워드가 비워져있다니");
        } else if (searchKeyword.length() > 50) {
            return ResponseDto.setFailed("키워드는 50자 이내로 작성해주세요");
        }

        // 3. 해당 keyword를 가지고 있는 댓글이 있는지 확인한다

        // 4. 순환하면서 찾는다
        // 5. 담는다


        return null;

    }

    @Override
    public ResponseDto<List<CommentResponse.CommentListResponse>> getCommentsByAuthor(String author) {

        return null;
    }

    @Override
    @Transactional
    public ResponseDto<CommentResponse> updateComment(Long taskId, Long commentId, CommentRequest.CommentUpdateRequest dto) {
        Comment comment = commentsRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 댓글을 찾을 수 없어요"));

        if (!comment.getTask().getId().equals(taskId)) {
            throw new IllegalArgumentException("해당 댓글이 Task 안에 포함되어있지 않아요");
        }

        comment.changeContent(dto.content());
        return ResponseDto.setSuccess("SUCCESS", CommentResponse.from(comment));

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
