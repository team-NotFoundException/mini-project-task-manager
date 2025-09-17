package com.example.mini_project_task_manager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
// 엔티티 설계 완료
@Entity
@Table(
        name = "tags",
        uniqueConstraints = @UniqueConstraint(name = "uk_tags_tag_name", columnNames = "tag_name"))

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    /** 태그명(PK값) */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    /** 태그 이름 */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @Column
    private String tag_name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false, foreignKey = @ForeignKey(name = "fk_tags_project_id"))
    private Project project;

    // 메서드 //
    public Tag (
            @NotNull String tag_name
    ){
        this.tag_name = tag_name;
    }


    // 태그 생성
    public static Tag create(@NotNull String tag_name){
        Tag tag = new Tag();
        tag.tag_name = tag_name;
        return tag;
    }


    // tag 생성/삭제 시 project에 공유
    void setProject(Project project) {
        this.project = project;
    }

    void setTag(Tag tag) {
        this.tag_name = tag_name;
    }

    // Tag : Task = N:N -> TaskTag 중간 테이블
    // TaskTag 안에 tag 필드
    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskTag> taskTags = new HashSet<>();
}