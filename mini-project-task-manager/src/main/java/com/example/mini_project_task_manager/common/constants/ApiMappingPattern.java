package com.example.mini_project_task_manager.common.constants;

public class ApiMappingPattern {
    public static final String API ="/api";
    public static final String V1 ="/v1";
    public static final String BASE = API + V1;


    // == AUTH == //
    public static final class Auth{
        private Auth() {}
        public static final String ROOT = BASE + "/auth";
    }

    public static final class Users {
        private Users() {}
        public static final String ROOT = BASE + "/users";

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

    }

}
