package com.example.mini_project_task_manager.service.impl;

import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.notification.request.NotificationsRequest;
import com.example.mini_project_task_manager.dto.notification.response.NotificationsResponse;
import com.example.mini_project_task_manager.entity.Notification;
import com.example.mini_project_task_manager.entity.User;
import com.example.mini_project_task_manager.repository.NotificationRepository;
import com.example.mini_project_task_manager.repository.UserRepository;
import com.example.mini_project_task_manager.security.UserPrincipal;
import com.example.mini_project_task_manager.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public ResponseDto<NotificationsResponse.NotificationDetailResponse> NotificationcreateResponse(UserPrincipal userPrincipal, NotificationsRequest.@Valid NotificationCreateRequest dto) {
        // 유효한지

        // 작성자?
//        final String username = userPrincipal.getUsername();

//        User author = userRepository.findByUsername(username)
//                .orElseThrow(() -> new IllegalArgumentException("작성자를 찾을 수 없음"));

        Notification saved = notificationRepository.save(Notification.create(dto.title(), dto.content()));

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
