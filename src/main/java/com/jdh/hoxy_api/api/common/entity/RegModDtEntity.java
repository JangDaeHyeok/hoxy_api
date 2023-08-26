package com.jdh.hoxy_api.api.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Embeddable
public class RegModDtEntity {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime regDt;

    @LastModifiedDate
    private LocalDateTime modDt;

}
