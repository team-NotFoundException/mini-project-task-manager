package com.example.mini_project_task_manager.repository;

import java.util.Optional;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.mini_project_task_manager.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


  @Query("""
     SELECT u
     FROM User u
       LEFT JOIN FETCH u.userRoles
     WHERE u.username = :loginId

  """)
    Optional<User> findWithRolesByUsername(@Param("username") String userName);

    @EntityGraph(attributePaths = "roles")
    Optional<User> findByLoginName(@Param("username") String username);

    @EntityGraph(attributePaths = "roles")
    Optional<Object> findWithRolesByName(
            @NotNull(message = "username은 필수입니다.")
            @Positive(message = "username은 양수여야 합니다.")
            Long username
    );

    boolean existsByUsername(String userName);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

  Optional<Object> findByUsername(String username);
}
