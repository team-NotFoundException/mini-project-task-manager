CREATE DATABASE `mini-project-task-manager`;

CREATE TABLE `tasks`(
	id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    author_id	BIGINT NOT NULL,
	project_id  BIGINT NOT NULL,
	title       VARCHAR(200) NOT NULL,
	content		TEXT NOT NULL,
    author		VARCHAR(100) NOT NULL,
	status      VARCHAR(50) NOT NULL DEFAULT 'TODO',
	priority    VARCHAR(50) NOT NULL DEFAULT 'MEDIUM',
	due_date    DATE NOT NULL,
	created_at  DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at  DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
	CONSTRAINT fk_task_status check(status IN('TODO','IN_PROGRESS','DONE')),
	CONSTRAINT fk_task_priority check(priority IN('LOW','MEDIUM','HIGH')),
	CONSTRAINT fk_task_project  FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
	CONSTRAINT fk_task_user FOREIGN KEY (author_id) REFERENCES users(id),

	INDEX idx_task_project_status (project_id, status),
	INDEX idx_task_author_due (author_id, due_date)	

) ENGINE=InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  COMMENT = '할일';



USE `mini-project-task-manager`;