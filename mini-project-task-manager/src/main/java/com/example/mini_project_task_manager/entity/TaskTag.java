package com.example.mini_project_task_manager.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 연관관계의 주인 = FK를 가진쪽
 */

// 엔티티 설계 완료
@Entity
@Table(name="task_tags")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaskTag{

    @EmbeddedId
    private TaskTagId id;

    @MapsId("taskId")
    // 복합키(TaskTagId)의 taskId 필드를 Task의 PK에 매핑
    // 해당 필드와 연관된 FK(task_id) 값이 해당 엔티티의 식별자(PK) 중 taskId 라는 필드를 채움
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "task_id", // PK(id) 기본 참조
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_task_tags_task_id")
    )
    private Task task;

    // @MapsId("tagName") @MapsId는 해당 엔티티의 PK값을 참조할때만 사용
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "tag_name",
            nullable = false,
            referencedColumnName = "tag_name", // 대상 엔티티가 PK가 아닌 컬럼을 FK로 참조!
            foreignKey = @ForeignKey(name = "fk_task_tags_tag_name")
    )
    private Tag tag;

    public TaskTag(Task task, Tag tag){
        this.task = task;
        this.tag = tag;

        Long taskId = task.getId();
        String tagName = tag.getTag_name();
        this.id = new TaskTagId(taskId, tagName);
    }
}