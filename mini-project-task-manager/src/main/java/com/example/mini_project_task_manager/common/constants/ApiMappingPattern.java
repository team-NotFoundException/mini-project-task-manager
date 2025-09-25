package com.example.mini_project_task_manager.common.constants;

public class ApiMappingPattern {
    public static final String API ="/api";
    public static final String V1 ="/v1";
    public static final String BASE = API + V1;

    public static final class Auth{
        private Auth() {}
        public static final String ROOT = BASE + "/auth";
        public static final String SIGN_UP = ROOT + "/sign-up";
        public static final String SIGN_IN = ROOT + "/sign-in";
    }

    public static final class Users {
        private Users() {}
        public static final String ROOT = BASE + "/users";
        public static final String MY_PROFILE = ROOT + "/me";
    }

    public static final class Projects {
        private Projects () {}
        public static final String ROOT = BASE + "/projects";
        public static final String SORTED = "sorted";
        public static final String MY_PROJECT = "/me/{authorId}";
        public static final String SEARCH = "/search";
        public static final String BY_ID = "/{projectId}";
        public static final String PROJECT_TAG = "/{projectId}/tag";
    }

    public static final class Tasks {
        private Tasks() {}
        public static final String ROOT = BASE + "/projects/{projectId}/tasks";
        public static final String BY_ID = "/{taskId}";
    }

    public static final class Tags{
        private Tags() {}
        public static final String ROOT = BASE + "/projects/{projectId}";
        public static final String FROM_TASK = "/tasks/{taskId}/tags";
        public static final String FROM_TAG = "tags";
        public static final String TASK_TAG = FROM_TASK + "/{tagId}";
        public static final String TAG_ID = "/tagId/{tagId}";
        public static final String TAG_NAME = "/task/{tagName}";
    }

    public static final class Notifications {
        private Notifications () {}
        public static final String ROOT = BASE + "/notifications";
        public static final String BY_ID = "/{notificationId}";
        public static final String SEARCH_CONTENT = "/search-content";
    }

    public static final class Comments {
        private Comments () {}
        public static final String ROOT = BASE + "/tasks/{taskId}/comments";
        public static final String SEARCH_CONTENT = "/search-content";
        public static final String SEARCH_AUTHOR = "/search-author";
        public static final String BY_ID = "/{commentId}";
    }

    public static final class Admin {
        private Admin () {}
        public static final String ROOT = BASE + "/admin";
        public static final String USERS = ROOT + "/users";
        public static final String ADD_ROLE=  "/roles/add";
        public static final String REMOVE_ROLE=  "/roles/remove";
    }

}