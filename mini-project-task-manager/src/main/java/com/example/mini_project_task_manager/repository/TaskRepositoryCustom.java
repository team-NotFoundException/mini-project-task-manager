package com.example.mini_project_task_manager.repository;

import com.example.mini_project_task_manager.common.enums.Priority;
import com.example.mini_project_task_manager.common.enums.Status;
import com.example.mini_project_task_manager.entity.Task;

import java.util.List;

public interface TaskRepositoryCustom {
    /** 상태/우선순위에 따른 Task 목록 조회 */
    List<Task> searchTasks(Long projectId, Status status, Priority priority);
}
