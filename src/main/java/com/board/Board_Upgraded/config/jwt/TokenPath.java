package com.board.Board_Upgraded.config.jwt;

import com.board.Board_Upgraded.dto.token.ReissueRequestDto;
import com.board.Board_Upgraded.dto.token.TokenResponseDto;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class TokenPath {

    public ReissueRequestDto getReissueResponseDtoFromHeader(HttpServletRequest request){
        return ReissueRequestDto.builder()
                .accessToken(resolveAccessToken(request))
                .refreshToken(resolveRefreshToken(request))
                .build();
    }

    public void putTokensOnHeader(HttpServletResponse response, TokenResponseDto tokenResponseDto){
        setHeaderAccessToken(response, tokenResponseDto.getAccessToken());
        setHeaderRefreshToken(response, tokenResponseDto.getRefreshToken());
    }

    private void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader("Authorization", "Bearer "+ accessToken);
    }

    // 리프레시 토큰 헤더 설정
    private void setHeaderRefreshToken(HttpServletResponse response, String refreshToken) {
        response.setHeader("RefreshToken", "Bearer "+ refreshToken);
    }

    private String resolveAccessToken(HttpServletRequest request) {
        if(request.getHeader("Authorization") != null )
            return request.getHeader("Authorization").substring(7);
        return null;
    }

    private String resolveRefreshToken(HttpServletRequest request) {
        if(request.getHeader("RefreshToken") != null )
            return request.getHeader("RefreshToken").substring(7);
        return null;
    }
}
