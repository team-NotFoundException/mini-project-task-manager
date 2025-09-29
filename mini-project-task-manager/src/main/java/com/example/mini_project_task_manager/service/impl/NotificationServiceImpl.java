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
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ResponseDto<NotificationsResponse.NotificationDetailResponse> NotificationcreateResponse(
            UserPrincipal userPrincipal,
            NotificationsRequest.NotificationCreateRequest dto) {

        if (!StringUtils.hasText(dto.title())) {
            throw new IllegalArgumentException("제목은 빌 수 없어요.");
        }
        if (!StringUtils.hasText(dto.content())) {
            throw new IllegalArgumentException("내용도 빌 수 없어요.");
        }

        @NotNull User author = userRepository.findByUsername(userPrincipal.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("권한을 찾을 수가 없어요."));

        Notification saved = notificationRepository.save(Notification.create(dto.title(), dto.content(), author));
        NotificationsResponse.NotificationDetailResponse data = NotificationsResponse.NotificationDetailResponse.from(saved);
        return ResponseDto.setSuccess("SUCCESS", data);
    }

    @Override
    public ResponseDto<List<NotificationsResponse.NotificationListResponse>> getAllNotifications() {
        List<NotificationsResponse.NotificationListResponse> data = null;

        data = notificationRepository.findAll().stream()
                .map(NotificationsResponse.NotificationListResponse::from)
                .toList();

        return ResponseDto.setSuccess("SUCCESS", data);
    }

    @Override
    public ResponseDto<NotificationsResponse.NotificationDetailResponse> getNotificationById(Long notificationId) {
        NotificationsResponse.NotificationDetailResponse data = null;

        if (notificationId == null) throw new IllegalArgumentException("공지를 불러올 수 없어요.");

        Notification notification
                = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("공지를 찾을 수 없어요."));

        data = NotificationsResponse.NotificationDetailResponse.from(notification);
        return ResponseDto.setSuccess("SUCCESS", data);
    }

    @Override
    public ResponseDto<List<NotificationsResponse.NotificationListResponse>> getNotificationByKeyword(String keyword) {
        if (keyword == null) throw new IllegalArgumentException("키워드는 비워져있을 수 없습니다.");

        List<NotificationsResponse.NotificationListResponse> data = null;
        List<Notification> notifications
                = notificationRepository.findByKeyWordContainingIgnoreCaseOrderByIdDesc(keyword);

        data = notifications.stream()
                .map(NotificationsResponse.NotificationListResponse::from)
                .toList();

        if (data.isEmpty()) {
            throw new IllegalArgumentException("해당 키워드에 해당하는 공지가 없습니다.");
        }
        return ResponseDto.setSuccess("SUCCESS", data);
    }

    @Override
    @Transactional
    public ResponseDto<Void> deleteNotification(UserPrincipal principal, Long notificationId) {
        Notification notification
                = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 공지가 없습니다."));
        notificationRepository.delete(notification);

        return ResponseDto.setSuccess("SUCCESS", null);
    }
}
