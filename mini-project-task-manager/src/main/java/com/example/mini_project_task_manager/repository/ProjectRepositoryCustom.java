package com.example.mini_project_task_manager.repository;

import com.example.mini_project_task_manager.entity.Project;

import java.util.List;

public interface ProjectRepositoryCustom {
    List<Project>findAllProjectsByCreatedAt(boolean sortedBy);
}
