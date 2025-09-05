package com.example.mini_project_task_manager.service.impl;

import com.example.mini_project_task_manager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// C: 권한X -> O
// R: USER 권한
// U: USER 권한
// D: USER 권한

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
}
