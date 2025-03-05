package com.muhkeun.productmanagerapi.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JwtUtil {


    public static String generateToken(String subject, long expiration, String encryptionKey, String issuer) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(encryptionKey.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .setIssuer(issuer)
                .compact();
    }
}
