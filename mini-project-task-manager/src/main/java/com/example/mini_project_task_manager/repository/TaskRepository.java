package com.example.mini_project_task_manager.repository;

import com.example.mini_project_task_manager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // 전체 할일 조회(댓글 제외)
    @Query("""
        select t
        from Task t
        where t.project.id = :projectId
        order by t.id desc
    """)
    List<Task> findAllOrderByIdDesc(@Param("projectId") Long projectId);

    // 할일 조회 + 댓글, 태그 까지 즉시 로딩
    @Query("""
        select distinct t
        from Task t
            left join fetch t.comments c
            left join fetch t.taskTags tt
            left join fetch tt.tag tag
        where t.id = :id
    """)
    Optional<Task> findByIdWithCommentsAndTaskTags(@Param("id") Long id);

    // 전체 할일 조회 (projectId + (status || priority) or status && priority
}
