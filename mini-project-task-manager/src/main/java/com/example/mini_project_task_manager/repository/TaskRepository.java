package com.example.mini_project_task_manager.repository;

import com.example.mini_project_task_manager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, TaskRepositoryCustom{

    /** projectId 로 Task 전체조회 */
    @Query(value = """
        select *
        from tasks t
        where t.project_id = :projectId
        order by t.id desc
""", nativeQuery = true)
    List<Task> findTasksByProjectId(Long projectId);

    /** Task와 Tag 같이 조회 */
    @Query(value = """
        select distinct t.*
        from tasks t
            join task_tags tt on t.id = tt.task_id
            join tags tag on tt.tag_id = tag.id
        where t.project_id = :projectId and t.id = :id
""", nativeQuery = true)
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

