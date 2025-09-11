package com.example.mini_project_task_manager.entity;

import com.example.mini_project_task_manager.common.enums.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role")
@Getter
@NoArgsConstructor
public class Role {
    @Id @Enumerated(EnumType.STRING)
    @Column(name = "roles_name", length = 30, nullable = false)
    private RoleType name;

    public Role(RoleType name) {
        this.name = name;
    }
}
