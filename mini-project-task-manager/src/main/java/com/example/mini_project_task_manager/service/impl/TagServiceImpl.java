package com.example.mini_project_task_manager.service.impl;

import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.tag.request.TagRequest;
import com.example.mini_project_task_manager.dto.tag.response.TagResponse;
import com.example.mini_project_task_manager.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// C: AUTHOR
// R: USER
// D: AUTHOR

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService {
    @Override
    public ResponseDto<TagResponse> createTag(Long projId, TagRequest.@Valid TagCreateRequest dto) {
        return null;
    }

    @Override
    public ResponseDto<TagResponse> deleteTag(Long projId, Long tagId) {
        return null;
    }

    @Override
    public ResponseDto<List<TagResponse.TagNameResponse>> getAllTags() {
        return null;
    }
}
