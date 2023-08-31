package com.jdh.hoxy_api.api.store.domain.entity;

import com.jdh.hoxy_api.api.common.entity.RegModDtEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreAdmin extends RegModDtEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idx;

    private String id;

    private String password;

    private String name;

    @OneToOne
    @JoinColumn(name = "store_idx")
    private Store store;

    /**
     * 패스워드 암호화
     * @param passwordEncoder 암호화에 사용할 인코더 클래스
     */
    public void encryptPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    /**
     * 패스워드 일치 여부 확인
     * @param plainPassword 입력받은 암호화 이전의 비밀번호
     * @param passwordEncoder 암호화에 사용할 인코더 클래스
     * @return 패스워드 일치 여부
     */
    public boolean checkPassword(String plainPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(plainPassword, this.password);
    }

    @Builder
    protected StoreAdmin(String id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }

}
