package com.example.mini_project_task_manager.repository;

import com.example.mini_project_task_manager.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Long> {
    public interface CommentListProjection {

        Long getId();
        String Content();
        String getAuthor();
    }
    @Query(value = """
        select 
            id,
            content
        from comment
            where content like concat('%', :searchKeyword, '%') 
            order by content desc
    """, nativeQuery = true)
    List<Comment> findByCommentKeyword(@Param("searchKeyword") String searchKeyword);

    @Query(value = """
        select 
            id,
            author
        from comment
            where auth like concat('%', :author, '%')
            order by author desc
""",nativeQuery = true)
    List<Comment> findByAuthor(@Param("author") String author);
}
