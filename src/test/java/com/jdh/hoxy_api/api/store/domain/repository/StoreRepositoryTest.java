package com.jdh.hoxy_api.api.store.domain.repository;

import com.jdh.hoxy_api.api.common.entity.DelYnEntity;
import com.jdh.hoxy_api.api.common.enums.YorN;
import com.jdh.hoxy_api.api.store.domain.entity.Store;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StoreRepositoryTest {

    @Autowired
    StoreRepository storeRepository;

    @Test
    public void StoreRepository_Not_Null() {
        assertThat(storeRepository);
    }

    @Test
    public void StoreRepository_업체_조회() {
        // given
        final Store store = Store.builder()
                .name("테스트")
                .build();

        // when
        storeRepository.save(store);
        final Store find = storeRepository.findAll().get(0);

        // then
        assertThat(find.getName()).isEqualTo("테스트");
    }

}
