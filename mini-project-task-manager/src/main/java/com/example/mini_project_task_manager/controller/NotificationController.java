package com.example.mini_project_task_manager.controller;


import com.example.mini_project_task_manager.common.constants.ApiMappingPattern;
import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.notification.request.NotificationsRequest;
import com.example.mini_project_task_manager.dto.notification.response.NotificationsResponse;
import com.example.mini_project_task_manager.repository.UserRepository;
import com.example.mini_project_task_manager.security.UserPrincipal;
import com.example.mini_project_task_manager.service.NotificationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.Notifications.ROOT)
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final UserRepository userRepository;


    // 공지 생성
    @PostMapping
    public ResponseEntity<ResponseDto<NotificationsResponse.NotificationDetailResponse>> createNotification(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody NotificationsRequest.NotificationCreateRequest dto
    ) {
        ResponseDto<NotificationsResponse.NotificationDetailResponse> response = notificationService.NotificationcreateResponse(principal, dto);
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
    @GetMapping
    public ResponseEntity<ResponseDto<List<NotificationsResponse.NotificationListResponse>>> getAllNotifications() {
        ResponseDto<List<NotificationsResponse.NotificationListResponse>> response = notificationService.getAllNotifications();
        return ResponseEntity.ok(response);
    }

    // 공지 단건 조회
    @GetMapping(ApiMappingPattern.Notifications.BY_ID)
    public ResponseEntity<ResponseDto<NotificationsResponse.NotificationDetailResponse>> getNotificationById(
            @PathVariable Long notificationId
    ) {
        ResponseDto<NotificationsResponse.NotificationDetailResponse> response = notificationService.getNotificationById(notificationId);
        return ResponseEntity.ok(response);
    }
    
    // 특정 키워드 포함 공지 조회
    @GetMapping(ApiMappingPattern.Notifications.SEARCH_CONTENT)
    public ResponseEntity<ResponseDto<List<NotificationsResponse.NotificationListResponse>>> getNotificationByKeyword(
            @RequestParam("keyword") @NotBlank(message = "검색 키워드는 비워져있을 수 없어요.") String keyword
    ) {
        ResponseDto<List<NotificationsResponse.NotificationListResponse>> response = notificationService.getNotificationByKeyword(keyword);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 공지 삭제
    @DeleteMapping(ApiMappingPattern.Notifications.BY_ID)
    public ResponseEntity<ResponseDto<Void>> deleteNotification(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable("notificationId") @Positive(message = "notificationId는 1 이상이어야 해요.") Long notificationId
            ) {
        ResponseDto<Void> response = notificationService.deleteNotification(notificationId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
