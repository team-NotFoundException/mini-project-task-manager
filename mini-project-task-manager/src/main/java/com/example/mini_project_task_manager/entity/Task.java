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

import java.util.HashSet;
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
    @JdbcTypeCode(SqlTypes.LONGVARCHAR) // MySql LONGTEXT와 호환
    @Column(name = "content", nullable = false)
    private String content;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false, foreignKey = @ForeignKey(name = "fk_tasks_project"))
    private Project project;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false, foreignKey = @ForeignKey(name = "fk_tasks_user"))
    private User user;

    // Task N <-> Tag N 다대다 관계
    // @ElementCollection(fetch = FetchType.LAZY)
    // 할일 리스트목록에 tag가 바로 안보이게 설정할 때, 이거 주석처리하면 Set<Tag>에 오류안남.
    @ManyToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
//    @CollectionTable(   // `task_tags` table 만들때 아래와 비교할것
//            name = "task_tags",
//            joinColumns = @JoinColumn(name = "task_id", foreignKey = @ForeignKey(name = "fk_task_tags"))
//            , uniqueConstraints = @UniqueConstraint(name = "uq_task_tags", columnNames = {"task_id", "tag"})
//    )
//    @Column(name = "tag", length = 30)
//    @JoinColumn(name = "tag_id", foreignKey = @ForeignKey(name = "fk_task_tag"))
    @Enumerated(EnumType.STRING)
    private Set<Tag> tags = new HashSet<>(); // 중복 제거위해 Set 사용


    // ===== Enum 작성 ==== // )) Enum 작성완료`
    // 태경님이 Enum 만드시면 import 할 것

    /** 상태 */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private Status status = Status.TODO;        // 디폴트 값 설정

    /** 우선 순위 */
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false, length = 50)
    private Priority priority = Priority.MEDIUM;

    @Builder
    public Task(@NotNull String title, @NotNull String content,
                @NotNull User user, Status status, Priority priority) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.status = (status != null) ? status : Status.TODO;
        this.priority = (priority != null) ? priority : Priority.MEDIUM;
        this.tags = (tags == null || tags.isEmpty())? new HashSet<>(Set.of()) : tags;
    }

    void addTags(Tag tag) {
        tags.add(tag); // Task 클래스의 tags필드 변수에 tag빌더로 만들어진 tag들을 추가한다.
        tag.setTask(this); // Tag 클래스에 있어야 할까.?
    }
}
