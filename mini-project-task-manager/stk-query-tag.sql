
CREATE TABLE IF NOT EXISTS `tag`(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    tag_name VARCHAR(100) NOT NULL,
    CONSTRAINT `uq_tag_name` UNIQUE (tag_name)
)

-- 프라이머리 키 : 테이블에서 각 행을 유일하게 식별하는 컬럼
-- NOT NULL에 중복불가면 자연스럽게 PRIMARY KEY가 된다.

CREATE TABLE IF NOT EXISTS `tasktag` (
    task_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY (task_id, tag_id),
    CONSTRAINT fk_task FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE,
    CONSTRAINT fk_tag FOREIGN KEY (tag_id) REFERENCES tag(id) ON DELETE CASCADE,
);



