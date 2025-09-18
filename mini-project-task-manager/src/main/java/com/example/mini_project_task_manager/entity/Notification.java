package com.example.mini_project_task_manager.entity;


import com.example.mini_project_task_manager.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
// 엔티티 설계 완료
@Entity
@Table(name = "notifications")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    /** PK */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "author_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_notifications_author_id"))
    private User user;

    /** 제목 */
    @NotNull
    @Column(name = "title")
    private String title;

    /** 내용 */
    @NotNull
    @Column(name = "content")
    private String content;

    /** 생성시간 */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "DATETIME(6)")
    private LocalDateTime createdAt;


    private Notification(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static Notification create(String title, String content) {
        return new Notification(title, content);
    }
}
