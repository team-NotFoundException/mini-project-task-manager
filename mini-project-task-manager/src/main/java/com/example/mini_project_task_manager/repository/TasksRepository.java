package com.example.mini_project_task_manager.repository;

import com.example.mini_project_task_manager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TasksRepository extends JpaRepository<Task, Long> {

    // 전체 할일 조회(댓글 제외)
    @Query("""
        select t
        from Task t
        where t.project.id = :projectId
        order by t.id desc
    """)
    List<Task> findAllByProjectIdOrderByIdDesc(@Param("projectId") Long projectId);

    // 할일 조회 + 댓글, 태그 까지 즉시 로딩
    @Query("""
        select distinct t
        from Task t
            left join fetch t.contents c
            left join fetch t.taskTags tt
            left join fetch tt.tag tag
        where t.id = :id
    """)
    Optional<Task> findByIdWithCommentsAndTaskTags(@Param("id") Long id);

    // 전체 할일 조회 (projectId + (status || priority) or status && priority
}

/*
    쿼리문 안 쓰고도 대부분 CRUD는 가능하고,
    복잡한 연관관계 조회/조인/즉시로딩 할때는 @Query 쓰자.
 */