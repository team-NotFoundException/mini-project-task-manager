package com.example.mini_project_task_manager.dto.tasktag.response;

import com.example.mini_project_task_manager.entity.TaskTag;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record TaskTagResponse (
        String tag_name
){
    public static TaskTagResponse from(TaskTag taskTag) {
        if (taskTag == null) return null;

        return new TaskTagResponse(
               taskTag.getTag().getTagName()
        );
    }
}
