package com.example.mini_project_task_manager.repository;

import com.example.mini_project_task_manager.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Long> {
}
