package com.board.Board_Upgraded.config.jwt;

import com.board.Board_Upgraded.dto.token.TokenDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24;            // 1시간 * 24 = 24시간(-Dev)
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일
    private final Key key;

    public TokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto generateTokenDto(Authentication authentication) {
        Date accessTokenExpiresIn = new Date((new Date()).getTime() + ACCESS_TOKEN_EXPIRE_TIME);
        return TokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(generateAccessToken(authentication, accessTokenExpiresIn))
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshToken(generateRefreshToken())
                .build();
    }

    // Refresh Token 생성
    private String generateRefreshToken(){
        return Jwts.builder()
                .setExpiration(new Date((new Date()).getTime() + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // Access Token 생성
    private String generateAccessToken(Authentication authentication, Date accessTokenExpiresIn){
        return Jwts.builder()
                .setSubject(authentication.getName())       // payload "sub": "name"
                .claim(AUTHORITIES_KEY, getAuthorities(authentication))        // payload "auth": "ROLE_USER"
                .setExpiration(accessTokenExpiresIn)        // payload "exp": 1516239022 (예시)
                .signWith(key, SignatureAlgorithm.HS512)    // header "alg": "HS512"
                .compact();
    }

    // 권한들 가져오기
    private String getAuthorities(Authentication authentication){
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }
}
