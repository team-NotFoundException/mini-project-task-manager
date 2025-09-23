package com.example.mini_project_task_manager.entity;

import com.example.mini_project_task_manager.common.enums.Gender;
import com.example.mini_project_task_manager.common.enums.RoleType;
import com.example.mini_project_task_manager.entity.base.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

// 엔티티 설계 완료
@Entity
@Table(name = "users",
        uniqueConstraints = {
            @UniqueConstraint(name = "uq_users_username", columnNames = "username"),
            @UniqueConstraint(name = "uq_users_email", columnNames = "email"),
            @UniqueConstraint(name = "uq_users_nickname", columnNames = "nickname")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    /** PK: 고유 번호 */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    /** 로그인 아이디 (유니크) */
    @Column(name = "username", updatable = false, nullable = false, length = 50)
    private String username;

    /** 로그인 비밀번호 (해시 저장 권장 - 암호화) */
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    /** 이메일 (유니크) */
    @Column(name = "email", nullable = false, length = 255)
    private String email;

    /** 닉네임 (유니크) */
    @Column(name = "nickname", nullable = false, length = 50)
    private String nickname;


    // ===== Enum 작성 ==== //
    // 태경님이 Enum 만드시면 import 할 것

    /** 성별 (선택, NULL 허용) */
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 20)
    private Gender gender;

//    private Set<RoleType> roles = new HashSet<>();


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRole> userRoles = new HashSet<>();

    /** 생성 편의 메서드 */
    @Builder
    private User(String username, String password, String email, String nickname, Gender gender, Set<RoleType> roles){
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.gender = gender;
    }


    public void changePassword(String password) {
        this.password = password;
    }

    public void changeProfile(String nickname, Gender gender) {
        this.nickname = nickname;
        this.gender = gender;
    }

        /** 권한 부여/회수 편의 메서드 */
         public void grantRole (Role role) {
             boolean exists = userRoles.stream().allMatch(ur -> ur.getRole().equals(role));
             if (!exists) {

                 userRoles.add(new UserRole(this, role));
             }
         }
         public void revokeRole(Role role) {
        userRoles.removeIf(userRole -> userRole.getRole().equals(role));
    }
    public Set<RoleType> getRoleTypes() {
        return userRoles.stream()
                .map(userRole -> userRole.getRole().getName())
                .collect(Collectors.toUnmodifiableSet());
    }
}
