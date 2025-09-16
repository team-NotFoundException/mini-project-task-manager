package com.example.mini_project_task_manager.controller;


import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.notification.request.NotiRequest;
import com.example.mini_project_task_manager.dto.notification.response.NotiResponse;
import com.example.mini_project_task_manager.repository.UserRepository;
import com.example.mini_project_task_manager.security.UserPrincipal;
import com.example.mini_project_task_manager.service.NotificationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final UserRepository userRepository;


    // 공지 생성
    @PostMapping
    public ResponseEntity<ResponseDto<NotiResponse.NotiDetailResponse>> createNotification(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody NotiRequest.NotiCreateRequest dto
    ) {
        ResponseDto<NotiResponse.NotiDetailResponse> response = notificationService.NotiDetailResponse(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

//    // 공지 정렬(최신/과거순) 페이지네이션
//    @GetMapping("/cursor")
//    public ResponseEntity<ResponseDto<PageMetaResponse.SliceResponse>> getNotificationByCursor(
//            @RequestParam(required = false) Long cursorId,
//            @RequestParam(defaultValue = "3") @Min(1) @Max(50) int size
//    ) {
//        ResponseDto<PageMetaResponse.SliceResponse> response = notificationService.getNotificationByCursor(cursorId, size);
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//
//    }

    // 공지 조회(전체) 
    @GetMapping("/api/v1/notifications/all")
    public ResponseEntity<ResponseDto<List<NotiResponse.NotiListResponse>>> getAllNotifications() {
        ResponseDto<List<NotiResponse.NotiListResponse>> response = notificationService.getAllNotifications();
        return ResponseEntity.ok(response);
    }

    // 공지 단건 조회
    @GetMapping("/api/v1/notifications/{notiId}")
    public ResponseEntity<ResponseDto<NotiResponse.NotiDetailResponse>> getNotificationById(
            @PathVariable Long notiId
    ) {
        ResponseDto<NotiResponse.NotiDetailResponse> response = notificationService.getNotificationById(notiId);
        return ResponseEntity.ok(response);
    }
    
    // 특정 키워드 포함 공지 조회
    @GetMapping("/search-notification")
    public ResponseEntity<ResponseDto<List<NotiResponse.NotiListResponse>>> getNotificationByKeyword(
            @RequestParam("keyword") @NotBlank(message = "검색 키워드는 비워져있을 ㅅ 없습니다.") String keyword
    ) {
        ResponseDto<List<NotiResponse.NotiListResponse>> response = notificationService.getNotificationByKeyword(keyword);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 공지 삭제
    @DeleteMapping("/api/v1/notification/{notiId}")
    public ResponseEntity<ResponseDto<Void>> deleteNotification(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable("notiId") @Positive(message = "notiId는 1 이상이어야합니다.") Long notiId
            ) {
        ResponseDto<Void> response = notificationService.deleteNotification(notiId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
