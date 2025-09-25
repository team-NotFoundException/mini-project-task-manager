package com.example.mini_project_task_manager.dto.tag.response;


import com.example.mini_project_task_manager.entity.Tag;

public class TagResponse {

    public record TagNameResponse(
            String tagName){
        public static TagNameResponse from(Tag tag){
            if (tag == null) return null;

            return new TagNameResponse(
                    tag.getTagName().toString()
            );
        }
        public static TagNameResponse from(String tagName){
            if (tagName == null) return null;

            return new TagNameResponse(tagName);
        }
    }
}
