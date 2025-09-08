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


    @NotNull @OneToOne
    @JoinColumn(name = "task_id", nullable = false, foreignKey = @ForeignKey(name = "fk_comment_task"))
    private Task task;

    @NotNull @ManyToOne
    @JoinColumn(name = "author_id", nullable = false, foreignKey = @ForeignKey(name = "fk_comment_author"))
    private User user;

    @NotNull @NotBlank
    @Column(name = "content", updatable = false)
    private String content;


    // 메서드 //
    public Comment (String content) {
        this.content = content;
    }

    // 생성 메서드 //
    public Comment create(String content) {
        return new Comment(content);
    }

    void setTask(Task task) {
        this.task = task;
    }

    public void changeContent(String content) {
        this.content = content;
    }
}
