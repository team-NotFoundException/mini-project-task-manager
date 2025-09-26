package com.example.mini_project_task_manager.repository;

import com.example.mini_project_task_manager.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // keyword가 포함된 댓글 찾기
    @Query(value = """
        select 
            *
        from comments
            where content like concat('%', :searchKeyword, '%') 
            order by content desc
    """, nativeQuery = true)
    List<Comment> findByCommentKeyword(@Param("searchKeyword") String searchKeyword);

    // 입력한 작성자와 일치하는 작성자의 모든 댓글 가져오기
    @Query(value = """
        select 
            c.*
        from comments c left join users u
            on c.author_id = u.id 
            where u.nickname like concat('%', :author, '%')
            order by u.nickname desc
""",nativeQuery = true)
    List<Comment> findByAuthor(@Param("author") String author);

    // Task에서 comment 불러오기
    @Query("""
        select c
        from Comment c
        where c.task.id = :taskId
        order by c.createdAt desc
""")
    List<Comment> findByTaskId(@Param("taskId") Long taskId);
}
