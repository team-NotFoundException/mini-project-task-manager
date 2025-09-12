package com.example.mini_project_task_manager.dto.tag.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TagRequest {

    /* 태그 추가 */
    public record TagCreateRequest(
        @NotBlank(message = "태그 이름은 필수입니다.")
        @Size(max = 100)
        String tag_name
    ) {}


}
