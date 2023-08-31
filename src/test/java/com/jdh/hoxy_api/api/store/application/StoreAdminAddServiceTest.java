package com.jdh.hoxy_api.api.store.application;

import com.jdh.hoxy_api.api.store.application.impl.StoreAdminAddServiceImpl;
import com.jdh.hoxy_api.api.store.domain.entity.Store;
import com.jdh.hoxy_api.api.store.domain.entity.StoreAdmin;
import com.jdh.hoxy_api.api.store.domain.repository.StoreRepository;
import com.jdh.hoxy_api.api.store.exception.StoreAdminException;
import com.jdh.hoxy_api.api.store.exception.enums.StoreAdminErrorResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StoreAdminAddServiceTest {

    @InjectMocks
    StoreAdminAddServiceImpl target;

    @Mock
    StoreRepository storeRepository;

    @Mock
    PasswordEncoder bCryptPasswordEncoder;

    @Test
    public void StoreAdminAddService_Not_Null() {
        assertThat(target);
    }

    @Test
    @DisplayName("존재하는 업체가 아닌 경우 계정 등록 실패 테스트")
    public void StoreAdminAddService_회원가입_실패_존재하지_않는_업체() {
        // given
        when(storeRepository.findById(1L)).thenReturn(Optional.empty());

        // when
        final StoreAdminException result = assertThrows(StoreAdminException.class, () -> target.addStoreAdmin(1, "test", "1234", "닉네임"));

        // then
        assertThat(result.getStoreAdminErrorResult()).isEqualTo(StoreAdminErrorResult.STORE_NOT_EXIST);
    }

    @Test
    @DisplayName("업체 관리자 계정 등록 시 이미 존재하는 계정이 있는 경우 계정 등록 실패 테스트")
    public void StoreAdminAddService_회원가입_실패_이미_존재하는_계정() {
        // given
        when(storeRepository.findById(1L)).thenReturn(Optional.of(getTestStore()));
        when(storeRepository.countByStoreAdminId("test")).thenReturn(1);

        // when
        final StoreAdminException result = assertThrows(StoreAdminException.class, () -> target.addStoreAdmin(1, "test", "1234", "닉네임"));

        // then
        assertThat(result.getStoreAdminErrorResult()).isEqualTo(StoreAdminErrorResult.DUPLICATE_STORE_ADMIN_ID);
    }

    @Test
    @DisplayName("업체 관리자 계정을 이미 생성한 경우 계정 등록 실패 테스트")
    public void StoreAdminAddService_회원가입_실패_이미_관리자_계정을_생성한_업체() {
        // given
        final Store store = getTestStore();
        final StoreAdmin storeAdmin = getTestStoreAdmin();
        store.addStoreAdmin(storeAdmin);
        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));

        // when
        final StoreAdminException result = assertThrows(StoreAdminException.class, () -> target.addStoreAdmin(1, "test", "1234", "닉네임"));

        // then
        assertThat(result.getStoreAdminErrorResult()).isEqualTo(StoreAdminErrorResult.DUPLICATE_STORE);
    }

    @Test
    @DisplayName("업체 관리자 계정 등록 성공")
    public void StoreAdminAddService_회원가입_성공() {
        // given
        final Store store = getTestStore();
        final StoreAdmin storeAdmin = getTestStoreAdmin();
        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));
        when(storeRepository.countByStoreAdminId("test")).thenReturn(0);

        // when
        target.addStoreAdmin(1, "test", "1234", "닉네임");

        // then

        // verify
        verify(storeRepository, times(1)).findById(1L);
        verify(storeRepository, times(1)).countByStoreAdminId("test");
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
