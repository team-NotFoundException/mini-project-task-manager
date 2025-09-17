package com.example.mini_project_task_manager.controller;


import com.example.mini_project_task_manager.common.constants.ApiMappingPattern;
import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.comment.request.CommentRequest;
import com.example.mini_project_task_manager.dto.comment.response.CommentResponse;
import com.example.mini_project_task_manager.service.CommentService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.Comments.ROOT)
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;


    // 댓글 생성
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<ResponseDto<CommentResponse>> createComment(
            @PathVariable("taskId") @Positive(message = "taskId는 1 이상이어야합니당") Long taskId,
            @RequestBody CommentRequest.CommentCreateRequest dto
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


    // comment 키워드 댓글 조회
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping(ApiMappingPattern.Comments.SEARCH_CONTENT)
    public ResponseEntity<ResponseDto<List<CommentResponse.CommentListResponse>>> searchCommentByKeyword (
            @RequestParam("keyword") @NotBlank(message = "검색 키워드는 비워져있을 수 없어요") String keyword
    ) {
        ResponseDto<List<CommentResponse.CommentListResponse>> response = commentService.searchCommentByKeyword(keyword);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 특정 작성자의 모든 댓글 조회
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping(ApiMappingPattern.Comments.SEARCH_AUTHOR)
    public ResponseEntity<ResponseDto<List<CommentResponse.CommentListResponse>>> getCommentsByAuthor(
            @RequestParam("author") @NotBlank(message = "작성자는 비어있을 수 없습니다.") String author
    ) {
        ResponseDto<List<CommentResponse.CommentListResponse>> response = commentService.getCommentsByAuthor(author);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 댓글 수정
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @PutMapping(ApiMappingPattern.Comments.BY_ID)
    public ResponseEntity<ResponseDto<CommentResponse>> updateComment(
            @PathVariable("taskId") @Positive(message = "taskId는 1 이상이어야합니당") Long taskId,
            @PathVariable("commentId") @Positive(message = "commentId는 1 이상이어야합니당") Long commentId,
            @RequestBody CommentRequest.CommentUpdateRequest dto
    ) {
        ResponseDto<CommentResponse> response = commentService.updateComment(taskId, commentId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 댓글 삭제
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @DeleteMapping(ApiMappingPattern.Comments.BY_ID)
    public ResponseEntity<ResponseDto<Void>> deleteComment(
            @PathVariable("taskId") Long taskId,
            @PathVariable("commentId") Long commentId
    ){
        ResponseDto<Void> response = commentService.deleteComment(taskId, commentId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
