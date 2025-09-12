package com.example.mini_project_task_manager.entity;

import com.example.mini_project_task_manager.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 엔티티 설계 완료
@Entity
@Table(
        name = "tags")

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    /** 태그명(PK값) */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    /** 태그 이름 */
    @NotNull
    @Column(name = "tag_name", updatable = false)
    private String tag_name;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false, foreignKey = @ForeignKey(name = "fk_tags_project_id"))
    private Project project;

    // 메서드 //
    public Tag (String tag_name){ this.tag_name = tag_name; }


    // 태그 생성
    public static Tag create(
            String tag_name){
        Tag tag = new Tag();
        tag.tag_name = tag_name;
        return tag;

    }




}