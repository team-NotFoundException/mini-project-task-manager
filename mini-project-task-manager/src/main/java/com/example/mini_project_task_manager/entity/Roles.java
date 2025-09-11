package com.example.mini_project_task_manager.entity;

import com.example.mini_project_task_manager.common.enums.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rolse")
@Getter
@NoArgsConstructor
public class Roles {
    @Id @Enumerated(EnumType.STRING)
    @Column(name = "role_name", length = 30, nullable = false)
    private RoleType name;

    public Roles(RoleType name) {
        this.name = name;
    }
}
