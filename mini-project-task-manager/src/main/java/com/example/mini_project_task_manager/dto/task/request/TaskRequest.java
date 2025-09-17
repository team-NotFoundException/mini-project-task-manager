package com.example.mini_project_task_manager.dto.task.request;

import com.example.mini_project_task_manager.common.enums.Priority;
import com.example.mini_project_task_manager.common.enums.Status;
import com.example.mini_project_task_manager.entity.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Set;

public class TaskRequest {

    /** 할일 생성 요청 Dto */
    public record TaskCreateRequest(

//            // @NotBlank는 String만 가능
//            @NotNull
//            Long projectId, --> controller에서 @PathVariable받으니 포함x

            @NotBlank(message = "제목 입력은 필수입니다.")
            @Size(max = 200, message = "제목은 200자 이하여야 합니다.")
            String title,

            @NotBlank(message = "내용을 입력해주세요.")
            String content,

            @NotNull(message = "마감기한을 정해주세요.")
            LocalDate dueDate,

            // 선택하지 않아도 기본값 설정
            // 스프링이 JSON 문자열을 Enum 으로 자동 변환 해줌.
            Priority priority,
            Status status,

            Set<String> tagNames

    ) {}

    /** 할일 수정 요청 Dto */
    public record TaskUpdateRequest(

            @NotBlank(message = "제목 입력은 필수입니다.")
            @Size(max = 200, message = "제목은 200자 이하여야 합니다.")
            String title,

            @NotBlank(message = "내용을 입력해주세요.")
            String content,

            @NotBlank(message = "마감기한을 정해주세요.")
            LocalDate dueDate,

            Priority priority,
            Status status,
            
            Set<String> tagNames
    ) {}
}