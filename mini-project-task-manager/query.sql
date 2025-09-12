DROP DATABASE IF EXISTS `mini-project-task-manager`;
CREATE DATABASE `mini-project-task-manager`;

USE `mini-project-task-manager`;

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
    id         		BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username    	VARCHAR(50) NOT NULL,
    password    	VARCHAR(255) NOT NULL,
    nickname    	VARCHAR(50) NOT NULL,
    email       	VARCHAR(255) NOT NULL,
    gender       	VARCHAR(10),
    created_at    	DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at    	DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),

    CONSTRAINT `uq_users_username` UNIQUE (username),
    CONSTRAINT `uq_users_email` UNIQUE (email),
    CONSTRAINT `uq_users_nickname` UNIQUE (nickname),
    CONSTRAINT `chk_users_gender` CHECK (gender IN ('MALE','FEMALE'))

) 	ENGINE=InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_unicode_ci
    COMMENT = '사용자';

DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (
    role_name	VARCHAR(30) PRIMARY KEY
)	ENGINE=InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_unicode_ci
    COMMENT = '권한';

DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE IF NOT EXISTS `user_roles` (
    user_id    BIGINT NOT NULL,
    role_name  VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, role_name),

    CONSTRAINT `fk_user_roles_user` FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT `fK_user_roles_role` FOREIGN KEY (role_name) REFERENCES roles (role_name) ON DELETE CASCADE

) 	ENGINE=InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_unicode_ci
    COMMENT = '사용자 권한';

DROP TABLE IF EXISTS `projects`;
CREATE TABLE IF NOT EXISTS `projects`(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    author_id       BIGINT NOT NULL,
    title   		VARCHAR(200) NOT NULL,
    content 		VARCHAR(255),
    created_at      DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at      DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),

    CONSTRAINT `fk_projects_author_id` FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT `uq_projects_title` UNIQUE (title)

) 	ENGINE=InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_unicode_ci
    COMMENT = '프로젝트';

DROP TABLE IF EXISTS `tasks`;
CREATE TABLE IF NOT EXISTS `tasks`(
    id          	BIGINT PRIMARY KEY AUTO_INCREMENT,
    project_id  	BIGINT NOT NULL,
    author_id   	BIGINT NOT NULL,
    title       	VARCHAR(200) NOT NULL,
    content      	LONGTEXT NOT NULL,
    status      	VARCHAR(50) NOT NULL DEFAULT 'TODO',
    priority    	VARCHAR(50) NOT NULL DEFAULT 'MEDIUM',
    due_date    	DATE NOT NULL,
    created_at  	DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at  	DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),

    CONSTRAINT `chk_tasks_status` CHECK (status IN ('TODO','IN_PROGRESS','DONE')),
    CONSTRAINT `chk_tasks_priority` CHECK (priority IN ('LOW','MEDIUM','HIGH')),
    CONSTRAINT `fk_tasks_project_id` FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
    CONSTRAINT `fk_tasks_author_id` FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE CASCADE,

    INDEX idx_tasks_project_id_status (project_id, status),
    INDEX idx_tasks_author_id_due_date (author_id, due_date)

) 	ENGINE=InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_unicode_ci
    COMMENT = '할일';

DROP TABLE IF EXISTS `tags`;
CREATE TABLE IF NOT EXISTS `tags`(
    tag_name    VARCHAR(100) PRIMARY KEY
) 	ENGINE=InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_unicode_ci
    COMMENT = '태그';

DROP TABLE IF EXISTS `task_tags`;
CREATE TABLE IF NOT EXISTS `task_tags` (
    task_id     BIGINT NOT NULL,
    tag_name   VARCHAR(100) NOT NULL,
    PRIMARY KEY (task_id, tag_name),
    CONSTRAINT `fk_task_tags_task_id` FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE,
    CONSTRAINT `fk_task_tags_tag_name` FOREIGN KEY (tag_name) REFERENCES tags(tag_name) ON DELETE CASCADE
)   ENGINE=InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_unicode_ci
    COMMENT ='태스크 태그';

DROP TABLE IF EXISTS `comments`;
CREATE TABLE IF NOT EXISTS `comments` (
    id          	BIGINT PRIMARY KEY AUTO_INCREMENT,
    task_id      	BIGINT NOT NULL,
    author_id    	BIGINT NOT NULL,
    content      	TEXT NOT NULL,
    created_at   	DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at  	DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),

    CONSTRAINT `fk_comments_task_id` FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE,
    CONSTRAINT `fk_comments_author_id` FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE CASCADE

) 	ENGINE=InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_unicode_ci
    COMMENT = '댓글';

DROP TABLE IF EXISTS `notifications`;
CREATE TABLE IF NOT EXISTS `notifications` (
    id          	BIGINT PRIMARY KEY AUTO_INCREMENT,
	author_id    	BIGINT NOT NULL,
    title      		VARCHAR(300) NOT NULL,
    content    		TEXT NOT NULL,
    created_at   	DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    
	CONSTRAINT `fk_notifications_author_id` FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE CASCADE

) 	ENGINE=InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_unicode_ci
    COMMENT = '공지';

USE `mini-project-task-manager`;