CREATE TABLE IF NOT EXISTS `users` (
	id BIGINT NOT NULL AUTO_INCREMENT,
    login_id VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    nickname VARCHAR(50) NOT NULL,
    gender VARCHAR(10),
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT `uk_users_login_id` UNIQUE (login_id),
    CONSTRAINT `uk_users_email` UNIQUE (email),
    CONSTRAINT `uk_users_nickname` UNIQUE (nickname),
    CONSTRAINT `chk_users_gender` CHECK(gender IN ('MALE', 'FEMALE'))
) ENGINE=InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  COMMENT = '사용자';

  CREATE TABLE IF NOT EXISTS `user_roles` (
    user_id VARCHAR(255) NOT NULL,
    role VARCHAR(30) NOT NULL,

    CONSTRAINT fk_user_roles_user_id
        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,

    CONSTRAINT uk_user_roles UNIQUE (user_id, role),

    CONSTRAINT chk_user_roles_role CHECK (role IN ('USER', 'AUTHOR','OWNER','ADMIN'))
) ENGINE=InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  COMMENT = '사용자 권한';