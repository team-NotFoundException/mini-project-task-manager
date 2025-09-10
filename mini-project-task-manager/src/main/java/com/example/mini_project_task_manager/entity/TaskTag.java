package com.example.mini_project_task_manager.entity;

import com.example.mini_project_task_manager.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="task_tags")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class TaskTag extends BaseTimeEntity {

    /** PK */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    /** FK tasks에서 가져온 id tags에서 가져온 id를 가져와야한다*/
    @NotNull @ManyToMany
    @JoinColumn(name = "task_id", nullable = false, foreignKey = @ForeignKey(name = "fk_tasks_id"))
    private List<Task> tasks;

    @NotNull @ManyToMany
    @JoinColumn(name = "tag_id", nullable = false, foreignKey = @ForeignKey(name = "fk_tags_id"))
    private List<Tag> tags;
}
