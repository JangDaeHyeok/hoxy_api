package com.jdh.hoxy_api.api.store.domain.repository;

import com.jdh.hoxy_api.api.store.domain.entity.Store;
import com.jdh.hoxy_api.api.store.domain.entity.StoreAdmin;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Order(1)
public class StoreRepositoryTest {

    @Autowired
    StoreRepository storeRepository;

    @BeforeEach
    public void addStore() {
        final Store store = getTestStore();
        final StoreAdmin storeAdmin = getTestStoreAdmin();
        store.addStoreAdmin(storeAdmin);

        storeRepository.save(store);
    }

    @AfterEach
    public void deleteStore() {
        storeRepository.deleteAll();
    }

    @Test
    public void StoreRepository_Not_Null() {
        assertThat(storeRepository);
    }

    @Test
    public void StoreRepository_업체_조회() {
        // when
        final Store find = storeRepository.findAll().get(0);

        // then
        assertThat(find.getName()).isEqualTo("테스트");
    }

    @Test
    public void StoreRepository_업체_관리자_id로_업체_조회() {
        // when
        final Store find = storeRepository.findByStoreAdminId("test").get();

        // then
        assertThat(find.getName()).isEqualTo("테스트");
        assertThat(find.getStoreAdmin().getId()).isEqualTo("test");
        assertThat(find.getStoreAdmin().getPassword()).isEqualTo("1234");
        assertThat(find.getStoreAdmin().getName()).isEqualTo("테스트 관리자");
    }

    @Test
    public void StoreRepository_업체_관리자_id_개수_조회() {
        // when
        final int find = storeRepository.countByStoreAdminId("test");

        // then
        assertThat(find).isEqualTo(1);
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
                .name("테스트 관리자")
                .build();
    }

}
