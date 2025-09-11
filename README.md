# mini-project-task-manager

## 🔑 권한(Role) 체계

### 1. ADMIN
- **user_roles.role = 'ADMIN'**
- 모든 프로젝트(`projects`), 테스크(`tasks`), 댓글(`comments`), 공지(`notifications`)에 대해 **CRUD 가능**
- 시스템 전체를 관리하는 최고 권한

---

### 2. OWNER
- **user_roles.role = 'OWNER'**  
- 자신이 **생성한 프로젝트(`projects`)**와 그 안의 **테스크(`tasks`), 댓글(`comments`), 공지(`notifications`)**를 **CRUD 가능**
- 판별 기준:  
  - `projects.owner_id = users.id`  
  - 해당 `users.id`는 반드시 OWNER 권한을 가지고 있어야 함
- ✅ 따라서 OWNER는 자신의 프로젝트에는 모든 권한이 있지만, 다른 프로젝트에는 간섭 불가

---

### 3. AUTHOR
- **user_roles.role = 'AUTHOR'**  
- 자신이 **생성한 테스크(`tasks`)**와 그 안의 **댓글(`comments`)**을 **CRUD 가능**
- 판별 기준:  
  - `tasks.author_id = users.id`  
  - 해당 `users.id`는 반드시 AUTHOR 권한을 가지고 있어야 함
- ✅ 따라서 AUTHOR는 자신의 task만 수정/삭제 가능, 다른 사람 task에는 접근 불가

---

### 4. USER
- **user_roles.role = 'USER'**  
- **댓글(`comments`) CRUD**만 가능
- 판별 기준:  
  - `comments.author_id = users.id`

---

## 📌 추가 규칙
1. **OWNER → 본인 프로젝트의 모든 task 관리 가능**
   - 조건:  
     - `tasks.project_id = projects.id`  
     - `projects.owner_id = users.id`  

2. **AUTHOR → 본인 task만 관리 가능**
   - 조건:  
     - `tasks.author_id = users.id`

3. **댓글(Comment)은 USER 이상이면 작성 가능**  
   - 단, `comments.author_id = users.id`일 경우에만 수정/삭제 가능

---

## 📊 ERD 구조 (간단화)

```mermaid
erDiagram
    USERS ||--o{ USER_ROLES : "has"
    USERS ||--o{ PROJECTS : "owns"
    USERS ||--o{ TASKS : "authors"
    USERS ||--o{ COMMENTS : "writes"

    PROJECTS ||--o{ TASKS : "contains"
    PROJECTS ||--o{ NOTIFICATIONS : "announces"

    TASKS ||--o{ COMMENTS : "has"
    TASKS ||--o{ TASK_TAGS : "tagged"
    TAGS ||--o{ TASK_TAGS : "maps"

    USERS {
        bigint id PK
        varchar username
        varchar email
        varchar nickname
        varchar gender
    }

    USER_ROLES {
        bigint id PK
        bigint user_id FK
        varchar role
    }

    PROJECTS {
        bigint id PK
        bigint owner_id FK
        varchar title
        varchar content
    }

    TASKS {
        bigint id PK
        bigint project_id FK
        bigint author_id FK
        varchar title
        varchar status
        varchar priority
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
        varchar tag_name
    }

    TASK_TAGS {
        bigint id PK
        bigint task_id FK
        bigint tag_id FK
    }
