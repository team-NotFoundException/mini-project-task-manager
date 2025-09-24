package com.example.mini_project_task_manager.dto.tag.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TagRequest {

    /* 태그 추가 */
    public record TagCreateRequest(
        @NotBlank(message = "태그를 입력해주세요")
        @Size(max = 100, message = "태그는 100자 이하로 작성해주세요")
        String tagName
    ) {}

//    public record TagCreateRequestByProject(
//            @NotBlank(message = "태그를 입력해주세요")
//            @Size(max = 100, message = "태그는 100자 이하로 작성해주세요")
//            String tag_name
//    ) {}
//
//    public record TagCreateRequestByTask(
//            @NotBlank(message = "태그를 입력해주세요")
//            @Size(max = 100, message = "태그는 100자 이하로 작성해주세요")
//            String tag_name
//    ) {}

}
