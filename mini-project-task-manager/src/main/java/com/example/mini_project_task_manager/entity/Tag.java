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
        name = "tags")

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    /** 태그명(PK값) */
    @Id @NotNull
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