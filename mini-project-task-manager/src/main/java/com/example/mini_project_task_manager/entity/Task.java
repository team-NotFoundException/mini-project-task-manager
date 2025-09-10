package com.example.mini_project_task_manager.entity;

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

    /** 담당자 */
    @Column(name = "author", nullable = false, length = 100)
    private String author;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false, foreignKey = @ForeignKey(name = "fk_task_project"))
    private Project project;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false, foreignKey = @ForeignKey(name = "fk_task_user"))
    private User user;

    // Task N <-> Tag N 다대다 관계
    @ElementCollection(fetch = FetchType.LAZY)
    // 할일 리스트목록에 tag가 바로 안보이게 설정할 때, 이거 주석처리하면 Set<Tag>에 오류안남.
    @ManyToMany
    @CollectionTable(   // `task_tags` table 만들때 아래와 비교할것
            name = "task_tags",
            joinColumns = @JoinColumn(name = "task_id", foreignKey = @ForeignKey(name = "fk_task_tags"))
            , uniqueConstraints = @UniqueConstraint(name = "uk_task_tags", columnNames = {"task_id", "tag"})
    )
    @Column(name = "tag", length = 30)
    @JoinColumn(name = "tag_id", foreignKey = @ForeignKey(name = "fk_task_tag"))
    @Enumerated(EnumType.STRING)
    private Set<Tag> tags = new HashSet<>(); // 중복 제거위해 Set 사용


    // ===== Enum 작성 ==== // )) Enum 작성완료`
    // 태경님이 Enum 만드시면 import 할 것

    /** 상태 */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private Status status;

    /** 우선 순위 */
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false, length = 50)
    private Priority priority;

    /** 매개변수 5개지만 필수 3개만 선택해서 build 가능 */
    @Builder
    public Task(@NotNull String title, @NotNull String content,
                @NotNull String author, Status status, Priority priority) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.status = (status == null ? Status.TODO : status);
        this.priority = (priority == null ? Priority.MEDIUM : priority);
        this.tags = (tags == null || tags.isEmpty())? new HashSet<>(Set.of(Tag)) : tags;
    }

    void setTag(Tag tag) {
        this.tag = tag;
    }
}

/*
    65번줄 / 90번줄 어떻게 해야할지 모르겠음.
 */