package com.example.mini_project_task_manager.dto.pagination;

import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * PageMetaResponse 추상 클래스
 *
 * 역할
 * - 공통 페이지네이션 응답 포맷을 정의하는 추상 클래스
 * - "Offset 기반 페이지네이션"과 "Cursor 기반 페이지네이션"을 모두 지원할 수 있도록 PageResponse, SliceResponse 내부 record를 제공
 *
 * 구성
 * - PageMeta: 페이지 번호, 페이지 크기, 총 원소 수, 전체 페이지 수, 정렬 정보 등
 *             (Spring Data JPA의 Page 객체 기반)
 *
 * - PageResponse: Offset 기반 페이지네이션 응답
 *                 → List<DomainSummarizable> + PageMeta
 *                 → 예: `/projects?page=0&size=10`
 *
 * - SliceResponse: Cursor 기반 페이지네이션 응답
 *                  → List<DomainSummarizable> + hasNext + nextCursor
 *                  → 예: `/notifications/cursor?cursorId=15&size=3`
 *
 * 특징
 * - 추상 클래스로 선언했지만, 실제 내부 record 타입(PageResponse, SliceResponse)을 직접 사용함
 * - DomainSummarizable 인터페이스와 함께 사용하면 여러 엔티티(Notification, Task, Project 등)를 하나의 공통 페이지네이션 로직으로 묶을 수 있음
 */

public abstract class PageMetaResponse {
    @Builder
    public record PageMeta(
            int page, int size, long totalElements, int totalPages, boolean hasNext, boolean hasPrevious, String sort
    ) {
        public static PageMeta from(Page<?> p){
            String sort = p.getSort().toString();

            return PageMeta.builder()
                    .page(p.getNumber())
                    .size(p.getSize())
                    .totalElements(p.getTotalElements())
                    .hasNext(p.hasNext())
                    .hasPrevious(p.hasPrevious())
                    .sort(sort)
                    .build();
        }
    }

    //Offset 기반 응답
    @Builder
    public record PageResponse(
            List<DomainSummarizable> content,
            PageMeta meta
    ) {}

    // Cursor 기반 응답
    @Builder
    public record SliceResponse(
            List<DomainSummarizable> content,
            boolean hasNext,
            Long nextCursor
    ) {}
}
