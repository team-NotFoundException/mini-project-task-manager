package com.example.mini_project_task_manager.dto.tag.response;


import com.example.mini_project_task_manager.entity.Tag;

public class TagResponse {

    public record TagNameResponse(
            String tag_name){
        public static TagNameResponse from(Tag tag){
            if (tag == null) return null;

            return new TagNameResponse(
                    tag.getTag_name().toString()
            );
        }
    }

    public record Detailresponse(
            Long id,
            String name

    ){}


}
