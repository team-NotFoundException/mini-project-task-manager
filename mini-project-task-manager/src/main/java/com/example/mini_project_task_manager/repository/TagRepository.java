package com.example.mini_project_task_manager.repository;

import com.example.mini_project_task_manager.entity.Project;
import com.example.mini_project_task_manager.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    // 특정 프로젝트안의 tagId로 검색하기
    @Query(value = """
    select tg.tag_name
    from tags tg
    join projects pj on pj.id = tg.project_id
    where pj.id = :projectId and tg.id = :tagId
""",nativeQuery = true)
    Optional<String> findTagsByTagId(@Param("projectId")Long taskId, @Param("tagId")Long tagId);


    @Query(value = """
        select tag
        from Tags tag
        where tag.project.id = :projectId
""",nativeQuery = true)
    List<Tag> findTagsByProjectId(@Param("projectId") Long projectId);

    // 프로젝트에 속해있는 모든 태그명 검색
    @Query(value = """
        select tg.tag_name
        from tags tg
        join projects pj on pj.id = tg.project_id
        where tg.project_id = :projectId
""", nativeQuery = true)
    List<String> findAllTagsByProjectId(@Param("projectId") Long projectId);

    @Query(value= """
    select tg.*
        from tags tg
        join task_tags tt on tg.id = tt.tag_id
        join tasks t on t.id = tt.task_id
    where t.id = :taskId
    order by tg.tag_name desc
""", nativeQuery = true)
    List<Tag> findTaskTagsAll(@Param("taskId")Long taskId);

    @Query(value = """
    select tg.*
        from tags tg
        join task_tags tt on tg.id = tt.tag_id
        join tasks t on t.id = tt.task_id
    where t.id = :taskId and tg.id = :tagId
    order by tg.tag_name desc
""", nativeQuery = true)
    Optional<Tag> findTaskTag(@Param("taskId")Long taskId, @Param("tagId")Long tagId);


    Long project(Project project);
}
