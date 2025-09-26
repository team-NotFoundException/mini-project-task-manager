package com.example.mini_project_task_manager.repository;


import com.example.mini_project_task_manager.common.enums.Priority;
import com.example.mini_project_task_manager.common.enums.Status;
import com.example.mini_project_task_manager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // 프로젝트 id로 Task 조회
    @Query("""
        select t
        from Task t
        where t.project.id = :projectId
""")
    List<Task> findTasksByProjectId(Long projectId);

    // 전체 할일 조회 +) 상태 / 우선순위 선택여부에 따라 조회
    List<Task> searchTasks(Long projectId, Status status, Priority priority, LocalDateTime fromUtc, LocalDateTime toUtc, LocalDate dueFrom, LocalDate dueTo);

    // Task + 태그 조회
    @Query("""
        select distinct t
        from Task t
            left join fetch t.taskTags tt
            left join fetch tt.tag tag
        where t.project.id = :projectId and t.id = :id
""")
    Optional<Task> findByIdWithTaskTags(@Param("projectId") Long projectId,@Param("id") Long id);

    // 태그이름으로 태스크 검색
    @Query(value = """
        select t.*
            from tasks t
            join task_tags tt on t.id = tt.task_id
            join tags tg on tg.id = tt.tag_id
        where tg.tag_name = :tagName AND t.project_id =:projectId
""", nativeQuery = true)
    List<Task> findTaskByTagName(@Param("projectId")Long projId,@Param("tagName") String tagName);
}

