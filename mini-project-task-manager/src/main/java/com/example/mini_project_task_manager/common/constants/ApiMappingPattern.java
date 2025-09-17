package com.example.mini_project_task_manager.common.constants;

public class ApiMappingPattern {
    public static final String API ="/api";
    public static final String V1 ="/v1";
    public static final String BASE = API + V1;


    // == AUTH == //
    public static final class Auth{
        private Auth() {}
        public static final String ROOT = BASE + "/auth";

        // admin은 만들어야 함
        // /api/vi/admin (ROOT)
        // /api/vi/admin/roles/replace (권한 갱신)
        // /api/vi/admin/roles/add (권한 추가)
        // /api/vi/admin/roles/remove (권한 삭제)

        // /api/v1/auth/ (ROOT)
        // /api/v1/auth/sign-up (회원가입)
        // /api/v1/auth/sign-in (로그인)
    }

    public static final class Users {
        private Users() {}
        public static final String ROOT = BASE + "/users";
        // /api/v1/users


    }

    public static final class Projects {
        private Projects () {}
        public static final String ROOT = BASE + "/projects";
    }

    public static final class Tasks {
        private Tasks() {}
        public static final String ROOT = BASE +"/tasks";


    }

    public static final class Tags{
        private Tags() {}
        public static final String ROOT = BASE + "/tags";

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

    public static final class Admin {}

}
