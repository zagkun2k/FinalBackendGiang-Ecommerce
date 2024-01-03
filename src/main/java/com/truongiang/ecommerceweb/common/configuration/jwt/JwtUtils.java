package com.truongiang.ecommerceweb.common.configuration.jwt;

import com.truongiang.ecommerceweb.common.configuration.security.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

@Component
public class JwtUtils {

	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${jwtSecret}")
	private String jwtSecrect;

	@Value("${jwtExpirationMs}")
	private int jwtExpirationMs;

	public String generateJwtToken(Authentication authentication) {

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

		Date dateJwtDate = new Date();
		dateJwtDate.getTime();

		return Jwts.builder().setSubject((userPrincipal.getEmail())).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + this.jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, this.jwtSecrect).compact();

	}

	public String doGenerateToken(String email) {

		Claims claims = Jwts.claims().setSubject(email);
		claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));

		return Jwts.builder().setClaims(claims).setIssuer("http://devglan.com")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 60 * 1000))
				.signWith(SignatureAlgorithm.HS256, this.jwtSecrect).compact();

	}

	public String getEmailFromJwtToken(String token) {

		return Jwts.parser().setSigningKey(this.jwtSecrect).parseClaimsJws(token).getBody().getSubject();

	}

	public boolean validateJwtToken(String authToken) {

		try {

			Jwts.parser().setSigningKey(this.jwtSecrect).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {

			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {

			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {

			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {

			logger.error("JWT token is usnupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {

			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;

	}

}
