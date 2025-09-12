package com.example.mini_project_task_manager.entity;

import com.example.mini_project_task_manager.common.enums.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Table(name = "user_roles")
@Getter
@NoArgsConstructor
public class UserRole  {
    @EmbeddedId
    private UserRoleId id;
// 테스트에 쓸
    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_user_roles_user")
    )
    private User user;

    @MapsId("roleName")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "role_name",
            nullable = false,
            referencedColumnName = "role_name",
            foreignKey = @ForeignKey(name = "fk_user_roles_role")

    )
    private Role role;

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;

        Long userId = user.getId();
        RoleType roleName = role.getName();
        this.id = new UserRoleId(userId, roleName);
    }

}
