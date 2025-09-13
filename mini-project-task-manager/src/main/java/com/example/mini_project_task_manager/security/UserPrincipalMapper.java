package com.example.mini_project_task_manager.security;

import com.example.mini_project_task_manager.common.enums.Gender;
import com.example.mini_project_task_manager.entity.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class UserPrincipalMapper {

    @NotNull
    public UserPrincipal map(@NotNull User user) {

        Collection<? extends GrantedAuthority> authorities =
                (user.getUserRoles() == null || user.getUserRoles().isEmpty())
                ? List.of(new SimpleGrantedAuthority("ROLE_USER"))
                : user.getUserRoles().stream()
                        .map(r -> {
                            String name = r.getRole().toString();
                            String role = name.startsWith("ROLE_") ? name : "ROLE_" + name;
                            return new SimpleGrantedAuthority(role);
                        })
                        .toList();

        return UserPrincipal.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
    }

}
