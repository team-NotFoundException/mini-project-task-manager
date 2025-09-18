package com.example.mini_project_task_manager.service;

import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.tag.request.TagRequest;
import com.example.mini_project_task_manager.dto.tag.response.TagResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import java.util.List;

public interface TagService {
    ResponseDto<TagResponse> createTag(@Positive(message = "projId는 1 이상이여야합니다.") Long projId, TagRequest.@Valid TagCreateRequest dto);
    ResponseDto<TagResponse> createTagByTask(@Positive(message = "projId는 1 이상이여야합니다.") Long projId, TagRequest.@Valid TagCreateRequest dto);
    ResponseDto<TagResponse> createTagByProject(@Positive(message = "projId는 1 이상이여야합니다.") Long projId, TagRequest.@Valid TagCreateRequest dto);

    ResponseDto<TagResponse> deleteTag(@Positive(message = "projId는 1 이상이여야합니다.") Long projId, Long tagId);


    ResponseDto<List<TagResponse.TagNameResponse>> getAllTags();

    ResponseDto<List<TagResponse.TagNameResponse>> getTagByTagId(long tagId);

    ResponseDto<TagResponse> createTagByTag(TagRequest.@Valid TagCreateRequest dto);
}
