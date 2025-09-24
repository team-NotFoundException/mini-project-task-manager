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

    List<Tag> findAll();
    Long id(Long id);


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
