package com.example.mini_project_task_manager.dto.project.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProjectRequest {

    /** 프로젝트 생성 요청 DTO */
    public record ProjectCreateRequest(
            @NotBlank(message = "제목을 작성해주세요.")
            @Size(max = 200, message = "제목은 200자 이하로 작성해주세요.")
            String title,

            @Size(max = 255, message = "개요는 255자 이하로 작성해주세요.")
            String content
    ) {}

    /** 프로젝트 수정 요청 DTO */
    public record ProjectUpdateRequest(
            @NotBlank(message = "수정할 제목을 작성해주세요.")
            @Size(max = 200, message = "제목은 200자 이하로 작성해주세요.")
            String title,

            @Size(max = 255, message = "개요는 255자 이하로 작성해주세요.")
            String content
    ) {}
}
