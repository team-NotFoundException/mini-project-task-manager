//package com.example.mini_project_task_manager.repository;
//
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import com.example.mini_project_task_manager.entity.User;
//
//@Repository
//public interface UserRepository extends JpaRepository<User, Long> {
//  @Query("""
//      SELECT u
//    FROM user u
//      WHERE u.loginId = :loginId
//
//    """)
//
//}
