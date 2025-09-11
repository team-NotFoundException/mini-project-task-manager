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
    USERS ||--o{ USER_ROLES : "has"
    USERS ||--o{ PROJECTS : "owns"
    USERS ||--o{ TASKS : "authors"
    USERS ||--o{ COMMENTS : "writes"

    ROLES ||--o{ USER_ROLES : "maps"
    
    PROJECTS ||--o{ TASKS : "contains"
    PROJECTS ||--o{ NOTIFICATIONS : "announces"

    TASKS ||--o{ COMMENTS : "has"
    TASKS ||--o{ TASK_TAGS : "tagged"
    TAGS ||--o{ TASK_TAGS : "maps"

    USERS {
        bigint id PK
        varchar username UNIQUE
        varchar email UNIQUE
        varchar nickname UNIQUE
        varchar gender CHECK(MALE,FEMALE)
    }

    ROLES {
        varchar role_name PK
    }

    USER_ROLES {
        bigint id PK
        bigint user_id FK
        varchar role CHECK(USER,AUTHOR,OWNER,ADMIN)
    }

    PROJECTS {
        bigint id PK
        bigint owner_id FK
        varchar title UNIQUE
        varchar content
    }

    TASKS {
        bigint id PK
        bigint project_id FK
        bigint author_id FK
        varchar title
        text content
        varchar status CHECK(TODO,IN_PROGRESS,DONE)
        varchar priority CHECK(LOW,MEDIUM,HIGH)
        date due_date
    }

    COMMENTS {
        bigint id PK
        bigint task_id FK
        bigint author_id FK
        text content
    }

    NOTIFICATIONS {
        bigint id PK
        bigint project_id FK
        varchar title
        text content
    }

    TAGS {
        bigint id PK
        varchar tag_name UNIQUE
    }

    TASK_TAGS {
        bigint id PK
        bigint task_id FK
        bigint tag_id FK
        UNIQUE(task_id, tag_id)
    }

