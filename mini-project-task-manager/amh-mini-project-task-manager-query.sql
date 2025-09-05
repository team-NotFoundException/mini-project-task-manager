CREATE DATABASE `mini-project-task-manager`;

CREATE TABLE `users`(
	id           BIGINT PRIMARY KEY AUTO_INCREMENT,
	project_id   BIGINT NOT NULL,
	title        VARCHAR(200) NOT NULL,
	description  TEXT,
	status       ENUM('TODO','IN_PROGRESS','DONE') NOT NULL DEFAULT 'TODO',
	priority     ENUM('LOW','MEDIUM','HIGH') NOT NULL DEFAULT 'MEDIUM',
	assignee_id  BIGINT NULL,
	due_date     DATE NULL,
	created_at   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	CONSTRAINT fk_task_project  FOREIGN KEY (project_id) REFERENCES projects(id),
	CONSTRAINT fk_task_assignee FOREIGN KEY (assignee_id) REFERENCES users(id),
	INDEX idx_task_project_status (project_id, status),
	INDEX idx_task_assignee_due (assignee_id, due_date)	

);







USE `mini-project-task-manager`;