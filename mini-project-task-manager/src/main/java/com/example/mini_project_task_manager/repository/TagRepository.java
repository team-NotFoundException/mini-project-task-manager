package com.example.mini_project_task_manager.repository;

import com.example.mini_project_task_manager.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findAll();



    Long id(Long id);
}
