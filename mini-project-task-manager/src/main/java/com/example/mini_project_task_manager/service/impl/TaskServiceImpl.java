package com.example.mini_project_task_manager.service.impl;

import com.example.mini_project_task_manager.common.enums.Priority;
import com.example.mini_project_task_manager.common.enums.Status;
import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.task.request.TaskRequest;
import com.example.mini_project_task_manager.dto.task.response.TaskResponse;
import com.example.mini_project_task_manager.entity.Project;
import com.example.mini_project_task_manager.entity.Tag;
import com.example.mini_project_task_manager.entity.Task;
import com.example.mini_project_task_manager.entity.User;
import com.example.mini_project_task_manager.repository.ProjectRepository;
import com.example.mini_project_task_manager.repository.TagsRepository;
import com.example.mini_project_task_manager.repository.TaskRepository;
import com.example.mini_project_task_manager.repository.UserRepository;
import com.example.mini_project_task_manager.security.UserPrincipal;
import com.example.mini_project_task_manager.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TagsRepository tagsRepository;

    @Override
    @Transactional
    public ResponseDto<TaskResponse.TaskDetailResponse> createTask(
            UserPrincipal principal, Long projectId, TaskRequest.@Valid TaskCreateRequest dto) {

        Project project = projectRepository.findById(projectId)
                        .orElseThrow(() -> new EntityNotFoundException("해당 id의 project를 찾을 수 없습니다."));

        User user = userRepository.findById(principal.getId())
                .orElseThrow(()-> new EntityNotFoundException("로그인 사용자를 찾을 수 없습니다."));

//        Tag tag = tagsRepository.findById()
//                .orElseThrow(() -> new EntityNotFoundException("등록된 태그가 없습니다. 등록하세요.");

        String title = dto.title().trim();
        String content = dto.content().trim();
        Status status = dto.status();
        Priority priority = dto.priority();
        LocalDate dueDate = dto.dueDate();

        Task task = Task.createTask(title, content, user, status, priority, dueDate);
        project.addTask(task);
//        task.addTag();
        Task saved = taskRepository.save(task);

        /** 태그 등록
         * 1. Project에 귀속된 Tag가 있다면, 선택해서 TaskTag에 추가 할 수도 있고
         * 2. Task 생성시에 새로운 Tag를 만들어서도 사용 가능
         * */

        // 해당 project에 Tag 가 등록되어있다면, 거기서 골라서 쓰게 해서 TaskTag에 추가해야됨.

        //if (project.getTags())
        //TaskTag taskTag = new TaskTag(task, TaskTag)

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

        Task task = taskRepository.findById(taskId)
                .orElseThrow(()-> new EntityNotFoundException("해당 id의 Task를 찾을 수 없습니다."));

        userRepository.findById(principal.getId())
                .orElseThrow(()-> new EntityNotFoundException("작성자를 찾을 수 없습니다."));

        if (!task.getProject().getId().equals(projectId)){
            throw new IllegalArgumentException("해당 Task가 프로젝트 내에 속해있지 않습니다. ");
        }

        task.changeContent(dto.title(), dto.content(),dto.status(), dto.priority(), dto.dueDate());

        // 여기도 역시나 Tag 관련 있어야함.

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

        Project project = task.getProject();
        project.removeTask(task);

        return ResponseDto.setSuccess("SUCCESS", null);
    }
}
