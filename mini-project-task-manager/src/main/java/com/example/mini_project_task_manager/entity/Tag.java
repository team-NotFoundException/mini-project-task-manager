package com.example.mini_project_task_manager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "tags",
        uniqueConstraints = @UniqueConstraint(name = "uq_tags_tag_name_project_id", columnNames = {"tag_name", "project_id"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    /** 태그명(PK값) */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    /** 태그 이름 */
    @NotNull
    @Column(name = "tag_name")
    private String tagName;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false, foreignKey = @ForeignKey(name = "fk_tags_project_id"))
    private Project project;

    @Builder
    public Tag (
            @NotNull String tag_name
    ){
        this.tagName = tag_name;
    }

    public static Tag create(String tag_name){
        Tag tag = new Tag();
        tag.tagName = tag_name;
        return tag;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    void setTag(Tag tag) {
        this.tagName = tagName;
    }

    @OneToMany(mappedBy = "tag")
    private Set<TaskTag> taskTags = new HashSet<>();
}