package com.jdh.hoxy_api.api.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum YorN {

    Y(1), N(0);

    private final int val;

}
