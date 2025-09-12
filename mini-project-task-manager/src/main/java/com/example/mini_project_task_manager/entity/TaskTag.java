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
public class TaskTag{
    
    @EmbeddedId
    private TaskTagId id;

    // 복합키의 taskId를 Task의 PK에 매핑
    @MapsId("taskId")
    // 해당 필드와 연관된 FK(task_id) 값이 해당 엔티티의 식별자(PK) 중 taskId 라는 필드를 채움
    // : 주로 복합키 매핑에 사용
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "task_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_task_tags_task_id")
    )
    private Task task;

    // 복합키의 tag_name을 Tag의 PK에 매핑
    @MapsId("tagName")
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

    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @Column(name = "tag_name", length = 30, nullable = false)
    private String tagName;


//    /** FK tasks에서 가져온 id tags에서 가져온 id를 가져와야한다*/
//    @NotNull @ManyToMany
//    @JoinColumn(name = "task_id", nullable = false, foreignKey = @ForeignKey(name = "fk_tasks_id"))
//    private List<Task> tasks;
//
//    @NotNull @ManyToMany
//    @JoinColumn(name = "tag_id", nullable = false, foreignKey = @ForeignKey(name = "fk_tags_id"))
//    private List<Tag> tags;

}
