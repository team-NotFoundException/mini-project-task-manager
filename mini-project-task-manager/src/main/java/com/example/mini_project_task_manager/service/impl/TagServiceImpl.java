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

import java.util.List;


@Service
@RequiredArgsConstructor
//@Transactional(readOnly = true)
public class TagServiceImpl implements TagService {


    private final TagRepository tagRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    @Override
    public ResponseDto<TagResponse.TagNameResponse> createTagByProject(Long projId, TagRequest.@Valid TagCreateRequest dto) {
        // 프로젝트에서 태그 생성
        // project의 아이디를 찾을수 없습니다 -> 프로젝트의 부재시 보내야할 메시지 필요
        Project project = projectRepository.findProjectById((projId))
                .orElseThrow(() -> new EntityNotFoundException("해당 프로젝트를 찾을 수 없어요."));

        Tag tag = Tag.create(dto.tagName());
        project.addTag(tag);
        Tag saved = tagRepository.save(tag);

        return ResponseDto.setSuccess("태그가 등록되었어요", TagResponse.TagNameResponse.from(saved));

    }

    @Override
    public ResponseDto<TagResponse> createTagByTask(Long taskId, TagRequest.@Valid TagCreateRequest dto) {
        // task 안에 tag를 넣는다고 가정을 해보자
        // project는 바로 검색이 가능하게 되었지만
        // projectId도 검색, taskId도 검색? taskId만 검색하기?


        return null;
    }


    @Override
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @Transactional
    public ResponseDto<TagResponse> deleteTag(Long projId, Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(()-> new EntityNotFoundException("해당 태그를 찾을 수 없어요"));

        tagRepository.delete(tag);
        return ResponseDto.setSuccess("태그 삭제",null);
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN','USER')")
    public ResponseDto<List<TagResponse.TagNameResponse>> getAllTags() {
        List<Tag> tags = tagRepository.findAll();
        List<TagResponse.TagNameResponse> result = tags.stream()
                .map(TagResponse.TagNameResponse::from)
                .toList();

        return ResponseDto.setSuccess("전체 태그 조회", result);
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN','USER')")
    @Transactional
    public ResponseDto<TagResponse.TagNameResponse> getTagByTagId(long tagId) {
        Long stagId = requirePositiveId(tagId);

        Tag tag = tagRepository.findById(stagId)
                .orElseThrow(()-> new EntityNotFoundException("해당 id의 태그를 찾을 수 없어요."));

        return ResponseDto.setSuccess("태그 조회 완료", TagResponse.TagNameResponse.from(tag));
    }

    private Long requirePositiveId(Long id){
        if (id == null || id <= 0) throw new IllegalArgumentException("ID는 반드시 양수여야 해요.");
        return id;
    }


    // 태그이름 검색 -> 태그가 포함된 task(태스크) 조회
    @Override
    public ResponseDto<List<TaskResponse.TaskListResponse>> getTaskByTagName(String tagName) {
        List<Task> task = taskRepository.findTaskByTagName(tagName);
        List<TaskResponse.TaskListResponse> result = task.stream()
                .map(TaskResponse.TaskListResponse::from)
                .toList();

        return ResponseDto.setSuccess("태그가 포함된 Task 조회", result);
    }

}
