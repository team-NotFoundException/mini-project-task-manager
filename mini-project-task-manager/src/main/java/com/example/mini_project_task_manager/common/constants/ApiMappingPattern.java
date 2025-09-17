package com.example.mini_project_task_manager.common.constants;

public class ApiMappingPattern {
    public static final String API ="/api";
    public static final String V1 ="/v1";
    public static final String BASE = API + V1;


    // == AUTH == //
    public static final class Auth{
        private Auth() {}
        public static final String ROOT = BASE + "/auth";
        public static final String SIGN_UP = ROOT + "/sign_up";
        public static final String SIGN_IN = ROOT + "/sign_in";


    }

    public static final class Users {
        private Users() {}
        public static final String ROOT = BASE + "/users";
        public static final String MY_PROFILE = ROOT + "/my_profile";
        public static final String UPDATE_PROFILE = ROOT + "/update_profile";

    }

    public static final class Projects {
        private Projects () {}
        public static final String ROOT = BASE + "/projects";
        public static final String ALL = ROOT + "/all";
        public static final String DESC = ALL + "/desc";
        public static final String ASC = ALL + "/asc";
        public static final String BY_AUTHOR_ID = ROOT + "/{author_id}";
//        public static final String BY_TITLE = ROOT + "/{title}";
        public static final String BY_KEYWORD = ROOT + "/search-project";
        public static final String BY_ID = ROOT + "/{id}";

    }

    public static final class Tasks {
        private Tasks() {}
        public static final String ROOT = BASE + "/projects/{projectId}";
        public static final String ALL = ROOT + "/tasks";
        public static final String BY_ID = ROOT + ALL+ "/{taskId}";

        // ROOT                 "/api/v1/projects/{projectId}"
        // Task 조회 (전체조회)   "/api/v1/projects/{projectId}/tasks"
        // Task 조회 (단건조회)   "/api/v1/projects/{projectId}/tasks/{taskId}"
        // Task 수정             "/api/v1/projects/{projectId}/tasks/{taskId}"
        // Task 삭제             "/api/v1/projects/{projectId}/tasks/{taskId}"
    }

    public static final class Tags{
        private Tags() {}

        public static final String ROOT = BASE + "/tags";
        public static final String TAG_ID = "/{tagId}";
    }

    public static final class Notifications {
        private Notifications () {}
        public static final String ROOT = BASE + "/notifications";
        public static final String All = ROOT + "/all";
        public static final String SEARCHID = ROOT + "/{notiId}";
        public static final String SEARCHKEYWORD = ROOT + "/search-notification";

    }

    public static final class Commnets {
        private Commnets () {}
        public static final String ROOT = BASE + "/tasks";
        public static final String COMMENT = ROOT + "/{taskId}/comments";
        public static final String FINDKEYWORD = ROOT + "/search-comment";
        public static final String FINDAUTHOR = COMMENT + "/auth/{author}";
        public static final String COMMENTID = COMMENT + "/{commnetsId}";
    }

    public static final class Admin {
        private Admin () {}
        public static final String ROOT = BASE + "/Admin";
        public static final String REPLACE = ROOT + "/roles/replace";
        public static final String ADD = ROOT + "/roles/add";
        public static final String REMOVE = ROOT + "/roles/remove";
    }

}
