package com.jdh.hoxy_api.config.security.handler;

import com.google.gson.JsonObject;
import com.jdh.hoxy_api.config.exception.common.enums.ApiExceptionEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Spring Security JWT 인증 정보가 존재하지 않는 경우 401 에러 반환
 */
@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint  {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
						 AuthenticationException authException) throws IOException {
		log.info("[JwtAuthenticationEntryPoint] 토큰 정보가 만료되었거나 존재하지 않습니다.");
		response.setStatus(ApiExceptionEnum.ACCESS_DENIED.getStatus().value());
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");

		JsonObject returnJson = new JsonObject();
		returnJson.addProperty("errorCode", ApiExceptionEnum.ACCESS_DENIED.getCode());
		returnJson.addProperty("errorMsg", ApiExceptionEnum.ACCESS_DENIED.getMessage());

		PrintWriter out = response.getWriter();
		out.print(returnJson);
	}

}
