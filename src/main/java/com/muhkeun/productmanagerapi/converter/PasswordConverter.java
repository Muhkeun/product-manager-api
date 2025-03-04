package com.muhkeun.productmanagerapi.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Converter
@RequiredArgsConstructor
public class PasswordConverter implements AttributeConverter<String, String> {

    private final PasswordEncoder encoder;

    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            return encoder.encode(attribute);
        } catch (Exception e) {
            throw new RuntimeException("암호화 실패", e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            return dbData;
        } catch (Exception e) {
            throw new RuntimeException("복호화 실패", e);
        }
    }
}
