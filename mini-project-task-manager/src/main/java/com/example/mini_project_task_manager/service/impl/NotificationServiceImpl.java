package com.example.mini_project_task_manager.service.impl;

import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.notification.request.NotificationsRequest;
import com.example.mini_project_task_manager.dto.notification.response.NotificationsResponse;
import com.example.mini_project_task_manager.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationServiceImpl implements NotificationService {

    @Override
    public ResponseDto<NotificationsResponse.NotificationDetailResponse> NotificationDetailResponse(NotificationsRequest.@Valid NotificationCreateRequest dto) {
        return null;
    }

    @Override
    public ResponseDto<List<NotificationsResponse.NotificationListResponse>> getAllNotifications() {
        return null;
    }

    @Override
    public ResponseDto<NotificationsResponse.NotificationDetailResponse> getNotificationById(Long notificationId) {
        return null;
    }

    @Override
    public ResponseDto<List<NotificationsResponse.NotificationListResponse>> getNotificationByKeyword(String keyword) {
        return null;
    }

    @Override
    public ResponseDto<Void> deleteNotification(Long notificationId) {
        return null;
    }
}
