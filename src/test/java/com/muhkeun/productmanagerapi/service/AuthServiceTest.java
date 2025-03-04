package com.muhkeun.productmanagerapi.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.muhkeun.productmanagerapi.model.dto.SignupRequest;
import com.muhkeun.productmanagerapi.model.entity.User;
import com.muhkeun.productmanagerapi.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("회원가입 성공: 중복 이메일이 없을 경우")
    void testSignupSuccess() {
        // given
        SignupRequest request = new SignupRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");
        request.setName("Test User");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> authService.signup(request));

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("회원가입 실패: 중복 이메일이 존재할 경우")
    void testSignupDuplicateEmail() {
        // given
        SignupRequest request = new SignupRequest();
        request.setEmail("duplicate@example.com");
        request.setPassword("password");
        request.setName("Duplicate User");

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(new User("duplicate@example.com", "password", "Duplicate User")));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> authService.signup(request));
        assertEquals("이미 존재하는 사용자 이메일입니다.", exception.getMessage());
    }
}
