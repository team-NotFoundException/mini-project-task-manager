package com.example.mini_project_task_manager.entity;

import com.example.mini_project_task_manager.common.enums.Priority;
import com.example.mini_project_task_manager.common.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.example.mini_project_task_manager.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
// 엔티티 설계 완료
@Entity
@Table(name = "tasks")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task extends BaseTimeEntity {

    // 테스트옹

    /** PK */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    /** 제목 */
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    /** 내용 */
    @Lob
    @JdbcTypeCode(SqlTypes.LONGVARCHAR) // MySql LONGTEXT와 호환
    @Column(name = "content", nullable = false)
    private String content;

    /** 상태 */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private Status status = Status.TODO;        // 디폴트 값 설정

    /** 우선 순위 */
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false, length = 50)
    private Priority priority = Priority.MEDIUM;// 디폴트 값 설정

    /** 마감 기한 */
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    /** 해쉬 태그 */
    /* Task N <-> Tag N 다대다 관계
     * ===== 권한 컬렉션 (조인 엔티티) =====
     * mappedBy = "task": TaskTag 엔티티 안의 task 필드가 연관관계의 주인을 뜻함
     * cascade = CascadeType.ALL: TaskTag 생성/삭제 전파
     * orphanRemoval = true: 컬렉션에서 제거되면 join row 삭제
     * */
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskTag> taskTags = new HashSet<>();
    // 처음에는 taskTags는 비어있는 Set 으로 초기화

    // Task : User = N:1
    @NotNull
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false, foreignKey = @ForeignKey(name = "fk_tasks_user"))
    private User user;

    // Task : Project = N:1
    @NotNull
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false, foreignKey = @ForeignKey(name = "fk_tasks_project"))
    private Project project;

    /** 연관관계 편의 메서드 */ // TaskServiceImpl 에서 사용할 메서드
    public void addTag(Tag tag) {
        if (tag == null) return;
        TaskTag taskTag = new TaskTag(this, tag); // 중간 엔티티 생성
        this.taskTags.add(taskTag);                   // Task & TaskTag 관계 세팅
        tag.getTaskTags().add(taskTag);               // Tag & TaskTag 관계 세팅
    }

    public static Task createTask(
            @NotNull String title, @NotNull String content, User user, Status status, Priority priority, LocalDate dueDate
    ){
        Task task = new Task();
        task.title = title;
        task.content = content;
        task.user = user;
        task.status = (status != null) ? status : Status.TODO;
        task.priority = (priority != null) ? priority : Priority.MEDIUM;
        task.dueDate = dueDate;

        return task;
    }

    /** 변경(수정) 메서드 */
    public void changeContent(String title, String content, Status status, Priority priority, Set<TaskTag> tag){
        this.title = title;
        this.content = content;
        this.status = status;
        this.priority = priority;
        this.taskTags = tag;
    }

    // Task 생성/수정/삭제 할때 프로젝트에 공유
    void setProject(Project project) {
        this.project = project;
    }
    
    // List<> ### new ######<>() 에도 @OneToMany 같은 관계형이 선언되어야함
    // 0917  손태경 수정함
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> contents = new ArrayList<>();
    // 팀장 - addComment 메서드 생성하러 왔습니다!
    public void addComment(Comment comment) {
        if (comment == null) return;
        if (!this.contents.contains(comment)) {
            this.contents.add(comment);
            comment.setTask(this);
        }
    }

    public void removeComment(Comment comment) {
        if (comment == null) return;
        if (this.contents.remove(comment)) {
            comment.setTask(null);
        }
    }
}