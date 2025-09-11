package com.example.mini_project_task_manager.entity;

import com.example.mini_project_task_manager.common.enums.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.management.relation.Role;

@Entity
@Table(name = "user_roles")
@Getter
@NoArgsConstructor
public class UserRole {
    @EmbeddedId
    private UserRoleId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_user_roles_user")
    )
    private user user;

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
        RoleType roleName = role.getRoleName();
        this.id = new UserRoleId(userId, roleName);
    }

}
