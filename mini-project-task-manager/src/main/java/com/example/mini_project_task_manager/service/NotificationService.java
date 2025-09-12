//package com.example.mini_project_task_manager.service;
//
//import com.example.mini_project_task_manager.dto.ResponseDto;
//import com.example.mini_project_task_manager.dto.notification.request.NotiRequest;
//import com.example.mini_project_task_manager.dto.notification.response.NotiResponse;
//import jakarta.validation.Valid;
//import jakarta.validation.constraints.Positive;
//
//public interface NotificationService {
//    ResponseDto<NotiResponse.NotiCreateResponse> createNotification(@Positive(message = "projId는 1 이상이어야합니다.") Long projId, NotiRequest.@Valid NotiCreateRequest dto);
//
//    ResponseDto<NotiResponse.NotiUpdateResponse> updateNotification(@Positive(message = "projId는 1 이상이어야합니다.") Long projId, @Positive(message = "notiId는 1 이상이어야합니다.") Long notiId, NotiRequest.@Valid NotiUpdateRequest dto);
//
//    ResponseDto<NotiResponse> deleteNotification(@Positive(message = "projId는 1 이상이어야합니다.") Long projId, @Positive(message = "notiId는 1 이상이어야합니다.") Long notiId);
//}
