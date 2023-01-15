package com.board.Board_Upgraded.dto.token;

import com.board.Board_Upgraded.config.jwt.TokenProvider;
import com.board.Board_Upgraded.dto.member.SignInRequestDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

import static com.board.Board_Upgraded.dto.token.TokenGeneratingComponent.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Date accessTokenExpiresIn;

    public TokenDto(Authentication authentication, Key secretKey){
        this.accessTokenExpiresIn = new Date((new Date()).getTime() + Long.parseLong(ACCESS_TOKEN_EXPIRE_TIME.getComponent()));
        this.grantType = BEARER_TYPE.getComponent();
        this.accessToken = generateAccessToken(authentication, secretKey);
        this.refreshToken = generateRefreshToken(secretKey);
    }

    // 권한들 가져오기
    private String getAuthorities(Authentication authentication){
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    // Access Token 생성
    private String generateAccessToken(Authentication authentication, Key key){
        return Jwts.builder()
                .setSubject(authentication.getName())       // payload "sub": "name"
                .claim(AUTHORITIES_KEY.getComponent(), getAuthorities(authentication))        // payload "auth": "ROLE_USER"
                .setExpiration(accessTokenExpiresIn)        // payload "exp": 1516239022 (예시)
                .signWith(key, SignatureAlgorithm.HS512)    // header "alg": "HS512"
                .compact();
    }

    // Refresh Token 생성
    private String generateRefreshToken(Key key){
        return Jwts.builder()
                .setExpiration(new Date((new Date()).getTime() + Long.parseLong(REFRESH_TOKEN_EXPIRE_TIME.getComponent())))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
}
