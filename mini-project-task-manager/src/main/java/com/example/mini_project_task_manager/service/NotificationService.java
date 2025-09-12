package com.example.mini_project_task_manager.service;

import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.notification.request.NotiRequest;
import com.example.mini_project_task_manager.dto.notification.response.NotiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import java.util.List;

public interface NotificationService {
    ResponseDto<NotiResponse.NotiDetailResponse> NotiDetailResponse(NotiRequest.@Valid NotiCreateRequest dto);

    ResponseDto<List<NotiResponse.NotiListResponse>> getAllNotifications();

    ResponseDto<NotiResponse.NotiDetailResponse> getNotificationById(Long id);

    ResponseDto<NotiResponse> deleteNotification(@Positive(message = "notiId는 1 이상이어야합니다.") Long notiId);
}
