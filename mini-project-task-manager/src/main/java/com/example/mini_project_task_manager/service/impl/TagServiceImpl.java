package com.example.mini_project_task_manager.service.impl;

import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.tag.request.TagRequest;
import com.example.mini_project_task_manager.dto.tag.response.TagResponse;
import com.example.mini_project_task_manager.dto.task.response.TaskResponse;
import com.example.mini_project_task_manager.entity.Project;
import com.example.mini_project_task_manager.entity.Tag;
import com.example.mini_project_task_manager.entity.Task;
import com.example.mini_project_task_manager.repository.ProjectRepository;
import com.example.mini_project_task_manager.repository.TagRepository;
import com.example.mini_project_task_manager.repository.TaskRepository;
import com.example.mini_project_task_manager.service.TagService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;


import java.util.List;

@Validated
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    @Override
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @Transactional
    public ResponseDto<TagResponse.TagNameResponse> createTagByProject(
           Long projId, TagRequest.TagCreateRequest dto) {

        TagResponse.TagNameResponse data = null;
        Project project = projectRepository.findProjectById((projId))
                .orElseThrow(() -> new EntityNotFoundException("해당 프로젝트를 찾을 수 없어요."));
        String clean = (dto.tagName() == null) ? "" : dto.tagName().trim();

       if (clean.isEmpty() || clean == null){
            throw new IllegalArgumentException("태그는 빈공간 안됩니다.");
       }

       Tag tag = Tag.create(clean);
       project.addTag(tag);
       Tag saved = tagRepository.save(tag);
       data = TagResponse.TagNameResponse.from(saved)
       return ResponseDto.setSuccess("태그가 등록되었어요", data);
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @Transactional
    public ResponseDto<TagResponse> deleteTag(
            Long projId, Long tagId) {

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(()-> new EntityNotFoundException("해당 태그를 찾을 수 없어요"));
        tagRepository.delete(tag);
        return ResponseDto.setSuccess("태그 삭제",null);
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN','USER')")
    public ResponseDto<List<TagResponse.TagNameResponse>> getAllTagsByProjectId(Long projectId) {
        List<String> tags = tagRepository.findAllTagsByProjectId(projectId);

        if (tags.isEmpty()){
            throw new EntityNotFoundException("해당 프로젝트에 태그가 존재하지 않습니다");
        }

        List<TagResponse.TagNameResponse> data = tags.stream()
                .map(TagResponse.TagNameResponse::from)
                .toList();

        return ResponseDto.setSuccess("검색된 프로젝트에 포함된 전체 태그 조회", data);
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN','USER')")
    public ResponseDto<TagResponse.TagNameResponse> getTagByTagId(
            long projectId, long tagId) {

        String tagName = tagRepository.findTagsByTagId(projectId, tagId)
                .orElseThrow(() -> new EntityNotFoundException("태그는 존재하나 현재 프로젝트에 존재하지 않습니다"));

        TagResponse.TagNameResponse data = TagResponse.TagNameResponse.from(tagName);
        return ResponseDto.setSuccess("태그 조회 완료", data);
    }

    private Long requirePositiveId(Long id){
        if (id == null || id <= 0) throw new IllegalArgumentException("ID는 반드시 양수여야 해요.");
        return id;
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN','USER')")
    public ResponseDto<List<TaskResponse.TaskListResponse>> getTaskByTagName(
            Long projectId, String tagName) {

        String clean = (tagName == null) ? "" : tagName.trim();

        if (clean.isEmpty()){
            throw new IllegalArgumentException("태그명은 비어 있을 수 없어요.");
        }
        if (clean.length() > 100){
            throw new IllegalArgumentException("태그명은 최대 100자까지에요.");
        }

        List<Task> task = taskRepository.findTaskByTagName(projectId, tagName);
        List<TaskResponse.TaskListResponse> data = task.stream()
                .map(TaskResponse.TaskListResponse::from)
                .toList();

        if (data.isEmpty()){
            throw new EntityNotFoundException("검색된 태스크가 없어요");
        }
        return ResponseDto.setSuccess("태그가 포함된 Task 조회", data);
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN','USER')")
    public ResponseDto<List<TagResponse.TagNameResponse>> getAllTagsByTask(Long taskId) {
        List<Tag> tags = tagRepository.findTaskTagsAll(taskId);

        List<TagResponse.TagNameResponse> data = tags.stream()
                .map(TagResponse.TagNameResponse::from)
                .toList();

        if (data.isEmpty()){
            throw new EntityNotFoundException("검색된 태그가 없습니다.");
        }
        return ResponseDto.setSuccess("전체 태그 조회", data);
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN','USER')")
    public ResponseDto<TagResponse.TagNameResponse> getTaskTag(
            Long taskId, Long tagId) {
        TagResponse.TagNameResponse data = null;
        Tag tag = tagRepository.findTaskTag(taskId, tagId)
                .orElseThrow(()-> new EntityNotFoundException("해당 id의 태그를 찾을 수 없어요."));

        data = TagResponse.TagNameResponse.from(tag);

        return ResponseDto.setSuccess("태그 조회 완료", data);
    }

}
