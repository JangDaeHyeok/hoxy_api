package com.jdh.hoxy_api.config.security;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@TestConfiguration
@EnableWebSecurity
public class TestSecurityConfig {

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public SecurityFilterChain config(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        MvcRequestMatcher[] PERMIT_ALL_WHITE_LIST = {
                mvc.pattern("/store"),
                mvc.pattern("/manage/store/admin"),
                mvc.pattern("/manage/login")
        };

        http.csrf(AbstractHttpConfigurer::disable);

        http.headers((headers) ->
                headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        http.authorizeHttpRequests(authorize -> authorize.requestMatchers(PERMIT_ALL_WHITE_LIST).permitAll());

        return http.build();
    }

}
