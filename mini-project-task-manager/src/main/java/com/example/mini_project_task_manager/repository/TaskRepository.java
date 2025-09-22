package com.example.mini_project_task_manager.repository;

import com.example.mini_project_task_manager.entity.Task;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    public interface TaskListProjection {
        Long getTaskId();
        String getTitle();
        String getStatus();
        String getPriority();
    }

//    @Query("""
//        SELECT
//                t.id as taskId,
//                t.title as title,
//                t.status as status,
//                t.priority as priority
//        FROM
//            task t
//        ORDER BY t.id desc
//""")
//    List<TaskListProjection> findTaskSummary( )

    // 전체 할일 조회(제목, 상태, 우선순위)
    @Query("""
        select t
        from Task t
        where t.project.id = :projectId
        order by t.id desc
    """)
    List<Task> findByProjectIdOrderByIdDesc(@Param("projectId") Long projectId);

    // 할일 조회 + 댓글, 태그 까지 즉시 로딩
    @Query("""
        select distinct t
        from Task t
            left join fetch t.comments c
            left join fetch t.taskTags tt
            left join fetch tt.tag tag
        where t.project.id = :projectId and t.id = :id
    """)
    Optional<Task> findByIdWithCommentsAndTaskTags(@Param("projectId") Long projectId, @Param("id") Long id);

    // 전체 할일 조회 status와 priority 선택된 값만 조회
//    List<Task> findByProjectIdAndStatusAndPriority()

}
