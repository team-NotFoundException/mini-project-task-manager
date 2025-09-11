package com.example.mini_project_task_manager.entity;

import com.example.mini_project_task_manager.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "comments")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;


    @NotNull @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "task_id", nullable = false, foreignKey = @ForeignKey(name = "fk_comments_task_id"))
    private Task task;

    @NotNull @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "author_id", nullable = false, foreignKey = @ForeignKey(name = "fk_comments_author_id"))
    private User user;

    @NotNull
    @Column(name = "content")
    private String content;


    // 메서드 //
    public Comment (String content) {
        this.content = content;
    }

    // 생성 메서드 //
    public static Comment create(
            Task task, User user, String content) {
        Comment comment = new Comment();
        comment.task = task;
        comment.user = user;
        comment.content = content;
        return  comment;
    }

    // 수정 메서드 //
    public void changeContent(String content) {
        this.content = content;
    }
}
