//package com.example.mini_project_task_manager.controller;
//
//
//import com.example.mini_project_task_manager.DTO.ResponseDto;
//import com.example.mini_project_task_manager.DTO.commentDto.request.CommentRequest;
//import com.example.mini_project_task_manager.DTO.commentDto.response.CommentResponse;
//import com.example.mini_project_task_manager.common.constants.ApiMappingPattern;
//import com.example.mini_project_task_manager.service.CommentService;
//import jakarta.validation.Valid;
//import jakarta.validation.constraints.Positive;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/v1/comments")
//@RequiredArgsConstructor
//public class CommentController {
//    private final CommentService commentService;
//
//
//    // 댓글 생성
//    @PostMapping
//    public ResponseEntity<ResponseDto<CommentResponse>> createComment(
//            @PathVariable("taskId") @Positive(message = "taskId는 1 이상이어야합니당") Long taskId,
//            @Valid @RequestBody CommentRequest.CommentCreateRequest dto
//            ) {
//        ResponseDto<CommentResponse> response = commentService.createComment(taskId, dto);
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }
//
//    // 댓글 수정
//    @PutMapping("/api/v1/tasks/{taskId}/comments/{commentsId}")
//    public ResponseEntity<ResponseDto<CommentResponse>> updateComment(
//            @PathVariable("taskId") @Positive(message = "taskId는 1 이상이어야합니당") Long taskId,
//            @PathVariable("commentId") @Positive(message = "commentId는 1 이상이어야합니당") Long commentId,
//            @Valid @RequestBody CommentRequest.CommentUpdateRequest dto
//    ) {
//        ResponseDto<CommentResponse> response = commentService.updateComment(taskId, commentId, dto);
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }
//
//    // 댓글 삭제
//    @DeleteMapping("/api/v1/tasks/{taskId}/comments/{commentsId}")
//    public ResponseEntity<ResponseDto<CommentResponse>> deleteComment(
//            @PathVariable("taskId") @Positive(message = "taskId는 1 이상이어야합니당") Long taskId,
//            @PathVariable("commentId") @Positive(message = "commentId는 1 이상이어야합니당") Long commentId
//    ){
//        ResponseDto<CommentResponse> response = commentService.deleteComment(taskId, commentId);
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }
//}
