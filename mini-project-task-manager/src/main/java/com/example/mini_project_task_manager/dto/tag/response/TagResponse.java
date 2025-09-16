package com.example.mini_project_task_manager.dto.tag.response;


import com.example.mini_project_task_manager.entity.Tag;

public class TagResponse {

    // 태그 이름만 반환하면 된다.
    public record TagNameResponse(
            String tag_name){
        public static TagNameResponse from(Tag tag){
            if (tag == null) return null;

            return new TagNameResponse(
                    tag.getTag_name()
            );
        }
    }
}
