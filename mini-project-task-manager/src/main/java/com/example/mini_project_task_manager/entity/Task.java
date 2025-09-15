package com.example.mini_project_task_manager.entity;

import com.example.mini_project_task_manager.common.enums.Priority;
import com.example.mini_project_task_manager.common.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.example.mini_project_task_manager.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
// 엔티티 설계 완료
@Entity
@Table(name = "tasks")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task extends BaseTimeEntity {

    // 테스트옹

    /** PK */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    /** 제목 */
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    /** 내용 */
    @Lob
    @JdbcTypeCode(SqlTypes.LONGVARCHAR) // MySql LONGTEXT와 호환
    @Column(name = "content", nullable = false)
    private String content;

    /** 상태 */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private Status status = Status.TODO;        // 디폴트 값 설정

    /** 우선 순위 */
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false, length = 50)
    private Priority priority = Priority.MEDIUM;

    /** 마감 기한 */
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    /** 해쉬 태그 */
    // Task N <-> Tag N 다대다 관계
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskTag> taskTags = new HashSet<>();



    @NotNull
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false, foreignKey = @ForeignKey(name = "fk_tasks_project"))
    private Project project;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false, foreignKey = @ForeignKey(name = "fk_tasks_user"))
    private User user;


    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    // update 할때 쓰는 메서드
    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public String tag(Tag tag) {
        this.tag = new Tag(tag.getTag_name());
        return
                /// 수정해야됨.
    }

    /** 생성 편의 메서드 */
    @Builder
    public Task(@NotNull String title, @NotNull String content,
                @NotNull User user, Status status, Priority priority, LocalDate dueDate, String tag_name) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.status = (status != null) ? status : Status.TODO;
        this.priority = (priority != null) ? priority : Priority.MEDIUM;
        this.dueDate = dueDate;
        this.tag = Tag.create(tag_name);
    }

    /** 변경(수정) 메서드 */
    public void changeContent(String title, String content, Status status, Priority priority, Set<TaskTag> tag){
        this.title = title;
        this.content = content;
        this.status = status;
        this.priority = priority;
        this.taskTags = tag;
    }
}