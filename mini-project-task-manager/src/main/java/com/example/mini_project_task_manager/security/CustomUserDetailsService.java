package com.example.mini_project_task_manager.security;

import com.example.mini_project_task_manager.entity.User;
import com.example.mini_project_task_manager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserPrincipalMapper userPrincipalMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String Username = (username == null) ? "" : username.trim();
        if(Username.isEmpty()) throw new UsernameNotFoundException("사용자를 찾을 수 없습니다. [ " + username + " ]");

        User auth = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다. [ " + username + " ]"));

        return userPrincipalMapper.map(auth);

    }
}