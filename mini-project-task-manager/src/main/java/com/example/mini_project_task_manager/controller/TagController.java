package com.example.mini_project_task_manager.controller;


import com.example.mini_project_task_manager.common.constants.ApiMappingPattern;
import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.project.response.ProjectResponse;
import com.example.mini_project_task_manager.dto.tag.request.TagRequest;
import com.example.mini_project_task_manager.dto.tag.response.TagResponse;
import com.example.mini_project_task_manager.dto.task.response.TaskResponse;
import com.example.mini_project_task_manager.entity.Tag;
import com.example.mini_project_task_manager.repository.TagRepository;
import com.example.mini_project_task_manager.security.UserPrincipal;
import com.example.mini_project_task_manager.service.TagService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static com.example.mini_project_task_manager.common.constants.ApiMappingPattern.Tags.FROM_TAG;
import static com.example.mini_project_task_manager.common.constants.ApiMappingPattern.Tags.FROM_TASK;

@RestController
@RequestMapping(ApiMappingPattern.Tags.ROOT)
@RequiredArgsConstructor
public class TagController {
    private final TagRepository tagRepository;
    // Tag 생성 - 인증된 사용자만
    private final TagService tagService;

    // PROJECT에서  태그 생성
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping(ApiMappingPattern.Tags.FROM_TAG)
    public ResponseEntity<ResponseDto<TagResponse.TagNameResponse>> createTagByProject(
            @PathVariable("projectId") @Positive(message = "projId는 1 이상이어야 해요.") Long projId,
            @Valid @RequestBody TagRequest.TagCreateRequest dto
            ){
        ResponseDto<TagResponse.TagNameResponse> response = tagService.createTagByProject(projId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Task에서 tag 생성
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping(FROM_TASK)
    public ResponseEntity<ResponseDto<TagResponse>> createTagByTask(
            @PathVariable("projectId") @Positive(message = "projId는 1 이상이어야 해요.") Long projId,
            @Valid @RequestBody TagRequest.TagCreateRequest dto
            ){
        ResponseDto<TagResponse> response = tagService.createTagByTask(projId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Project에서 Tag 전체 조회
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping("tags/all/asc")
    public ResponseEntity<ResponseDto<List<TagResponse.TagNameResponse>>> getAllTags() {
        ResponseDto<List<TagResponse.TagNameResponse>> response = tagService.getAllTags();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Task에서 Tag 전체 조회


    // Task에서 Tag 단건 조회

    // Project에서 Tag 단건 조회
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping("tagId/{tagId}")
    public ResponseEntity<ResponseDto<TagResponse.TagNameResponse>> getTagByTagId(
            @PathVariable ("tagId") @Positive(message = "tagId는 1 이상이어야 해요.") Long tagId
    ){
        ResponseDto<TagResponse.TagNameResponse> response = tagService.getTagByTagId(tagId);

        return ResponseEntity.ok().body(response);
    }

    // Tag 명을 통해 Task 조회
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping("tags/{tagName}")
    public ResponseEntity<ResponseDto<List<TaskResponse.TaskListResponse>>> getTaskByTagName(
            @PathVariable("tagName") @NotBlank(message = "tagName은 공백이 안되요.")
            @Size(max= 100, message = "tagName은 최대 100자까지 가능해요")
            String tagName
            ){
        ResponseDto<List<TaskResponse.TaskListResponse>> response = tagService.getTaskByTagName(tagName);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 프로젝트에서 Tag 삭제
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @DeleteMapping( "tagId/{tagId}")
    public ResponseEntity<ResponseDto<TagResponse>> deleteTag(
            @PathVariable("projectId") @Positive(message = "projectId는 1 이상이어야 해요.") Long projId,
            @PathVariable("tagId") @Positive(message = "tagId는 1 이상이어야 해요.") Long tagId
            ){
        ResponseDto<TagResponse> response = tagService.deleteTag(projId,tagId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
