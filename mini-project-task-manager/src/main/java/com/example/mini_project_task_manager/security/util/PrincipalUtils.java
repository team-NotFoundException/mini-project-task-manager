package com.example.mini_project_task_manager.security.util;


import com.example.mini_project_task_manager.security.UserPrincipal;
import org.springframework.security.access.AccessDeniedException;

public class PrincipalUtils {
    private PrincipalUtils() {}

    public static void requiredActive(UserPrincipal principal) {
        if (principal == null) {
            throw new AccessDeniedException("인증 필요");
        }
        if (!principal.isAccountNonLocked() || !principal.isEnabled() || !principal.isAccountNonExpirde()) {
            throw new AccessDeniedException("비활성화 된 계정");
        }
    }


}
