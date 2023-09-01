package com.jdh.hoxy_api.config.security.filter;

import com.jdh.hoxy_api.config.security.provider.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT를 검증하는 filter
 * OncePerRequestFilter를 상속해 요청당 한번의 filter를 수행
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// jwt local storage 사용 시
		final String token = request.getHeader("Authorization");

		String userId = null;
		String jwtToken;

		// Bearer token인 경우
		if (token != null && token.startsWith("Bearer ")) {
			jwtToken = token.substring(7);

			// jwt token
			if(jwtTokenProvider.validateToken(jwtToken))
				userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
		}

		// token 검증이 되고 인증 정보가 존재하지 않는 경우 spring security 인증 정보 저장
		if(userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UsernamePasswordAuthenticationToken authenticationToken =
					new UsernamePasswordAuthenticationToken(userId, null , null);

			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}

		filterChain.doFilter(request,response);
	}
}
