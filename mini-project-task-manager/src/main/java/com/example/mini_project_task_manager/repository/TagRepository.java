package com.example.mini_project_task_manager.repository;

import com.example.mini_project_task_manager.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByTagName(String tag);

    List<Tag> findAll(); // 이거 필요없으면 지우기
    Long id(Long id);

    @Query(value = """
        select tag
        from Tag tag
        where tag.project.id = :projectId
""")
    List<Tag> findTagsByProjectId(@Param("projectId") Long projectId);

    // 프로젝트에 속해있는 모든 태그명 검색
    @Query(value = """
        select tg.tag_name
        from tags tg
        join projects pj on pj.id = tg.project_id
        where tg.project_id = :projectId
""", nativeQuery = true)
    List<String> findAllTagsByProjectId(@Param("projectId") Long projectId);
    // 에러나면 tag로 고치기



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


}
