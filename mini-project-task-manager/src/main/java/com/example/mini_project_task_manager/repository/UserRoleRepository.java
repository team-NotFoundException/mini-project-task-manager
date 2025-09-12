package com.example.mini_project_task_manager.repository;

import com.example.mini_project_task_manager.entity.UserRole;
import com.example.mini_project_task_manager.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
    List<UserRole> findByIdUserId(Long userId);
}
