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

            @NotBlank(message = "제목을 작성해주세요.")
            @Size(max = 200, message = "제목은 200자 이하로 작성해주세요.")
            String title,

            @NotBlank(message = "내용을 작성해주세요.")
            String content,

            @NotNull(message = "마감기한을 작성해주세요.")
            LocalDate dueDate,

            Priority priority,
            Status status,
            Set<String> tagNames
    ) {}

    /** 할일 수정 요청 Dto */
    public record TaskUpdateRequest(

            @NotBlank(message = "수정할 제목을 작성해주세요.")
            @Size(max = 200, message = "제목은 200자 이하로 작성해주세요.")
            String title,

            @NotBlank(message = "수정할 내용을 작성해주세요.")
            String content,

            @NotNull(message = "마감기한을 작성해주세요.")
            LocalDate dueDate,

            Priority priority,
            Status status,
            Set<String> tagNames
    ) {}
}