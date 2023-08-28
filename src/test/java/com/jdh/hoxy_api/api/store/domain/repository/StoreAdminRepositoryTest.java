package com.jdh.hoxy_api.api.store.domain.repository;

import com.jdh.hoxy_api.api.store.domain.entity.Store;
import com.jdh.hoxy_api.api.store.domain.entity.StoreAdmin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StoreAdminRepositoryTest {

    @Autowired
    StoreAdminRepository storeAdminRepository;

    @Autowired
    StoreRepository storeRepository;

    @Test
    public void StoreAdminRepository_Not_Null() {
        assertThat(storeAdminRepository);
    }

    @Test
    public void StoreAdminRepository_업체_관리자_저장() {
        // given
        final Store store = getTestStore();
        final StoreAdmin storeAdmin = getTestStoreAdmin();
        storeAdmin.addStore(store);

        // when
        final StoreAdmin result = storeAdminRepository.save(storeAdmin);

        // then
        assertThat(result.getId()).isEqualTo("test");
        assertThat(result.getPassword()).isEqualTo("1234");
        assertThat(result.getPwSalt()).isEqualTo("salt");
        assertThat(result.getName()).isEqualTo("테스트 관리자");
        assertThat(result.getStore().getName()).isEqualTo("테스트");
    }

    @Test
    public void StoreAdminRepository_업체_관리자를_업체정보로_조회() {
        // given
        final Store store = getTestStore();
        storeRepository.save(store);
        final StoreAdmin storeAdmin = getTestStoreAdmin();
        storeAdmin.addStore(store);

        // when
        storeAdminRepository.save(storeAdmin);
        final StoreAdmin result = storeAdminRepository.findByStore(store);

        // then
        assertThat(result.getId()).isEqualTo("test");
        assertThat(result.getPassword()).isEqualTo("1234");
        assertThat(result.getPwSalt()).isEqualTo("salt");
        assertThat(result.getName()).isEqualTo("테스트 관리자");
        assertThat(result.getStore().getName()).isEqualTo("테스트");
    }

    @Test
    public void StoreAdminRepository_업체_관리자를_업체_idx로_조회() {
        // given
        final Store store = getTestStore();
        storeRepository.save(store);
        final StoreAdmin storeAdmin = getTestStoreAdmin();
        storeAdmin.addStore(store);

        // when
        storeAdminRepository.save(storeAdmin);
        final StoreAdmin result = storeAdminRepository.findByStoreIdx(1);

        // then
        assertThat(result.getId()).isEqualTo("test");
        assertThat(result.getPassword()).isEqualTo("1234");
        assertThat(result.getPwSalt()).isEqualTo("salt");
        assertThat(result.getName()).isEqualTo("테스트 관리자");
        assertThat(result.getStore().getName()).isEqualTo("테스트");
    }

    @Test
    public void StoreAdminRepository_id_개수_조회() {
        // given
        final Store store = getTestStore();
        storeRepository.save(store);
        final StoreAdmin storeAdmin = getTestStoreAdmin();
        storeAdmin.addStore(store);

        // when
        storeAdminRepository.save(storeAdmin);
        final int result = storeAdminRepository.countById("test");

        // then
        assertThat(result).isEqualTo(1);
    }

    private Store getTestStore() {
        return Store.builder()
                .name("테스트")
                .build();
    }

    private StoreAdmin getTestStoreAdmin() {
        return StoreAdmin.builder()
                .id("test")
                .password("1234")
                .pwSalt("salt")
                .name("테스트 관리자")
                .build();
    }

}
