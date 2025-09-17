package com.example.mini_project_task_manager.dto.tasktag.response;

import com.example.mini_project_task_manager.entity.TaskTag;

public record TaskTagResponse (
        String tag_name
){
    public static TaskTagResponse from(TaskTag taskTag) {
        if (taskTag == null) return null;

        return new TaskTagResponse(
               taskTag.getTag().getTag_name()
        );
    }
}
