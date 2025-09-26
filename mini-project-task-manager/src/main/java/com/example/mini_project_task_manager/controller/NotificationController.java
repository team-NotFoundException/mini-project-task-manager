package com.example.mini_project_task_manager.controller;

import com.example.mini_project_task_manager.common.constants.ApiMappingPattern;
import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.notification.request.NotificationsRequest;
import com.example.mini_project_task_manager.dto.notification.response.NotificationsResponse;
import com.example.mini_project_task_manager.security.UserPrincipal;
import com.example.mini_project_task_manager.service.NotificationService;
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

    @PostMapping
    public ResponseEntity<ResponseDto<NotificationsResponse.NotificationDetailResponse>> createNotification(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody NotificationsRequest.NotificationCreateRequest dto
    ) {
        ResponseDto<NotificationsResponse.NotificationDetailResponse> response = notificationService.NotificationcreateResponse(principal, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<NotificationsResponse.NotificationListResponse>>> getAllNotifications() {
        ResponseDto<List<NotificationsResponse.NotificationListResponse>> response = notificationService.getAllNotifications();
        return ResponseEntity.ok(response);
    }

    @GetMapping(ApiMappingPattern.Notifications.BY_ID)
    public ResponseEntity<ResponseDto<NotificationsResponse.NotificationDetailResponse>> getNotificationById(
            @PathVariable Long notificationId
    ) {
        ResponseDto<NotificationsResponse.NotificationDetailResponse> response = notificationService.getNotificationById(notificationId);
        return ResponseEntity.ok(response);
    }

    @GetMapping(ApiMappingPattern.Notifications.SEARCH_CONTENT)
    public ResponseEntity<ResponseDto<List<NotificationsResponse.NotificationListResponse>>> getNotificationByKeyword(
            @RequestParam("keyword") @NotBlank(message = "검색 키워드는 비워져있을 수 없어요.") String keyword
    ) {
        ResponseDto<List<NotificationsResponse.NotificationListResponse>> response = notificationService.getNotificationByKeyword(keyword);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(ApiMappingPattern.Notifications.BY_ID)
    public ResponseEntity<ResponseDto<Void>> deleteNotification(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable("notificationId") @Positive(message = "notificationId는 1 이상이어야 해요.") Long notificationId
            ) {
        ResponseDto<Void> response = notificationService.deleteNotification(principal, notificationId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
