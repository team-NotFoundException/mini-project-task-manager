package com.example.mini_project_task_manager.repository;

import com.example.mini_project_task_manager.common.enums.RoleType;
import com.example.mini_project_task_manager.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, RoleType> {
}
