package com.example.mini_project_task_manager.repository;

import com.example.mini_project_task_manager.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Long> {
    public interface CommentListProjection {
        Long getId();
        String Content();
        String getAuthor();
    }

    List<CommentListProjection> findByCommentKeyword(String searchKeyword);
}
