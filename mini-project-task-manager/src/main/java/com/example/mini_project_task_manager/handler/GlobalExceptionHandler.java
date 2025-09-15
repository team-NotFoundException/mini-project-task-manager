package com.example.mini_project_task_manager.handler;


import com.example.mini_project_task_manager.common.enums.ErrorCode;
import com.example.mini_project_task_manager.common.errors.ErrorResponse;
import com.example.mini_project_task_manager.common.errors.FieldErrorItem;
import com.example.mini_project_task_manager.dto.ResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



@RestControllerAdvice
// 전역 예외처리 되도록 설정  예외 가로채서 JSON 표준 응답을 반환해줌
// 1. 단일 책임 원칙 (SRP) - 예외 처리를 Controller이 아닌 GEH(Global exception handler)에서 담당
// 흐름 : controller에서 DTO를 받을 때 @Valid가 붙어 있으면 각 필드의 검증 어노테이션이 값을 검증을 한다
// MethodArgumentNotValidException을 던진다.
// 2. 재사용성 증가
// - 모든 controller 에 대한 예외 처리가 한 곳에서 관리를 하기 때문
// 3. 가독성 향상
// - 재사용성 증가와 같은 맥락

@Slf4j
// 로깅 라이브러리 통합, 프레임워크에 얽매이지 않고 코드 작성을 가능하게 해주는 어노테이션


public class GlobalExceptionHandler {
    // 공통 응답 생성 유틸 //
    // 예외 상황에서 클라이언트에게 반환할 ResponseEntity<ResponseDto>를 조립하는 유틸 메서드
    // - ErrorCode, reason, errors 를 받아 표즌 JSON으로 변환
    private ResponseEntity<ResponseDto<Object>> fail(
            ErrorCode code, String reason, List<FieldErrorItem> errors
            // : 실제 응답을 한 곳에서 조립
    ) {
        // 1) reason값 설정: 비워질 경우 ErrorCode의 기본 메시지 값 사용
        String finalReason = (reason != null && !reason.isBlank()) ? reason : code.defaultMessage;
        // 2) 클라이언트가 해석할 수 있는 표준 에러 응답 본문 조립
        ErrorResponse body = ErrorResponse.of(code.code, finalReason, errors);
        return ResponseEntity.status(code.status)
                .body(ResponseDto.setFailed(finalReason, body));
    }

    // == @Valid(RequestBody) 검증 실패 항목을 표준 형식으로 변환 == //
    private List<FieldErrorItem> toFieldErrors(MethodArgumentNotValidException e){
        List<FieldErrorItem> list = new ArrayList<>(); // 에러 수집용 리스트

        // 필드 단위 검증 실패 항목 순회
        e.getBindingResult().getFieldErrors().forEach(err -> {
            list.add(new FieldErrorItem(
                    err.getField(),
                    Objects.toString(err.getRejectedValue(), "null"),
                    err.getDefaultMessage()
            ));
        });

        // 글로벌 에러도 검증
        e.getBindingResult().getGlobalErrors().forEach(err -> {
            // 필드명 대신 오브젝트명 사용
            list.add(new FieldErrorItem(err.getObjectName(), "", err.getDefaultMessage()));
        });

        return list;
    }

    // === 400 Bad Request 그룹: 잘못된 인자/상태 (서비스 레벨 방어 예외 등) === //
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<ResponseDto<Object>> handleBadRequest(Exception e) {
        log.warn("Bad Request: {}", e.getMessage());
        return fail(ErrorCode.BAD_REQUEST, null, null);
    }
    // === 400 Validation Error: @Valid @RequestBody 검증 실패 === //
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<Object>> handleValidation(MethodArgumentNotValidException e) {
        log.warn("Validation failed: {}", e.getMessage());
        return fail(ErrorCode.VALIDATION_ERROR, null, toFieldErrors(e));
    }

    // === 401 AuthenticationException -> 인증/권한 문제
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseDto<Object>> handleAuth(AuthenticationException e) {
        log.warn("UnAuthorized: {}", e.getMessage());
        return fail(ErrorCode.UNAUTHORIZED, null, null);
    }
    // === 403 Forbidden: 접근 거부 === //
    @ExceptionHandler({ AccessDeniedException.class, AuthorizationDeniedException.class })
    public ResponseEntity<ResponseDto<Object>> handleAccessDenied(AccessDeniedException e) {
        log.warn("AccessDenied: {}", e.getMessage());
        return fail(ErrorCode.FORBIDDEN, null, null);
    }

    // === 404 Not Found: 엔티티 조회 실패 === //
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseDto<Object>> handleNotFound(EntityNotFoundException e) {
        log.warn("Not Found: {}", e.getMessage());
        return fail(ErrorCode.NOT_FOUND, null, null);
    }

    // === 409 Conflict: 무결성 위반(중복/제약조건) === //
    @ExceptionHandler(DataIntegrityViolationException.class) // Unique 키 충돌, FK 위반 등
    public ResponseEntity<ResponseDto<Object>> handleConflict(DataIntegrityViolationException e) {
        log.warn("Conflict: {}", e.getMessage());
        return fail(ErrorCode.CONFLICT, null, null);
    }

    // === 500 Internal Server Error: 그 밖의 모든 예외에 대한 최종 안정망 === //
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<Object>> handleException(Exception e) {
        log.error("Internal error", e);
        return fail(ErrorCode.INTERNAL_ERROR, null, null);
    }

}
