package com.example.mini_project_task_manager.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Entity
@Table(name="task_tags")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaskTag{

    @EmbeddedId
    private TaskTagId id;

    @MapsId("taskId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "task_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_task_tags_task_id")
    )
    private Task task;

    @MapsId("tagId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "tag_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_task_tags_tag_id")
    )
    private Tag tag;

    public TaskTag(Task task, Tag tag){
        this.task = task;
        this.tag = tag;
        Long taskId = task.getId();
        Long tagId = tag.getId();
        this.id = new TaskTagId(taskId, tagId);
    }
}