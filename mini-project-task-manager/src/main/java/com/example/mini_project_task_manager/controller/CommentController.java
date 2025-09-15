package com.example.mini_project_task_manager.controller;


import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.comment.request.CommentRequest;
import com.example.mini_project_task_manager.dto.comment.response.CommentResponse;
import com.example.mini_project_task_manager.service.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;


    // 댓글 생성
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @PostMapping("/api/v1/tasks/{taskId}/comments")
    public ResponseEntity<ResponseDto<CommentResponse>> createComment(
            @PathVariable("taskId") @Positive(message = "taskId는 1 이상이어야합니당") Long taskId,
            @Valid @RequestBody CommentRequest.CommentCreateRequest dto
            ) {
        ResponseDto<CommentResponse> response = commentService.createComment(taskId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 댓글 조회
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<ResponseDto<List<CommentResponse.CommentListResponse>>> getAllComment() {
        ResponseDto<List<CommentResponse.CommentListResponse>> response = commentService.getAllComment();
        return ResponseEntity.ok(response);
    }

    // task별 댓글 조회
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping("/api/v1/tasks/{taskId}/comments")
    public ResponseEntity<ResponseDto<List<CommentResponse.CommentListResponse>>> getCommentByTaskId(
            @PathVariable("taskId") @Positive(message = "taksId 는 1 이상이어야함") Long taskId
    ) {
        ResponseDto<List<CommentResponse.CommentListResponse>> response = commentService.getCommentByTaskId(taskId);
        return ResponseEntity.ok(response);
    }

    // comment 키워드 댓글 조회
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping("/search-comment")
    public ResponseEntity<ResponseDto<List<CommentResponse.CommentListResponse>>> searchCommentByKeyword (
            @RequestParam("keyword") @NotBlank(message = "검색 키워드는 비워져있을 수 없어요") String keyword
    ) {
        ResponseDto<List<CommentResponse.CommentListResponse>> response = commentService.searchCommentByKeyword(keyword);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 특정 작성자의 모든 댓글 조회
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/api/v1/tasks/comments/auth/{author}")
    public ResponseEntity<ResponseDto<List<CommentResponse.CommentListResponse>>> getCommentsByAuthor(
            @PathVariable @NotBlank(message = "작성자는 비어있을 수 없습니다.") String author
    ) {
        ResponseDto<List<CommentResponse.CommentListResponse>> response = commentService.getCommentsByAuthor(author);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 댓글 수정
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @PutMapping("/api/v1/tasks/{taskId}/comments/{commentsId}")
    public ResponseEntity<ResponseDto<CommentResponse>> updateComment(
            @PathVariable("taskId") @Positive(message = "taskId는 1 이상이어야합니당") Long taskId,
            @PathVariable("commentsId") @Positive(message = "commentId는 1 이상이어야합니당") Long commentsId,
            @Valid @RequestBody CommentRequest.CommentUpdateRequest dto
    ) {
        ResponseDto<CommentResponse> response = commentService.updateComment(taskId, commentsId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 댓글 삭제
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @DeleteMapping("/api/v1/tasks/{taskId}/comments/{commentsId}")
    public ResponseEntity<ResponseDto<CommentResponse>> deleteComment(
            @PathVariable("taskId") @Positive(message = "taskId는 1 이상이어야합니당") Long taskId,
            @PathVariable("commentsId") @Positive(message = "commentId는 1 이상이어야합니당") Long commentsId
    ){
        ResponseDto<CommentResponse> response = commentService.deleteComment(taskId, commentsId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
