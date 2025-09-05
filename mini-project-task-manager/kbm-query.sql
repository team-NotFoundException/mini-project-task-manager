create table comments (
	id 		bigint primary key auto_increment,
    task_id	bigint not null,
    author_id bigint not null,
    content	text not null,
    create_at datetime(6) not null default current_timestamp,
    constraint fk_comment_task foreign key (task_id) references tasks(id) on delete cascade,
    constraint fk_comment_author foreign key (author_id) references user(id)
) engine=InnoDB
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci
  comment = '댓글';

create table notifications (
	id 		bigint primary key auto_increment,
    project_id bigint not null,
    author_id bigint not null,
    title	varchar(300) not null,
    content text not null,
    created_at datetime(6) not null default current_timestamp,
    constraint fk_noti_project foreign key (project_id) references project(id) on delete cascade,
    constraint fk_noti_author foreign key (author_id) references user(id) 
) engine=InnoDB
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci
  comment = '공지';