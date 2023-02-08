package com.board.Board_Upgraded.dto.token;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenResponseDto {
    private String accessToken;
    private String refreshToken;

    public TokenResponseDto(TokenDto tokenDto){
        this.accessToken = tokenDto.getAccessToken();
        this.refreshToken = tokenDto.getRefreshToken();
    }
}