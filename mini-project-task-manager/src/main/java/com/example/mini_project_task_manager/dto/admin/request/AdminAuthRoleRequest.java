
// (admin 전용) 사용자 권한 관리 요청 DTO 패키지
package com.example.mini_project_task_manager.dto.admin.request;

// 사용자 권한 타입을 정의한 Enum
import com.example.mini_project_task_manager.common.enums.RoleType;
// 유효성 검증 어노테이션들
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

// 역할 목록을 위한 Set 컬렉션
import java.util.Set;

/**
 * (Admin 전용) 사용자 권한 관리 요청 DTO
 * 이 클래스는 관리자가 특정 사용자의 권한을 추가, 제거, 갱신할 때 사용하는 요청 객체들을 내부 record로 정의합니다.
 * Controller에서 요청 바디로 받아 Service 계층으로 전달됩니다.
 */
public class AdminAuthRoleRequest {

        /**
         * 특정 사용자의 권한 목록을 새로운 Set으로 교체(갱신)할 때 사용하는 요청 DTO
         * 예) 기존 권한 전체를 새로운 권한 Set으로 대체
         *
         * 사용처: AdminController에서 권한 일괄 갱신 API의 요청 바디로 사용
         */
        public record UpdateRolesRequest(
                        // 갱신 대상 사용자의 username (DB PK)
                        @NotNull(message = "username는 필수 입니다.") @Positive(message = "username는 양수여야 합니다.") Long username,

                        // 새로 부여할 권한 목록 (RoleType Enum의 Set)
                        @NotEmpty(message = "roles는 비어있을 수 없습니다.") Set<@NotNull(message = " roles 항목은 null일 수 없습니다.") RoleType> role) {
                // 별도 메서드 없이 데이터 전달용 record
        }

        /**
         * 특정 사용자에게 단일 권한을 추가할 때 사용하는 요청 DTO
         * 예) 기존 권한에 새로운 RoleType 1개 추가
         *
         * 사용처: AdminController에서 권한 추가 API의 요청 바디로 사용
         */
        public record AddRoleRequest(
                        // 권한을 추가할 대상 사용자의 username (DB PK)
                        @NotNull(message = "username는 필수 입니다.") @Positive(message = "username는 양수여야 합니다.") Long username,

                        // 추가할 권한 (RoleType Enum)
                        @NotNull(message = "role은 필수 입니다.") RoleType role) {
                // 별도 메서드 없이 데이터 전달용 record
        }

        /**
         * 특정 사용자에게서 단일 권한을 제거할 때 사용하는 요청 DTO
         * 예) 기존 권한 중 특정 RoleType 1개 제거
         *
         * 사용처: AdminController에서 권한 제거 API의 요청 바디로 사용
         */
        public record RemoveRoleRequest(
                        // 권한을 제거할 대상 사용자의 username (DB PK)
                        @NotNull(message = "username는 필수 입니다.") @Positive(message = "username는 양수여야 합니다.") Long username,

                        // 제거할 권한 (RoleType Enum)
                        @NotNull(message = "role은 필수 입니다.") RoleType role) {
                // 별도 메서드 없이 데이터 전달용 record
        }
}
