package com.muhkeun.productmanagerapi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotEmpty(message = "사용자 이메일은 필수 항목입니다.")
    @Email
    @Schema(description = "사용자 이메일", example = "muhkeun@naver.com")
    private String email;

    @NotEmpty(message = "사용자 비밀번호는 필수 항목입니다.")
    @Schema(description = "사용자 비밀번호", example = "123456")
    private String password;
}
