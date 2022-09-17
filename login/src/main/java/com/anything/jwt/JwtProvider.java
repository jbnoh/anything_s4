package com.anything.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.anything.jwt.dto.TokenValueDTO;
import com.anything.jwt.service.CustomUserDetailService;
import com.anything.type.DefaultType.JwtType;
import com.anything.vo.DataMap;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtProvider {

	@Value("${jwt.secretKey}")
	private String secretKey;

	@Value("${jwt.accessExpire}")
	private long accessExpire;

	@Value("${jwt.refreshExpire}")
	private long refreshExpire;

	private final Map<String, Object> jwtHeader = new HashMap<>();
	{
		jwtHeader.put("typ", "JWT");
		jwtHeader.put("alg", "HS256");
	}

	private final CustomUserDetailService customUserDetailService;

	public String createAccessToken(TokenValueDTO tokenValDto) {

		Map<String, Object> payloads = new HashMap<>();
		payloads.put("userId", tokenValDto.getUserId());

		Date expiration = new Date();
		expiration.setTime(expiration.getTime() + (accessExpire * 60 * 1000L));

		String jwt = Jwts.builder()
				.setHeader(jwtHeader)
				.setClaims(payloads)
				.setExpiration(expiration)
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();

		return jwt;
	}

	public DataMap createRefreshToken(TokenValueDTO tokenValDto) {

		Map<String, Object> payloads = new HashMap<>();
		payloads.put("userId", tokenValDto.getUserId());

		Date expiration = new Date();
		expiration.setTime(expiration.getTime() + (refreshExpire * 60 * 1000L));

		String jwt = Jwts.builder()
				.setHeader(jwtHeader)
				.setClaims(payloads)
				.setExpiration(expiration)
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();

		DataMap result = new DataMap();
		result.put("refreshToken", jwt);
		result.put("refreshTokenExpire", expiration.getTime());
		return result;
	}

	public Authentication getAuthentication(String token) throws Exception {

		String userId = this.getUserId(token);

		UserDetails userDetails = customUserDetailService.loadUserByUsername(userId);
		if (userDetails == null) {
			throw new Exception(String.format("User does not exist [USER: %s]", userId));
		}

		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

	public Claims getBody(String token) {

		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}

	public String getUserId(String token) {

		return this.getBody(token).get("userId").toString();
	}

	public String resolveToken(HttpServletRequest request) {

		return request.getHeader(JwtType.TOKEN.getValue());
	}

	public boolean validateJwtToken(String token) {

		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
		} catch (Exception e) {
			throw e;
		}

		return true;
	}
}
