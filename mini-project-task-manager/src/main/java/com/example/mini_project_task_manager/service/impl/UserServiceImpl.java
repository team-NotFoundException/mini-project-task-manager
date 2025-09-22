package com.example.mini_project_task_manager.service.impl;

import com.example.mini_project_task_manager.common.enums.RoleType;
import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.dto.Auth.request.SignRequest;
import com.example.mini_project_task_manager.dto.user.request.UserProfileUpdateRequest;
import com.example.mini_project_task_manager.dto.Auth.response.SignInResponse;
import com.example.mini_project_task_manager.dto.user.response.UserProfileResponse;
import com.example.mini_project_task_manager.entity.Role;
import com.example.mini_project_task_manager.entity.User;
import com.example.mini_project_task_manager.provider.JwtProvider;
import com.example.mini_project_task_manager.repository.RoleRepository;
import com.example.mini_project_task_manager.repository.UserRepository;
import com.example.mini_project_task_manager.security.UserPrincipal;
import com.example.mini_project_task_manager.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

// C: 권한X -> O
// R: USER 권한
// U: USER 권한
// D: USER 권한

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    public void signUp(SignRequest.@Valid SingUpRequest req) {

        if (userRepository.existsByUsername(req.username())) {
            throw new IllegalArgumentException("이미 사용 중인 로그인 아이디 입니다.");
        }

        if (userRepository.existsByEmail(req.email())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일 입니다.");
        }

        if (userRepository.existsByNickname(req.nickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임 입니다.");
        }
        String encoded = passwordEncoder.encode(req.password());

        User user = User.builder()
                .username(req.username())
                .password(encoded)
                .email(req.email())
                .nickname(req.nickname())
                .build();

        Role defaultRole = roleRepository.getReferenceById(RoleType.USER);
        user.grantRole(defaultRole);
        userRepository.save(user);
    }

    @Override
    public ResponseDto<SignInResponse> signIn(SignRequest.@Valid SignInRequest req) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username(), req.password())
        );
        Set<String> roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        String accessToken = jwtProvider.generateJwtToken(req.username(), roles);

        Claims claims = jwtProvider.getClaims(accessToken);
        long expiresAt = claims.getExpiration().getTime();

        SignInResponse response = new SignInResponse(
                "Bearer",
                accessToken,
                expiresAt,
                req.username(),
                roles
        );

        return ResponseDto.setSuccess("로그인 성공", response);
    }

    @Override
    public ResponseDto<UserProfileResponse.MyPageResponse> getMyInfo(UserPrincipal principal) {
        return null;
    }

    @Override
    public ResponseDto<UserProfileResponse.MyPageResponse> updateMyInfo(UserPrincipal principal, UserProfileUpdateRequest request) {
        return null;
    }
}
