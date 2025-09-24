package com.example.mini_project_task_manager.repository;

import com.example.mini_project_task_manager.dto.project.response.ProjectResponse;
import com.example.mini_project_task_manager.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectRepositoryCustom {

    // 전체 조회(내림차순)
//    @Query(value = """
//    select
//        p.id            as "고유번호",
//        p.title         as "제목",
//        u.nickname      as "작성자",
//        p.created_at    as "생성일"
//    from
//        projects p
//        left join p.users u
//        on u.id = p.author_id
//    order by
//        p.created_at desc
//    """, nativeQuery = true)
//    List<Project> findAllProjectsByCreatedAt();


    // 작성자 id로 조회
    @Query(value = """
	select 
		projects.*
	from 
		projects
		left join users
		on users.id = projects.author_id
	where users.id = :author_id
	order by 
		projects.created_at asc
    """, nativeQuery = true)
    List<Project> findProjectsByAuthorId(@Param("author_id") Long author_id);


    // 키워드 검색 (제목+개요)
    @Query(value = """
	select 
		distinct projects.*
	from 
		projects
		left join users
		on users.id = projects.author_id
	where projects.content like concat('%', :searchKeyword, '%') or projects.title like concat('%', :searchKeyword, '%')
	order by 
		projects.created_at asc
    """, nativeQuery = true)
    List<Project> searchProjectsByKeyword(@Param("searchKeyword") String searchKeyword) ;
           
	Optional<Project> findProjectById(Long projectId);

}
