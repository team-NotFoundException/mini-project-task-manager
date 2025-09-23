package com.example.mini_project_task_manager.entity;

import com.example.mini_project_task_manager.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "projects",
        uniqueConstraints = {@UniqueConstraint(name = "uq_projects_title", columnNames = "title")}
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends BaseTimeEntity {

    /** PK: 고유 번호 */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    /** 제목 */
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    /** 내용 */
    @Column(name = "content", length = 255)
    private String content;

    /** author_id(users 테이블의 id) 외래키 설정 */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false, foreignKey = @ForeignKey(name = "fk_projects_author_id"))
    private User user;

    @Builder
    private Project(@NotNull String title, String content) {
        this.title = title;
        this.content = content;
    }

    /** Task 생성시 Project에 적용 */
    @OneToMany(mappedBy = "project")
    private List<Task> tasks = new ArrayList<>();
    public void addTask(Task task) {
        if( task == null) return;
        if(!this.tasks.contains(task)){
            tasks.add(task);
            task.setProject(this);
        }
    }

    public void removeTask(Task task){
        if (task == null) return;
        this.tasks.remove(task);
    }

    /** Tag 생성 */
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tag> tags = new ArrayList<>();
    public void addTag(Tag tag) {
        if (tag == null) return;
        if (!this.tags.contains(tag)) {
            tags.add(tag);
            tag.setProject(this);
        }
    }

    /** Tag 삭제 */
    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.setProject(null);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
