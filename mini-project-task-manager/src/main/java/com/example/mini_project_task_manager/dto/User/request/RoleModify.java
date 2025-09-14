package main.java.com.example.mini_project_task_manager.dto.User.request;

importcom.example.mini_project_task_manager.common.enums.RoleType;

import jakarta.validation.constraints.NotNull;

public class RoleModify {
    @NotNull
    RoleType role;

}
