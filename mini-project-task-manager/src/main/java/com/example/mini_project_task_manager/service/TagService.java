package com.example.mini_project_task_manager.service;

import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.tag.request.TagRequest;
import com.example.mini_project_task_manager.dto.tag.response.TagResponse;
import com.example.mini_project_task_manager.dto.task.response.TaskResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public interface TagService {
    ResponseDto<TagResponse.TagNameResponse> createTagByProject(@Positive(message = "projId는 1 이상이여야합니다.") Long projId, TagRequest.@Valid TagCreateRequest dto);
    ResponseDto<TagResponse> deleteTag(@Positive(message = "projId는 1 이상이여야합니다.") Long projId, Long tagId);
    ResponseDto<List<TagResponse.TagNameResponse>> getAllTagsByProjectId(Long projectId);
    ResponseDto<TagResponse.TagNameResponse> getTagByTagId(long projectId, long tagId);
    ResponseDto<List<TaskResponse.TaskListResponse>> getTaskByTagName(Long projectId,@NotBlank(message = "tagName은 공백이 안되요.") @Size(max= 100, message = "tagName은 최대 100자까지 가능해요") String tagName);
    ResponseDto<List<TagResponse.TagNameResponse>> getAllTagsByTask(@Positive(message = "tagId는 1 이상이어야 해요.") Long taskId);
    ResponseDto<TagResponse.TagNameResponse> getTaskTag(Long taskId, Long tagId);
}

