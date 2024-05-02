package com.ssafy.user.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTime {

    // Entity가 생성되어 저장될 때 시간이 자동 저장됨
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // 조회한 Entity 값을 변경할 때 시간이 자동 저장됨
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}