package com.jdh.hoxy_api.api.login.application;

import com.jdh.hoxy_api.api.login.application.impl.LoginServiceImpl;
import com.jdh.hoxy_api.api.login.dto.response.LoginResponseDTO;
import com.jdh.hoxy_api.api.login.exception.LoginException;
import com.jdh.hoxy_api.api.login.exception.enums.LoginErrorResult;
import com.jdh.hoxy_api.api.store.domain.entity.Store;
import com.jdh.hoxy_api.api.store.domain.entity.StoreAdmin;
import com.jdh.hoxy_api.api.store.domain.repository.StoreRepository;
import com.jdh.hoxy_api.config.security.provider.JwtTokenProvider;
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
public class LoginServiceTest {

    @InjectMocks
    LoginServiceImpl target;

    @Mock
    StoreRepository storeRepository;

    @Mock
    PasswordEncoder bCryptPasswordEncoder;

    @Mock
    JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("LoginService 존재 test")
    public void LoginService_Not_Null() {
        assertThat(target);
    }

    @Test
    @DisplayName("아이디가 존재하지 않는 경우 로그인 실패 test")
    public void LoginService_실패_아이디_미존재() {
        // given
        when(storeRepository.findByStoreAdminId("test123")).thenReturn(Optional.empty());

        // when
        final LoginException result = assertThrows(LoginException.class, () -> target.login("test123", "1234"));

        // then
        assertThat(result.getLoginErrorResult()).isEqualTo(LoginErrorResult.ID_PASSWORD_NOT_CORRECT);
    }

    @Test
    @DisplayName("아이디와 비밀번호가 일치하지 않는 경우 로그인 실패 test")
    public void LoginService_실패_아이디_비밀번호_불일치() {
        // given
        final Store store = getTestStore();
        final StoreAdmin storeAdmin = getTestStoreAdmin();
        store.addStoreAdmin(storeAdmin);
        when(storeRepository.findByStoreAdminId("test")).thenReturn(Optional.of(store));
        when(storeAdmin.checkPassword("5678", bCryptPasswordEncoder)).thenReturn(false);

        // when
        final LoginException result = assertThrows(LoginException.class, () -> target.login("test", "5678"));

        // then
        assertThat(result.getLoginErrorResult()).isEqualTo(LoginErrorResult.ID_PASSWORD_NOT_CORRECT);
    }

    @Test
    @DisplayName("로그인 성공 test")
    public void LoginService_성공() throws Exception {
        // given
        final Store store = getTestStore();
        final StoreAdmin storeAdmin = getTestStoreAdmin();
        store.addStoreAdmin(storeAdmin);
        when(storeRepository.findByStoreAdminId("test")).thenReturn(Optional.of(store));
        when(storeAdmin.checkPassword("1234", bCryptPasswordEncoder)).thenReturn(true);
        final String testToken = "testToken1234";
        when(jwtTokenProvider.generateAccessToken(String.valueOf(store.getIdx()))).thenReturn(testToken);

        // when
        final LoginResponseDTO result = target.login("test", "1234");

        // then
        assertThat(result.getToken()).isNotNull();
        assertThat(result.getToken()).isEqualTo(testToken);

        // verify
        verify(storeRepository, times(1)).findByStoreAdminId("test");
        verify(jwtTokenProvider, times(1)).generateAccessToken(String.valueOf(store.getIdx()));
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
