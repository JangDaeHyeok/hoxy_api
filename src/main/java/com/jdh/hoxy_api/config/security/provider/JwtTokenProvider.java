	package com.jdh.hoxy_api.config.security.provider;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SignatureException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

	/**
 * JWT 생성, 조회, 유효성 체크
 * @Author 장대혁
 */
@Component
@Slf4j
public class JwtTokenProvider {

	@Value("${jwt.signing-key}")
	private String secret;

	/**
	 * token 유효시간 (1시간)
	 */
	public static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 60;

	/**
	 * 사용자 id 조회
	 * @param token
	 * @return 사용자 id
	 */
	public String getUserIdFromToken(String token) {
		return getClaimFromToken(token, Claims::getId);
	}

	/**
	 * 사용자 속성 정보 조회
	 * @param token
	 * @param claimsResolver
	 * @return 사용자 속성 정보
	 */
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * token에 대한 사용자 속성 정보 조회
	 * @param token
	 * @return 사용자 속성 정보
	 */
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	/**
	 * token 만료 여부 체크
	 * @param token
	 * @return 토큰 만료 여부
	 */
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	/**
	 * token 만료일자 조회
	 * @param token
	 * @return 토큰 만료일자
	 */
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	/**
	 * id를 입력받아 accessToken 생성
	 * @param id
	 * @return accessToken
	 */
	public String generateAccessToken(String id) {
		return generateAccessToken(id, new HashMap<>());
	}

	/**
	 * id, 속성정보를 이용해 accessToken 생성
	 * @param id
	 * @param claims
	 * @return accessToken
	 */
	public String generateAccessToken(String id, Map<String, Object> claims) {
		return doGenerateAccessToken(id, claims);
	}

	/**
	 * JWT accessToken 생성
	 * @param id
	 * @param claims
	 * @return accessToken 생성
	 */
	private String doGenerateAccessToken(String id, Map<String, Object> claims) {
		String accessToken = Jwts.builder()
				.setClaims(claims)
				.setId(id)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1)) // 1시간
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
		
		return accessToken;
	}

	/**
	 * token 검증
	 * @param token
	 * @return token 검증 결과
	 * @exception SignatureException token 시그니처 검증 실패
	 * @exception MalformedJwtException 손상된 토큰
	 * @exception ExpiredJwtException 만료된 토큰
	 * @exception UnsupportedJwtException 지원하지 않는 토큰
	 * @exception IllegalArgumentException empty token
	 */
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty: {}", e.getMessage());
		}
		
		return false;
	}
}
