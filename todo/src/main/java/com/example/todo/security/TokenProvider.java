package com.example.todo.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.stereotype.Service;

import com.example.todo.model.UserEntity;

import lombok.extern.slf4j.Slf4j;

/***
 * 사용자 정보를 받아 JWT를 생성하는 클래스
 * @author chaeng
 *
 */
@Slf4j
@Service
public class TokenProvider {

	private static final String SECRET_KEY = "NMA8JpctFuna59f5";
	
	public String create(UserEntity userEntity) {
		// 기한은 지금부터 1일
		Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
		
		// JWT Token 생성
		return Jwts.builder()
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				.setSubject(userEntity.getId())	// sub
				.setIssuer("demo app")	// iss
				.setIssuedAt(new Date())	// iat
				.setExpiration(expiryDate)	// exp
				.compact();
	}
	
	public String validateAndGetUserId(String token) {
		// parseClaimsJws 메소드가 Base64로 디코딩 및 파싱
		// 헤더와 페이로드를 setSigningKey로 넘어온 시크릿을 이용해 서명한 후 otken의 서명과 비교
		// 위조되지 않았다면 페이로드(Claims) 리턴, 위조라면 예외를 날림
		// 그중 우리는 userId가 필요하므로 getBody를 부른다.
		Claims claims = Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody();
		
		return claims.getSubject();
	}
}
