CREATE TABLE IF NOT EXISTS `project`(
	id 				BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_title	VARCHAR(200) NOT NULL,
    project_content	VARCHAR(255)
);