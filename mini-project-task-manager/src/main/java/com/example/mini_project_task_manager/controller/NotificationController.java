//package com.example.mini_project_task_manager.controller;
//
//
//import com.example.mini_project_task_manager.DTO.ResponseDto;
//import com.example.mini_project_task_manager.DTO.notificationDto.request.NotiRequest;
//import com.example.mini_project_task_manager.DTO.notificationDto.response.NotiResponse;
//import com.example.mini_project_task_manager.service.NotificationService;
//import jakarta.validation.Valid;
//import jakarta.validation.constraints.Positive;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/v1/notifications")
//@RequiredArgsConstructor
//public class NotificationController {
//    private final NotificationService notificationService;
//
//
//    // 공지 생성
//    @PostMapping
//    public ResponseEntity<ResponseDto<NotiResponse.NotiCreateResponse>> createNotification(
//            @PathVariable("projId") @Positive(message = "projId는 1 이상이어야합니다.") Long projId,
//            @Valid @RequestBody NotiRequest.NotiCreateRequest dto
//    ) {
//        ResponseDto<NotiResponse.NotiCreateResponse> response = notificationService.createNotification(projId, dto);
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }
//
//    @PutMapping("/api/v1/pojets/{projId}/notification/{notiId}")
//    public ResponseEntity<ResponseDto<NotiResponse.NotiUpdateResponse>> updateNotification(
//            @PathVariable("projId") @Positive(message = "projId는 1 이상이어야합니다.") Long projId,
//            @PathVariable("notiId") @Positive(message = "notiId는 1 이상이어야합니다.") Long notiId,
//            @Valid @RequestBody NotiRequest.NotiUpdateRequest dto
//    ) {
//        ResponseDto<NotiResponse.NotiUpdateResponse> response = notificationService.updateNotification(projId, notiId, dto);
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }
//
//    // 공지 삭제
//    @DeleteMapping("/api/v1/pojets/{projId}/notification/{notiId}")
//    public ResponseEntity<ResponseDto<NotiResponse>> deleteNotification(
//            @PathVariable("projId") @Positive(message = "projId는 1 이상이어야합니다.") Long projId,
//            @PathVariable("notiId") @Positive(message = "notiId는 1 이상이어야합니다.") Long notiId
//            ) {
//        ResponseDto<NotiResponse> response = notificationService.deleteNotification(projId, notiId);
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }
//}
