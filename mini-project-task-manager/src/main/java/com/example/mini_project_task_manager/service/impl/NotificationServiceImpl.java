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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDto<NotificationsResponse.NotificationDetailResponse> NotificationcreateResponse(
            UserPrincipal userPrincipal,
            NotificationsRequest.NotificationCreateRequest dto) {

//        // 유효한지
        if (!StringUtils.hasText(dto.title())) {
            throw new IllegalArgumentException("제목은 빌 수 없어요.");
        }
        if (!StringUtils.hasText(dto.content())) {
            throw new IllegalArgumentException("내용도 빌 수 없어요.");
        }

        // 작성자?
        @NotNull User author = userRepository.findByUsername(userPrincipal.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("AUTHOR NOT FOUND"));

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

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("공지를 찾을 수 없어요."));

        data = NotificationsResponse.NotificationDetailResponse.from(notification);

        return ResponseDto.setSuccess("SUCCESS", data);
    }

    @Override
    public ResponseDto<List<NotificationsResponse.NotificationListResponse>> getNotificationByKeyword(String keyword) {
        List<Notification> notifications = notificationRepository.findByKeyWordContainingIgnoreCaseOrderByIdDesc(keyword);

        List<NotificationsResponse.NotificationListResponse> result = notifications.stream()
                .map(NotificationsResponse.NotificationListResponse::from)
                .toList();



        return ResponseDto.setSuccess("SUCCESS", result);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDto<Void> deleteNotification(UserPrincipal principal, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 공지가 없습니다."));

        notificationRepository.delete(notification);

        return ResponseDto.setSuccess("SUCCESS", null);
    }


}
