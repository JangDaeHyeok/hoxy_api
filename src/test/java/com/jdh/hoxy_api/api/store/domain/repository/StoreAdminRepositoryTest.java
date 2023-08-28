package com.jdh.hoxy_api.api.store.domain.repository;

import com.jdh.hoxy_api.api.common.entity.DelYnEntity;
import com.jdh.hoxy_api.api.common.enums.YorN;
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
    public void StoreAdminRepository_업체_관리자를_아이디_비밀번호_정보로_조회() {
        // given
        final Store store = getTestStore();
        storeRepository.save(store);
        final StoreAdmin storeAdmin = getTestStoreAdmin();
        storeAdmin.addStore(store);

        // when
        storeAdminRepository.save(storeAdmin);
        final StoreAdmin result = storeAdminRepository.findByIdAndPasswordAndDelYn("test"
                , "1234"
                , DelYnEntity.builder().delYn(YorN.N).build()
        );

        // then
        assertThat(result.getId()).isEqualTo("test");
        assertThat(result.getPassword()).isEqualTo("1234");
        assertThat(result.getPwSalt()).isEqualTo("salt");
        assertThat(result.getName()).isEqualTo("테스트 관리자");
        assertThat(result.getStore().getName()).isEqualTo("테스트");
    }

    @Test
    public void StoreAdminRepository_업체_관리자를_아이디_비밀번호로_조회_시_삭제된_계정정보는_미조회하는지_테스트() {
        // given
        final Store store = getTestStore();
        storeRepository.save(store);
        final StoreAdmin storeAdmin = getTestStoreAdmin();
        storeAdmin.addStore(store);
        storeAdmin.chgDelYn(YorN.Y);

        // when
        storeAdminRepository.save(storeAdmin);
        final StoreAdmin result = storeAdminRepository.findByIdAndPasswordAndDelYn("test"
                , "1234"
                , DelYnEntity.builder().delYn(YorN.N).build()
        );

        // then
        assertThat(result).isEqualTo(null);
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

    private StoreAdmin getTestStoreAdmin2() {
        return StoreAdmin.builder()
                .id("test")
                .password("1234")
                .pwSalt("salt")
                .name("임시 관리자")
                .build();
    }

}
