package com.example.mini_project_task_manager.service.impl;

import com.example.mini_project_task_manager.common.enums.RoleType;
import com.example.mini_project_task_manager.dto.Auth.request.FindUsernameRequest;
import com.example.mini_project_task_manager.dto.Auth.response.FindUsernameResponse;
import com.example.mini_project_task_manager.dto.Mail.MailRequest;
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
import com.example.mini_project_task_manager.security.util.PrincipalUtils;
import com.example.mini_project_task_manager.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
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
                .gender(req.gender())
                .build();

        User save = userRepository.save(user);

        Role defaultRole = roleRepository.findById(RoleType.USER)
                .orElseThrow(() -> new IllegalStateException("ROLE USER is not present in roles table"));

        save.grantRole(defaultRole);
        userRepository.save(user);
    }


    @Override
    @Transactional
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
    @Transactional
    public ResponseDto<UserProfileResponse.MyPageResponse> getMyInfo(UserPrincipal principal) {
        PrincipalUtils.requiredActive(principal);

        User user = userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() ->
                        new EntityNotFoundException
                                ("해당 username의 사용자가 없습니다: " + principal.getUsername()));

        UserProfileResponse.MyPageResponse data = new UserProfileResponse.MyPageResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getNickname(),
                user.getGender()

        );

        return ResponseDto.setSuccess("SUCCESS", data);
    }

    @Override
    @Transactional
    public ResponseDto<UserProfileResponse.MyPageResponse> updateMyInfo
            (UserPrincipal principal, UserProfileUpdateRequest request
            ) {
        PrincipalUtils.requiredActive(principal);

        User user = userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() ->
                        new EntityNotFoundException
                                ("해당 username의 사용자가 없습니다: " + principal.getUsername()));
        user.changeProfile(request.nickname(), request.email(), request.gender());
        userRepository.flush();

        UserProfileResponse.MyPageResponse data = new UserProfileResponse.MyPageResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getNickname(),
                user.getGender()
        );
        return ResponseDto.setSuccess("SUCCESS", data);
    }

    @Override
    @Transactional
    public void resetPassword(MailRequest.@Valid PasswordReset req) {
        if (!req.newPassword().equals(req.confirmPassword()))
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");

        User user = userRepository.findByEmail(req.email())
                .orElseThrow(() -> new IllegalStateException("가입된 이메일이 아닙니다."));
        String encoded = passwordEncoder.encode(req.newPassword());

        user.changePassword(encoded);

        userRepository.save(user);
    }

    @Override
    public ResponseDto<FindUsernameResponse> findUsername(FindUsernameRequest req) {
        FindUsernameResponse response = userRepository.findIdByNicknameAndEmail(req.nickname(), req.email())
                .orElseThrow(() -> new IllegalArgumentException("해당 닉네임/이메일로 사용자를 찾을 수 없습니다."));

        return ResponseDto.setSuccess("SUCCESS", response);
    }

}
