package main.java.com.example.mini_project_task_manager.dto.User.request;

public record RoleModify(
        @NotBlank @Size(Max = 50) String nickname,
        Gender gender) {
}
