package com.example.mini_project_task_manager.service.impl;

import com.example.mini_project_task_manager.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// C: AUTHOR
// R: USER
// D: AUTHOR

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService {
}
