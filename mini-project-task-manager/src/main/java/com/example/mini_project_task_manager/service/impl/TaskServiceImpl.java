package com.example.mini_project_task_manager.service.impl;

import com.example.mini_project_task_manager.common.enums.Priority;
import com.example.mini_project_task_manager.common.enums.Status;
import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.task.request.TaskRequest;
import com.example.mini_project_task_manager.dto.task.response.TaskResponse;
import com.example.mini_project_task_manager.entity.*;
import com.example.mini_project_task_manager.repository.ProjectRepository;
import com.example.mini_project_task_manager.repository.TagRepository;
import com.example.mini_project_task_manager.repository.TaskRepository;
import com.example.mini_project_task_manager.repository.UserRepository;
import com.example.mini_project_task_manager.security.UserPrincipal;
import com.example.mini_project_task_manager.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TagRepository tagRepository;

    @Override
    @Transactional
    public ResponseDto<TaskResponse.TaskDetailResponse> createTask(
            UserPrincipal principal, Long projectId, TaskRequest.@Valid TaskCreateRequest dto) {

        Project project = projectRepository.findById(projectId)
                        .orElseThrow(() -> new EntityNotFoundException("해당 id의 project를 찾을 수 없습니다."));

        User user = userRepository.findById(principal.getId())
                .orElseThrow(()-> new EntityNotFoundException("로그인 사용자를 찾을 수 없습니다."));

        Task task = Task.createTask(
                dto.title().trim(),
                dto.content().trim(),
                user,
                dto.status(),
                dto.priority(),
                dto.dueDate());

        project.addTask(task);
        // === 여기 까지는 Task 생성하고 project에 적용시키기

        Task saved = taskRepository.save(task);
        // Task먼저 DB에 저장해야 addTag 할 때,
        // new TaskTag(this,tag) 할 때, taskId를 넣을 수 있음,
        // 아니면 taskId setter 나옴 , task 번호가 없는데, addTag에 못넣음.

        /** 직접 입력한 태그 처리 하기 */
        // === 여러 태그 처리 ===
        if (dto.tagNames() != null && !dto.tagNames().isEmpty()){
            for (String tagName : dto.tagNames()) {
                if (tagName == null || tagName.isBlank()) continue;

                // 1. DB에서 기존 Tag 찾아서 없으면 새로만들기.
                Tag tag = tagRepository.findByTagName("#"+tagName.trim())
                        .orElseGet(() -> {
                            // 2. 없으면 새 Tag 생성 후 저장
                            Tag newTag = new Tag("#"+tagName.trim());
                            newTag.setProject(project);
                            // 새 태그 생성시, 관련 프로젝트에도 등록, public안하니까 런타임에러
                            // project 널일수없다고 나옴.
                            return tagRepository.save(newTag);
                        });

                // 3. Task에 Tag 추가 (TaskTag도 자동 생성)
                task.addTag(tag);
            }
        }
        return ResponseDto.setSuccess("SUCCESS", TaskResponse.TaskDetailResponse.from(saved));
    }


    @Override
    public ResponseDto<List<TaskResponse.TaskListResponse>> getAllTasks(Long projectId) {
        List<Task> tasks = taskRepository.findByProjectIdOrderByIdDesc(projectId); // 최신순 반환

        List<TaskResponse.TaskListResponse> result = tasks.stream()
                .map(TaskResponse.TaskListResponse::from)
                .toList();

        return ResponseDto.setSuccess("SUCCESS", result);
    }

    @Override
    public ResponseDto<List<TaskResponse.TaskListResponse>> getTasksByFiltering(
            Long projectId, Status status, Priority priority) {

        return null;
    }

    @Override
    public ResponseDto<TaskResponse.TaskDetailResponse> getTaskById(Long projectId, Long taskId) {
        Task task = taskRepository.findByIdWithCommentsAndTaskTags(projectId, taskId)
                .orElseThrow(()-> new EntityNotFoundException("해당 id의 Task를 찾을 수 없습니다."));

        return ResponseDto.setSuccess("SUCCESS", TaskResponse.TaskDetailResponse.from(task));
    }

    @Override
    @Transactional
    public ResponseDto<TaskResponse.TaskDetailResponse> updateTask(
            UserPrincipal principal, Long projectId, Long taskId, TaskRequest.@Valid TaskUpdateRequest dto) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 project를 찾을 수 없습니다."));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(()-> new EntityNotFoundException("해당 id의 Task를 찾을 수 없습니다."));

        userRepository.findById(principal.getId())
                .orElseThrow(()-> new EntityNotFoundException("작성자를 찾을 수 없습니다."));

        if (!task.getProject().getId().equals(projectId)){
            throw new IllegalArgumentException("해당 Task가 프로젝트 내에 속해있지 않습니다. ");
        }

        task.changeContent(dto.title(), dto.content(),dto.status(), dto.priority(), dto.dueDate());
        Task saved = taskRepository.save(task);
        // Task먼저 DB에 저장해야 addTag 할 때,
        // new TaskTag(this,tag) 할 때, taskId를 넣을 수 있음,
        // 아니면 taskId setter 나옴 , task 번호가 없는데, addTag에 못넣음.

        /** 직접 입력한 태그 처리 하기 */
        // === 여러 태그 처리 ===
        if (dto.tagNames() != null && !dto.tagNames().isEmpty()){
            for (String tagName : dto.tagNames()) {
                if (tagName == null || tagName.isBlank()) continue;

                // 1. DB에서 기존 Tag 찾아서 없으면 새로만들기.
                Tag tag = tagRepository.findByTagName("#"+tagName.trim())
                        .orElseGet(() -> {
                            // 2. 없으면 새 Tag 생성 후 저장
                            Tag newTag = new Tag("#"+tagName.trim());
                            newTag.setProject(project);
                            // 새 태그 생성시, 관련 프로젝트에도 등록, public안하니까 런타임에러
                            // project 널일수없다고 나옴.
                            return tagRepository.save(newTag);
                        });

                // 3. Task에 Tag 추가 (TaskTag도 자동 생성)
                task.addTag(tag);
            }
        }
        return ResponseDto.setSuccess("SUCCESS", TaskResponse.TaskDetailResponse.from(task));
    }

    @Override
    @Transactional
    public ResponseDto<Void> deleteTask(UserPrincipal principal, Long projectId, Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(()-> new EntityNotFoundException("해당 id의 Task를 찾을 수 없습니다."));

        if(!task.getUser().getId().equals(principal.getId())){
            throw new IllegalArgumentException("잘못된 사용자의 접근입니다.");
        }

        if (!task.getProject().getId().equals(projectId)){
            throw new IllegalArgumentException("해당 Task가 프로젝트 내에 속해있지 않습니다. ");
        }

        // new HashSet<> 복사본 만들어서 순회
        for (TaskTag taskTag : new HashSet<>(task.getTaskTags())){
            // TaskTag table에서는 삭제. tags에는 남아있음
            // taskTag 에서 Tag 불러와서. 그 태그에 연결된 TaskTag 삭제
            taskTag.getTag().getTaskTags().remove(taskTag);
        }
        taskRepository.delete(task);

        // 고아 객체 제거 - tag에 연결된 Task가 없다면
        for (TaskTag taskTag2 : new HashSet<>(task.getTaskTags())){
            if (taskTag2.getTag().getTaskTags().isEmpty()){
                tagRepository.delete(taskTag2.getTag());
            }
        }

//        List로 한번에 불러와서 stream 하는 방법
//        List<Tag> orphanTags= tagRepository.findAll().stream()
//                .filter(tag -> tag.getTaskTags().isEmpty())
//                .toList();
//
//        tagRepository.deleteAll(orphanTags);

        Project project = task.getProject();
        project.removeTask(task);

        return ResponseDto.setSuccess("SUCCESS", null);
    }
}
