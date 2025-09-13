package com.example.mini_project_task_manager.dto.pagination;

import java.time.LocalDateTime;


// 해당 인터페이스는 Project, Task 같은 엔터티에서 임포트해와서 쓰면 됩니다.
// 해당 인터페이스는 각 도메인 별 요약한 페이지네이션 쓰고싶으시면 쓰면 됩니다.
/* 아래는 사용 방법입니다.
* @Entity
* public calss 도메인명 implements Summarizable{
*       필드
* }
* */

/**
 * 해당 인터페이스의 역할
 * - 페이지네이션 응답에 포함될 개별 도메인 객체(Task, Noti, Project 등)를 공통된 방식으로 요약(summary) 할 수 있도록 강제하는 인터페이스
 * - 어떤 엔티티든 이 인터페이스만 implements 한다면? => 페이지네이션을 돕는 DTO 생성이 쉬움
 *
 * 아래는 사용 방법입니다. Notification Entity 를 예시로 들어 설명하겠습니다.
 *  @Entity
 *  public class Notification extends BaseTimeEntity implements DomainSummarizable {
 *      @Override
 *      public Long getId() { return this.id; }
 *      @Override
 *      public String getTitle() { return this.title; }
 *      @Override
 *      public LocalDateTime getCreatedAt() { return this.createdAt; }
 *  }
 *
 *
 *
 * */



public interface DomainSummarizable {
    Long getId();
    String getTitle();
    LocalDateTime getCreatedAt();
}
