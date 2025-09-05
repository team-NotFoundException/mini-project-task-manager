package com.example.mini_project_task_manager.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
// 전역 예외처리 되도록 설정 // 예외 가로채서 JSON 표준 응답을 반환해줌
@Slf4j
// 로깅을 하기위한것


public class GlobalExceptionHandler {
    //@ExceptionHandler()
}
