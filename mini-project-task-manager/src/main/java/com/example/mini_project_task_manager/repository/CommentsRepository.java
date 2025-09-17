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

    // keyword가 포함된 댓글 찾기
    @Query(value = """
        select 
            id          as commentId,
            content
        from comments
            where content like concat('%', :searchKeyword, '%') 
            order by content desc
    """, nativeQuery = true)
    List<Comment> findByCommentKeyword(@Param("searchKeyword") String searchKeyword);

    // 입력한 작성자와 일치하는 작성자의 모든 댓글 가져오기
    @Query(value = """
        select 
            content     as content,
            author_name as authorName
        from comments c left join users u
            on c.authorId = u.userId 
            where auth like concat('%', :author, '%')
            order by author desc
""",nativeQuery = true)
    List<Comment> findByAuthor(@Param("author") String author);
}
