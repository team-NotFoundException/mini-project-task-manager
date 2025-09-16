package com.example.mini_project_task_manager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/**
 * == 복합키 (task_tags PK) ==
 * : task_id + tag_name
 * */

// 엔티티 설계 완료
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaskTagId implements Serializable {

    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @Column(name = "tag_name", length = 30, nullable = false)
    private String tagName;

    public TaskTagId(Long taskId, String tagName) {
        this.taskId = taskId;
        this.tagName = tagName;
    }
}