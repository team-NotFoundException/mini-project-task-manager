package com.example.mini_project_task_manager.entity;

import com.example.mini_project_task_manager.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(
        name = "tags",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_tags_tag_name",
                        columnNames = "tag_name")
        })
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag extends BaseTimeEntity {

    /** PK */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    /** 태그명 */
    @NotNull
    @Column(name = "tag_name", updatable = false)
    private String tag_name;

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