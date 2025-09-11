package com.example.mini_project_task_manager.DTO.projectDto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProjectRequest {

    /** 프로젝트 생성 요청 DTO */
    public record ProjectCreateRequest(
            @NotBlank(message = "프로젝트 제목은 필수입니다.")
            @Size(max = 200)
            String title,

            @Size(max = 255)
            String content
    ) {}

    /** 프로젝트 수정 요청 DTO */
    public record ProjectUpdateRequest(
            @NotBlank(message = "프로젝트 제목은 공백일 수 없습니다.")
            @Size(max = 200)
            String title,

            @Size(max = 255)
            String content
    ) {}

}
