package com.muhkeun.productmanagerapi.service;

import com.muhkeun.productmanagerapi.model.dto.SignupRequest;
import com.muhkeun.productmanagerapi.model.entity.User;
import com.muhkeun.productmanagerapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public void signup(SignupRequest request) {

        if (isDuplicatedUserEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 사용자 이메일입니다.");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .name(request.getName())
                .build();

        userRepository.save(user);
    }

    private boolean isDuplicatedUserEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
