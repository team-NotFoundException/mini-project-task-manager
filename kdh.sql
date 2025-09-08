CREATE TABLE IF NOT EXISTS `project`(
	id 				BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_title	VARCHAR(200) NOT NULL,
    project_content	VARCHAR(255),
    user_id 		BIGINT NOT NULL,
    tag_id			BIGINT NOT NULL,
    task_id			BIGINT NOT NULL,
    CONSTRAINT fk_project_user
		FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
	CONSTRAINT fk_project_tag
		FOREIGN KEY (tag_id) REFERENCES tag(id) ON DELETE CASCADE,
	CONSTRAINT fk_project_task
		FOREIGN KEY (task_id) REFERENCES task(id) ON DELETE CASCADE
);