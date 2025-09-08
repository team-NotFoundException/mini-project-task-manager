
CREATE TABLE IF NOT EXISTS `tag`(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    tag_name VARCHAR(100) NOT NULL,
    CONSTRAINT `uq_tag_name` UNIQUE (tag_name)
)

CREATE TABLE IF NOT EXISTS `tasktag` (
    task_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    CONSTRAINT fk_task FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE,
    CONSTRAINT fk_tag FOREIGN KEY (tag_id) REFERENCES tag(id) ON DELETE CASCADE
    CONSTRAINT uq_tasktag UNIQUE (task_id, tag_id)
);



