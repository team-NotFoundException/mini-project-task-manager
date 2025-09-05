package com.example.mini_project_task_manager.service.impl;

import com.example.mini_project_task_manager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// C: USER, OWNER
// R: 권한 x
// U: AUTHOR, OWNER
// D: Status가 COMPLETE면 권한 X | Status가 COMPLETE가 아니라면 AUTHOR, OWNER


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {
}
