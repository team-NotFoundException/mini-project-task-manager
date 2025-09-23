package com.example.mini_project_task_manager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

// 엔티티 설계 완료
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaskTagId implements Serializable {

    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @Column(name = "tag_id", length = 30, nullable = false)
    private Long tagId;

    public TaskTagId(Long taskId, Long tagId) {
        this.taskId = taskId;
        this.tagId = tagId;
    }
}