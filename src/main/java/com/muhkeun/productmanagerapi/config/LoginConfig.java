package com.muhkeun.productmanagerapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class LoginConfig {

    @Value("${app.jwt.login-expiration}")
    private long loginExpiration;

    @Value("${app.jwt.encryption-key}")
    private String encryptionKey;

    @Value("${app.jwt.issuer}")
    private String issuer;

    public long getLoginExpiration() {
        return loginExpiration;
    }

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public String getIssuer() {
        return issuer;
    }
}
