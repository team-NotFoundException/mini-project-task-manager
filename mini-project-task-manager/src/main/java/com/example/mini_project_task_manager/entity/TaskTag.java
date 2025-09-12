package com.example.mini_project_task_manager.entity;

import com.example.mini_project_task_manager.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
// 테스트용
@Entity
@Table(name="task_tags")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class TaskTag implements Serializable {

    /** PK */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    /** FK tasks에서 가져온 id tags에서 가져온 id를 가져와야한다*/
    @MapsId("task_id")
    @NotNull @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false, foreignKey = @ForeignKey(name = "fk_task_tags_task_id"))
    private List<Task> tasks;

    @MapsId("tag_name")
    @NotNull @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_name", nullable = false, foreignKey = @ForeignKey(name = "fk_task_tags_tag_name"))
    private List<Tag> tags;
}
