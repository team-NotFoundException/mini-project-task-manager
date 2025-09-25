package com.example.mini_project_task_manager.entity;

import com.example.mini_project_task_manager.common.enums.Priority;
import com.example.mini_project_task_manager.common.enums.Status;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.example.mini_project_task_manager.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tasks")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task extends BaseTimeEntity {

    /** PK */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    /** 제목 */
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    /** 내용 */
    @Lob
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(name = "content", nullable = false)
    private String content;

    /** 상태 */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private Status status = Status.TODO;

    /** 우선 순위 */
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false, length = 50)
    private Priority priority = Priority.MEDIUM;

    /** 마감 기한 */
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    /** 해쉬 태그 */
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskTag> taskTags = new HashSet<>();

    /** 작성자 */
    @NotNull
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false, foreignKey = @ForeignKey(name = "fk_tasks_user"))
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false, foreignKey = @ForeignKey(name = "fk_tasks_project"))
    private Project project;

    /** 연관관계 편의 메서드 */
    public void addTag(Tag tag) {
        if (tag == null) return;
        TaskTag taskTag = new TaskTag(this,tag);
        this.taskTags.add(taskTag);
        tag.getTaskTags().add(taskTag);
    }
//
//    public void removeTag(TaskTag taskTag){
//        this.taskTags.remove(taskTag);
//        if (taskTag.getTag() != null) {
//            taskTag.getTag().getTaskTags().remove(taskTag);
//        }
//        taskTag.setTask(null);
//        taskTag.setTag(null);
//    }

    public static Task createTask(
            @NotNull String title, @NotNull String content, User user, Status status, Priority priority, LocalDate dueDate
    ){
        Task task = new Task();
        task.title = title;
        task.content = content;
        task.user = user;
        task.status = (status != null) ? status : Status.TODO;
        task.priority = (priority != null) ? priority : Priority.LOW;
        task.dueDate = dueDate;
        return task;
    }

    /** 변경(수정) 메서드 */
    public void changeContent(String title, String content, Status status, Priority priority, LocalDate dueDate){
        this.title = title;
        this.content = content;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    /** Task 생성시 Project에 적용 */
    void setProject(Project project) {
        this.project = project;
    }

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment) {
        if (comment == null) return;
        if (!this.comments.contains(comment)) {
            this.comments.add(comment);
            comment.setTask(this);
        }
    }

    public void removeComment(Comment comment) {
        if (comment == null) return;
        if (this.comments.remove(comment)) {
            comment.setTask(null);
        }
    }
}