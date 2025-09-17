package com.example.mini_project_task_manager.repository;

import com.example.mini_project_task_manager.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    // 전체 조회(내림차순)
    @Query(value = """
    select 
        p.id            as "고유번호",
        p.title         as "제목",
        u.username      as "작성자",
        p.created_at    as "생성일"
    from
        projects p
        left join p.users u
        on u.id = p.author_id
    order by
        p.created_at desc
    """, nativeQuery = true)
    List<Project> findAllProjectsByCreatedAtDesc();


    // 전체 조회(오름차순)
    @Query(value = """
    select 
        p.id            as "고유번호",
        p.title         as "제목",
        u.username      as "작성자",
        p.created_at    as "생성일"
    from
        projects p
        left join p.users u
        on u.id = p.author_id
    order by
        p.created_at asc
    """, nativeQuery = true)
    List<Project> findAllProjectsByCreatedAtAsc();


    // 작성자 id로 조회
    @Query(value = """
	select 
		p.id 			as "고유번호",
		p.title			as "제목",
		u.username 	    as "작성자",
		p.created_at 	as "생성일"
	from 
		projects p
		left join  p.users u
		on u.id = p.author_id
	where u.id = p.author_id
	order by 
		p.created_at asc
    """, nativeQuery = true)
    List<Project> findProjectsByAuthorId(@Param("authorId") String authorId);


    // 키워드 검색 (제목+개요)
    @Query(value = """
	select distinct 
	    p.id 			as "고유번호",
		p.title			as "제목",
	 	u.username 	    as "작성자",
		p.created_at 	as "생성일"
	from 
		projects p
		left join  p.users u
		on u.id = p.author_id
	where p.content like concat('%', :searchKeyword, '%') or p.title like concat('%', :searchKeyword, '%')
		p.created_at asc
    """, nativeQuery = true)
    List<Project> searchProjectsByKeyword(@Param("searchKeyword") String searchKeyword) ;


}
