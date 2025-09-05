package com.example.mini_project_task_manager.service.impl;

import com.example.mini_project_task_manager.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// C: USER
// R: USER
// U: OWNER
// D: OWNER

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {
}
