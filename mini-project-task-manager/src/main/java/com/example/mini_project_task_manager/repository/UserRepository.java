package com.example.mini_project_task_manager.repository;

import java.util.Optional;

import com.example.mini_project_task_manager.dto.Auth.response.FindUsernameResponse;
import com.example.mini_project_task_manager.dto.ResponseDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
       LEFT JOIN FETCH u.userRoles ur
       LEFT JOIN FETCH ur.role
     WHERE u.username = :username

  """)
  Optional<User> findWithRolesByUsername(@Param("username") String username);

  @EntityGraph(attributePaths = "userRoles")
  Optional<User> findByUsername (@NotNull String username);

  boolean existsByUsername(String username);
  boolean existsByEmail(String email);
  boolean existsByNickname(String nickname);

  Optional<User> findByEmail(@NotBlank @Email String email);


  @NotNull Long id(Long id);

  @Query("""
    SELECT u.username
    FROM User u
    WHERE u.nickname = :nickname and u.email = :email
""")
  Optional<FindUsernameResponse> findIdByNicknameAndEmail(
          @Param("nickname") String nickname,
          @Param("email") String email
  );

  boolean existsByNicknameAndIdNot(@NotBlank(message = "공백일 수 없습니다.") @Size(min = 1, max = 16) String nickname, Long id);

  boolean existsByEmailAndIdNot(@NotBlank @Email String email, Long id);
}
