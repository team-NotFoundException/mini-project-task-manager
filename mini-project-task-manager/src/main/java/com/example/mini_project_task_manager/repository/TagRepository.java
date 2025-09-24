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
}
