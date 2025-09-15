package com.example.mini_project_task_manager.repository;

import com.example.mini_project_task_manager.common.enums.Auth;
import com.example.mini_project_task_manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {
}
