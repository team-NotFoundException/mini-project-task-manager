package com.example.mini_project_task_manager.common.constants;

public class ApiMappingPattern {
    public static final String API ="/api";
    public static final String V1 ="/v1";
    public static final String BASE = API + V1;


    // == AUTH == //
    public static final class Auth{
        private Auth() {}
        public static final String ROOT = BASE + "/auth";
        public static final String SIGN_UP = ROOT + "/sign-up";
        public static final String SIGN_IN = ROOT + "/sign-in";


    }

    public static final class Users {
        private Users() {}
        public static final String ROOT = BASE + "/users";

        // 로그인 한 유저의 본인 데이터 활용 (조회/수정/삭제 등)
        public static final String MY_PROFILE = ROOT + "/me";


    }

    public static final class Projects {
        private Projects () {}
        public static final String ROOT = BASE + "/projects";
        public static final String SORTED = ROOT + "/sorted";
        public static final String MY_PROJECT = ROOT + "/me";
        public static final String SEARCH = "/search";
        public static final String BY_ID = "/{projectId}";
        public static final String PROJECT_TAG = ROOT + "/project/{projectId}/tag";
    }

    public static final class Tasks {
        private Tasks() {}

        public static final String ROOT = BASE + "/projects/{projectId}/tasks";
        public static final String BY_ID = BASE + "/{taskId}";
        public static final String TASK = BASE + "/tasks";
        public static final String TASK_BY_ID = TASK + BY_ID;

        // Task에서 Tag 생성
        public static final String TASK_TAG = BASE + "/projects/{projectId}/tasks/{taskId}/tags";

        // 필터링(status, priority - @RequestParam)
        public static final String FILTER_OPTION = "/filtering";

    public static final class Tags{
        private Tags() {}
        public static final String ROOT = BASE + "/tags";
        public static final String All = ROOT + "/all";
        public static final String TAGID = ROOT + "/{tagid}";


    }

    public static final class Notifications {
        private Notifications () {}

        // 컨트롤러 단위
        // 생성, 전체 조회
        public static final String ROOT = BASE + "/notifications";

        // 단건 조회, 삭제
        public static final String BY_ID = "/{notificationId}";

        public static final String SEARCH_CONTENT = "/search-content";
    }

    public static final class Comments {
        private Comments () {}

        // 생성, 전체 조회
        public static final String ROOT = BASE + "/tasks/{taskId}/comments";

        // 내용으로 댓글 검색
        public static final String SEARCH_CONTENT = "/search-content";

        // 작성자별 댓글 검색
        public static final String SEARCH_AUTHOR = "/search-author";

        // 수정, 삭제
        public static final String BY_ID = "/{commnetId}";
    }

    public static final class Admin {
        private Admin () {}
        public static final String ROOT = BASE + "/admin";

        public static final String USERS = ROOT + "/users";
        public static final String USER_ROLES = USERS + "/{userId}/roles";
        public static final String USER_ROLE_MANAGER = USER_ROLES + "/MANAGER";

    }

}
