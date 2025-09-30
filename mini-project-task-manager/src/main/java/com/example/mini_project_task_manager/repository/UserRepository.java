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

  @Query(value = """
    SELECT u.*
    FROM users u
      LEFT JOIN user_roles ur ON u.id = ur.user_id
      LEFT JOIN roles r ON ur.role_id = r.id
    WHERE u.username = :username
""", nativeQuery = true)
  Optional<User> findWithRolesByUsername(@Param("username") String username);

  @EntityGraph(attributePaths = "userRoles")
  Optional<User> findByUsername (@NotNull String username);

  boolean existsByUsername(String username);
  boolean existsByEmail(String email);
  boolean existsByNickname(String nickname);

  Optional<User> findByEmail(@NotBlank @Email String email);

  @NotNull Long id(Long id);

  @Query(value = """
    SELECT u.username
    FROM users u
    WHERE u.nickname = :nickname
      AND u.email = :email
""", nativeQuery = true)
  Optional<FindUsernameResponse> findIdByNicknameAndEmail(
          @Param("nickname") String nickname,
          @Param("email") String email
  );

  boolean existsByNicknameAndIdNot(@NotBlank(message = "공백일 수 없습니다.") @Size(min = 1, max = 16) String nickname, Long id);

  boolean existsByEmailAndIdNot(@NotBlank @Email String email, Long id);
}
