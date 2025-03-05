package com.muhkeun.productmanagerapi.service;

import com.muhkeun.productmanagerapi.config.LoginConfig;
import com.muhkeun.productmanagerapi.exception.AuthenticationException;
import com.muhkeun.productmanagerapi.model.dto.LoginRequest;
import com.muhkeun.productmanagerapi.model.dto.SignupRequest;
import com.muhkeun.productmanagerapi.model.entity.User;
import com.muhkeun.productmanagerapi.repository.UserRepository;
import com.muhkeun.productmanagerapi.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final LoginConfig loginConfig;

    public void signup(SignupRequest request) {

        if (isDuplicatedUserEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 사용자 이메일입니다.");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .build();

        userRepository.save(user);
    }

    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AuthenticationException("잘못된 사용자 아이디 또는 비밀번호입니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthenticationException("잘못된 사용자 아이디 또는 비밀번호입니다.");
        }

        return JwtUtil.generateToken(user.getEmail(), loginConfig.getLoginExpiration(), loginConfig.getEncryptionKey(), loginConfig.getIssuer());
    }

    private boolean isDuplicatedUserEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
