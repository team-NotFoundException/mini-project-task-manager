# 📌 Mini Project Task Manager

팀 프로젝트 협업을 위한 **웹 기반 프로젝트 & 태스크 관리 시스템**입니다.  
사용자는 역할(Role)에 따라 프로젝트 생성, 태스크 관리, 댓글/공지 작성 등 다양한 기능을 수행할 수 있습니다.  
본 프로젝트는 **권한(Role) 기반 접근 제어**를 통해 안전하고 체계적인 Task Manager 환경을 제공합니다.  

---

## 🚀 개요

- **MANAGER**: 프로젝트 생성, 태스크 등록/관리, 댓글 작성 가능  
- **ADMIN**: 프로젝트 생성, 태스크 등록/관리, 댓글 및 공지 작성, 권한 부여 가능
- **USER**: 프로젝트 및 태스크 조회, 댓글 작성 가능  

---

## 🛠 주요 기능

- 회원가입 및 로그인 (**JWT 기반 인증**)  
- 프로젝트 생성 및 관리  
- Task 등록, 수정, 삭제 및 조회  
- 댓글 및 공지 작성  
- 태그(Tag) 기반 태스크 분류  
- 권한(Role) 기반 접근 제어 (**USER / MANAGER / ADMIN**)  

---

## ⚙️ 기술 스택

### Backend
- **Java 17**
- **Spring Boot**
- **Spring Security + JWT**
- **JPA (Hibernate)**
- **MySQL**

---

## 🗄 데이터베이스 구조

본 프로젝트는 **MySQL** 기반으로, 총 **9개 주요 테이블**로 구성됩니다.  

- **users** – 사용자 계정 정보  
- **roles** – 권한 코드 (USER, MANAGER, ADMIN)  
- **user_roles** – 사용자-권한 매핑  
- **projects** – 프로젝트 정보  
- **tasks** – 프로젝트 내 할일(Task)  
- **tags** – 태그  
- **task_tags** – 태스크-태그 매핑 (N:M 관계)  
- **comments** – 댓글  
- **notifications** – 프로젝트 공지  

👉 테이블 생성 SQL은 `/mini-project-task-manager/query.sql` 참고  

---

## 📌 사용 방법

1. **DB 생성 및 테이블 초기화**
   ```sql
   CREATE DATABASE `mini-project-task-manager`
       CHARACTER SET utf8mb4
       COLLATE utf8mb4_unicode_ci;
   USE `mini-project-task-manager`;
이후 제공된 query.sql을 실행하여 테이블 생성

초기 권한(roles) 데이터 입력

sql
코드 복사
INSERT INTO roles (role_name) VALUES ('USER');
INSERT INTO roles (role_name) VALUES ('MANAGER');
INSERT INTO roles (role_name) VALUES ('ADMIN');
애플리케이션 실행

Spring Boot 실행 (IntelliJ / VS Code / Terminal 등)

기본 포트: http://localhost:8080

회원가입 후 권한 부여

최초 회원가입 시 USER 권한 부여

필요 시 user_roles 테이블 또는 Admin API를 통해 권한 추가

📡 API 명세
Admin
POST /api/v1/admin/roles/add → 권한 부여

POST /api/v1/admin/roles/remove → 권한 삭제

Auth
POST /api/v1/auth/sign-up → 회원가입

POST /api/v1/auth/sign-in → 로그인

POST /api/v1/auth/find-id → 아이디 찾기

POST /api/v1/auth/reset-password → 비밀번호 재설정

Users
GET /api/users/me → 내 정보 조회

Projects
POST /api/v1/projects → 프로젝트 생성

GET /api/v1/projects/sorted?sortedBy= → 프로젝트 전체 조회

GET /api/v1/projects/me/{authorId} → 내 프로젝트 조회

GET /api/v1/projects/search/?keyword= → 키워드 검색

PUT /api/v1/projects/:projectId → 프로젝트 수정

DELETE /api/v1/projects/:projectId → 프로젝트 삭제

Tasks
POST /api/v1/projects/:projectId/tasks → 태스크 생성

GET /api/v1/projects/{projectId}/tasks?status=&priority=&from=&to=&dueFrom=&dueTo= → 태스크 조회 (필터링 가능)

GET /api/v1/projects/:projectId/tasks/:taskId → 태스크 단건 조회

PUT /api/v1/projects/:projectId/tasks/:taskId → 태스크 수정

DELETE /api/v1/projects/:projectId/tasks/:taskId → 태스크 삭제

Tags
POST /api/v1/projects/:projectId/tags → 태그 생성 (프로젝트)

GET /api/v1/projects/:projectId/tags → 태그 전체 조회 (프로젝트)

GET /api/v1/projects/:projectId/tagId/:tagId → 태그 단건 조회 (프로젝트)

DELETE /api/v1/projects/:projectId/tagId/:tagId → 태그 삭제 (프로젝트)

GET /api/v1/projects/:projectId/tasks/:taskId/tags → 태그 전체 조회 (태스크)

GET /api/v1/projects/:projectId/tasks/by-tag/:tagName → 태그명으로 태스크 조회

GET /api/v1/projects/:projectId/tasks/:taskId/tags/:tagId → 태그 단건 조회 (태스크)

Comments
POST /api/v1/tasks/:taskId/comments → 댓글 작성

PUT /api/v1/tasks/:taskId/comments/:commentId → 댓글 수정

GET /api/v1/tasks/:taskId/comments/search-content?searchKeyword= → 댓글 내용 검색

GET /api/v1/tasks/:taskId/comments/search-author?author= → 댓글 작성자 검색

DELETE /api/v1/tasks/:taskId/comments/:commentId → 댓글 삭제

Notifications (옵션)
POST /api/v1/notifications → 공지 생성

GET /api/v1/notifications → 공지 조회

GET /api/v1/notifications/search-content?keyword= → 공지 키워드 조회

GET /api/v1/notifications/:notificationId → 공지 단건 조회

DELETE /api/v1/notifications/:notificationId → 공지 삭제
### 📊 ERD 다이어그램

```mermaid
erDiagram
    USERS {
        BIGINT id PK
        VARCHAR username UK
        VARCHAR password
        VARCHAR nickname UK
        VARCHAR email UK
        VARCHAR gender
        DATETIME created_at
        DATETIME updated_at
    }

    ROLES {
        VARCHAR role_name PK
    }

    USER_ROLES {
        BIGINT user_id FK
        VARCHAR role_name FK
    }

    PROJECTS {
        BIGINT id PK
        BIGINT author_id FK
        VARCHAR title UK
        VARCHAR content
        DATETIME created_at
        DATETIME updated_at
    }

    TASKS {
        BIGINT id PK
        BIGINT project_id FK
        BIGINT author_id FK
        VARCHAR title
        LONGTEXT content
        VARCHAR status
        VARCHAR priority
        DATE due_date
        DATETIME created_at
        DATETIME updated_at
    }

    TAGS {
        BIGINT id PK
        VARCHAR tag_name UK
        BIGINT project_id FK
    }

    TASK_TAGS {
        BIGINT task_id FK
        BIGINT tag_id FK
    }

    COMMENTS {
        BIGINT id PK
        BIGINT task_id FK
        BIGINT author_id FK
        TEXT content
        DATETIME created_at
        DATETIME updated_at
    }

    NOTIFICATIONS {
        BIGINT id PK
        BIGINT author_id FK
        VARCHAR title
        TEXT content
        DATETIME created_at
    }

    %% 관계 정의

    USERS ||--o{ USER_ROLES : "has"
    ROLES ||--o{ USER_ROLES : "has"

    USERS ||--o{ PROJECTS : "creates"
    PROJECTS ||--o{ TASKS : "has"
    USERS ||--o{ TASKS : "writes"

    PROJECTS ||--o{ TAGS : "has"
    TASKS ||--o{ TASK_TAGS : "tagged"
    TAGS ||--o{ TASK_TAGS : "tagged"

    TASKS ||--o{ COMMENTS : "has"
    USERS ||--o{ COMMENTS : "writes"

    USERS ||--o{ NOTIFICATIONS : "creates"

