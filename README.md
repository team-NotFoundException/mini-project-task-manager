# mini-project-task-manager

# 📌 Mini Project Task Manager

팀 프로젝트 협업을 위한 **웹 기반 프로젝트 & 태스크 관리 시스템**입니다.  
사용자는 프로젝트를 생성하고, 태스크를 등록/관리하며, 댓글과 공지를 통해 소통할 수 있습니다.  
또한 **권한(Role)** 기반 접근 제어를 통해 안전하고 체계적인 협업 환경을 제공합니다.  

---

## 🚀 주요 기능

- 회원가입 및 로그인 (JWT 기반 인증)
- 프로젝트 생성 및 관리
- 태스크(Task) 등록, 수정, 삭제
- 댓글 및 공지 작성
- 태그(Tag) 기반 태스크 분류
- 권한(Role) 기반 접근 제어 (USER / AUTHOR / OWNER / ADMIN)

---

## 🛠 기술 스택

### Backend
- **Java 17**
- **Spring Boot**
- **Spring Security + JWT**
- **JPA (Hibernate)**
- **MySQL**


---


## 🗄 데이터베이스 구조

본 프로젝트는 **MySQL**을 기반으로 하며, 총 **9개 주요 테이블**로 구성되어 있습니다.  

### 📌 테이블 목록
1. **users** – 사용자 계정 정보  
2. **roles** – 권한 코드 (USER, AUTHOR, OWNER, ADMIN 등)  
3. **user_roles** – 사용자와 권한 매핑  
4. **projects** – 프로젝트 정보  
5. **tasks** – 프로젝트 내 할일(Task)  
6. **tags** – 태그  
7. **task_tags** – 태스크-태그 매핑 (N:M 관계)  
8. **comments** – 댓글  
9. **notifications** – 프로젝트 공지  

---

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

