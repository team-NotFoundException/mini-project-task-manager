package com.example.mini_project_task_manager.controller;


import com.example.mini_project_task_manager.common.constants.ApiMappingPattern;
import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.tag.request.TagRequest;
import com.example.mini_project_task_manager.dto.tag.response.TagResponse;
import com.example.mini_project_task_manager.dto.task.response.TaskResponse;
import com.example.mini_project_task_manager.repository.TagRepository;
import com.example.mini_project_task_manager.service.TagService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static com.example.mini_project_task_manager.common.constants.ApiMappingPattern.Tags.FROM_TAG;
import static com.example.mini_project_task_manager.common.constants.ApiMappingPattern.Tags.FROM_TASK;

@RestController
@RequestMapping(ApiMappingPattern.Tags.ROOT)
@RequiredArgsConstructor
public class TagController {
    // Tag 생성 - 인증된 사용자만
    private final TagService tagService;
    // PROJECT에서  태그 생성
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping(FROM_TAG)
    public ResponseEntity<ResponseDto<TagResponse.TagNameResponse>> createTagByProject(
            @PathVariable("projectId") @Positive(message = "projId는 1 이상이어야 해요.") Long projId,
            @RequestBody TagRequest.TagCreateRequest dto
            ){
        ResponseDto<TagResponse.TagNameResponse> response = tagService.createTagByProject(projId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ProjectId 검색후 Tag 전체 조회
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping(FROM_TAG)
    public ResponseEntity<ResponseDto<List<TagResponse.TagNameResponse>>> getAllTagsByProjectId(
            @PathVariable("projectId") @Positive(message = "projectId는 1이상이어야 해요.") Long projectId
    ) {
        ResponseDto<List<TagResponse.TagNameResponse>> response = tagService.getAllTagsByProjectId(projectId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Task에서 Tag 전체 조회
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping(FROM_TASK)
    public ResponseEntity<ResponseDto<List<TagResponse.TagNameResponse>>> getAllTagsByTask(
            @PathVariable("taskId") @Positive(message = "taskId는 1이상이어야 해요.") Long taskId) {
        ResponseDto<List<TagResponse.TagNameResponse>> response = tagService.getAllTagsByTask(taskId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    // Task에서 Tag 단건 조회
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping(ApiMappingPattern.Tags.TASK_TAG)
    public ResponseEntity<ResponseDto<TagResponse.TagNameResponse>> getTagById(
            @PathVariable ("taskId") @Positive(message = "tagId는 1 이상이어야 해요.")Long taskId,
            @PathVariable ("tagId") @Positive(message = "tagId는 1 이상이어야 해요.")Long tagId
    ) {
        ResponseDto<TagResponse.TagNameResponse> response = tagService.getTaskTag(taskId, tagId);

        return ResponseEntity.ok().body(response);
    }

    // Project에서 Tag 단건 조회
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping(ApiMappingPattern.Tags.TAG_ID)
    public ResponseEntity<ResponseDto<TagResponse.TagNameResponse>> getTagByTagId(
            @PathVariable ("projectId") @Positive(message = "projectId는 1 이상이어야 해요.") Long projectId,
            @PathVariable ("tagId") @Positive(message = "tagId는 1 이상이어야 해요.") Long tagId
    ){
        ResponseDto<TagResponse.TagNameResponse> response = tagService.getTagByTagId(projectId, tagId);

        return ResponseEntity.ok().body(response);
    }

    // Tag 명을 통해 Task 조회
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping("task/by-tag/{tagName}")
    public ResponseEntity<ResponseDto<List<TaskResponse.TaskListResponse>>> getTaskByTagName(
            @PathVariable("projectId") @Positive(message = "projId는 1 이상이어야 해요.") Long projectId,
            @PathVariable("tagName") @NotNull String tagName
            ){
        ResponseDto<List<TaskResponse.TaskListResponse>> response = tagService.getTaskByTagName(projectId,tagName);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 프로젝트에서 Tag 삭제
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @DeleteMapping( ApiMappingPattern.Tags.TAG_ID)
    public ResponseEntity<ResponseDto<TagResponse>> deleteTag(
            @PathVariable("projectId") @Positive(message = "projectId는 1 이상이어야 해요.") Long projId,
            @PathVariable("tagId") @Positive(message = "tagId는 1 이상이어야 해요.") Long tagId
            ){
        ResponseDto<TagResponse> response = tagService.deleteTag(projId,tagId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
