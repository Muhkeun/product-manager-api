package com.muhkeun.productmanagerapi.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseEntity {

    @Column(name = "createdAt", nullable = false, updatable = false, length = 0, columnDefinition = "DATETIME")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updatedAt", length = 0, columnDefinition = "DATETIME")
    @LastModifiedDate
    private LocalDateTime updatedAt;

}
