package com.example.mini_project_task_manager.controller;


import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.tag.request.TagRequest;
import com.example.mini_project_task_manager.dto.tag.response.TagResponse;
import com.example.mini_project_task_manager.security.UserPrincipal;
import com.example.mini_project_task_manager.service.TagService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    // Tag 생성 - 인증된 사용자만
    private final TagService tagService;

    @PostMapping("/api/v1/tags/{projectid}")
    public ResponseEntity<ResponseDto<TagResponse>> createTag(
            @PathVariable("projId") @Positive(message = "projId는 1 이상이여야합니다.") Long projId,
            @Valid @RequestBody TagRequest.TagCreateRequest dto
            ){
        ResponseDto<TagResponse> response = tagService.createTag(projId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    // Tag 삭제
    @DeleteMapping( "/api/v1/tags/{projId}/tags/{tagId}")
    public ResponseEntity<ResponseDto<TagResponse>> deleteTag(
            @PathVariable("projId") @Positive(message = "projId는 1 이상이여야합니다") Long projId,
            @PathVariable("tagId") @Positive(message = "tagId는 1이상이여야합니다") Long tagId
            ){
        ResponseDto<TagResponse> response = tagService.deleteTag(projId,tagId);
        return ResponseEntity.status(HttpStatus.OK).body(response);


    }

}
