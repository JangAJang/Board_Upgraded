package com.board.Board_Upgraded.service;

import com.board.Board_Upgraded.config.jwt.TokenProvider;
import com.board.Board_Upgraded.dto.token.ReissueRequestDto;
import com.board.Board_Upgraded.dto.token.TokenDto;
import com.board.Board_Upgraded.dto.token.TokenResponseDto;
import com.board.Board_Upgraded.entity.member.RefreshToken;
import com.board.Board_Upgraded.exception.token.LogOutMemberException;
import com.board.Board_Upgraded.exception.token.TokenUnmatchWithMemberException;
import com.board.Board_Upgraded.exception.token.UnvalidRefreshTokenException;
import com.board.Board_Upgraded.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final TokenProvider tokenProvider;



    @Transactional
    public TokenResponseDto reissue(ReissueRequestDto req) {
        validateRefreshToken(req);
        RefreshToken refreshToken = refreshTokenRepository.findByKey(getAuthentication(req).getName())
                .orElseThrow(LogOutMemberException::new);
        validateTokenInfo(refreshToken, req);
        TokenDto tokenDto = tokenProvider.generateTokenDto(getAuthentication(req));
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);
        return new TokenResponseDto(tokenDto.getAccessToken(), tokenDto.getRefreshToken());
    }

    private void validateRefreshToken(ReissueRequestDto req){
        if (!tokenProvider.validateToken(req.getRefreshToken())) {
            throw new UnvalidRefreshTokenException();
        }
    }

    private void validateTokenInfo(RefreshToken refreshToken, ReissueRequestDto req){
        if (!refreshToken.getValue().equals(req.getRefreshToken())) {
            throw new TokenUnmatchWithMemberException();
        }
    }

    private Authentication getAuthentication(ReissueRequestDto req){
        return tokenProvider.getAuthentication(req.getAccessToken());
    }

    //SignIn을 위한 로직2
    @Transactional
    public TokenResponseDto createTokenDtoByAuthentication(Authentication authentication){
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);
        return new TokenResponseDto(tokenDto.getAccessToken(), tokenDto.getRefreshToken());
    }
}
