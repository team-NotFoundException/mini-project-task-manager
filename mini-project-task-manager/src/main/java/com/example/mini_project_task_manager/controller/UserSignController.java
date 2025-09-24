package com.example.mini_project_task_manager.controller;

import com.example.mini_project_task_manager.dto.Auth.request.SignRequest;
import com.example.mini_project_task_manager.dto.Auth.response.SignInResponse;
import com.example.mini_project_task_manager.dto.Mail.MailRequest;
import com.example.mini_project_task_manager.dto.ResponseDto;
import com.example.mini_project_task_manager.service.MailService;
import com.example.mini_project_task_manager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserSignController {
    private final UserService userService;
    private final MailService mailService;

    /** 회원가입 */
    @PostMapping("/sign-up")
    public ResponseEntity<ResponseDto<Void>> sigUp(@Valid @RequestBody SignRequest.SingUpRequest req){
        userService.signUp(req);
        return ResponseEntity.ok(ResponseDto.setSuccess("회원가입이 완료되었습니다.",null));
    }
    /** 로그인 */
    @PostMapping("/sign-in")
    public ResponseEntity<ResponseDto<SignInResponse>> signIn(@Valid @RequestBody SignRequest.SignInRequest req){
        ResponseDto<SignInResponse> response = userService.signIn(req);
        return ResponseEntity.ok().body(response);
    }
    /** 이메일 전송 */
    @PostMapping("/send-email")
    public ResponseEntity<ResponseDto<Void>> sendEmail(@Valid @RequestBody MailRequest.SendMail req) {
        mailService.sendEmail(req);
        return ResponseEntity.noContent().build();
    }
    /** 이메일 인증 */
    @GetMapping("/verify")
    public ResponseEntity<ResponseDto<Void>> verifyEmail(@RequestParam String token) {
        mailService.verifyEmail(token);
        return ResponseEntity.noContent().build();
    }
    /** 비밀번호 재설정 */
    @PostMapping("/reset-password")
    public ResponseEntity<ResponseDto<Void>> resetPassword(@Valid @RequestBody MailRequest.PasswordReset req) {
        userService.resetPassword(req);
        return ResponseEntity.noContent().build();
    }

}
