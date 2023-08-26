package com.jdh.hoxy_api.api.store.application;

import com.jdh.hoxy_api.api.store.application.impl.StoreGetServiceImpl;
import com.jdh.hoxy_api.api.store.domain.entity.Store;
import com.jdh.hoxy_api.api.store.domain.repository.StoreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StoreGetServiceTest {

    @InjectMocks
    StoreGetServiceImpl target;

    @Mock
    StoreRepository storeRepository;

    @Test
    public void StoreGetService_Not_Null() {
        assertThat(target);
    }

    @Test
    public void Store_조회_성공() {
        // given
        final Store store = Store.builder()
                .name("테스트")
                .build();
        final List<Store> resultList = new ArrayList<>(){{
            add(store);
        }};
        when(storeRepository.findAll()).thenReturn(resultList);

        // when
        final List<Store> result = target.getStoreList();

        // then
        assertThat(result.get(0).getName()).isEqualTo("테스트");
    }

}
