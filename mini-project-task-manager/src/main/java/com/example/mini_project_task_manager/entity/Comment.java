package com.example.mini_project_task_manager.entity;

import com.example.mini_project_task_manager.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
// 엔티티 설계 완료
@Entity
@Table(name = "comments")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotNull @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "task_id", nullable = false, foreignKey = @ForeignKey(name = "fk_comments_task_id"))
    private Task task;

    @NotNull @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false, foreignKey = @ForeignKey(name = "fk_comments_author_id"))
    private User user;

    @NotNull
    @Column(name = "content")
    private String comment;

    public Comment (String content) {
        this.comment = content;
    }

    public static Comment create(String content, User author, Task task) {
        Comment comment = new Comment();
        comment.comment = content;
        comment.user = author;
        comment.task = task;
        return  comment;
    }

    void setTask(Task task) {
        this.task = task;
    }

    public void changeContent(String content) {
        this.comment = content;
    }
}
