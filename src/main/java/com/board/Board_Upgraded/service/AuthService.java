package com.board.Board_Upgraded.service;

import com.board.Board_Upgraded.config.jwt.TokenProvider;
import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.dto.member.SignInRequestDto;
import com.board.Board_Upgraded.dto.token.*;
import com.board.Board_Upgraded.entity.member.*;
import com.board.Board_Upgraded.exception.token.*;
import com.board.Board_Upgraded.repository.RefreshTokenRepository;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private MemberInstanceValidator memberInstanceValidator;

    @PostConstruct
    private void setValidator(){
        memberInstanceValidator = new MemberInstanceValidator(memberRepository, passwordEncoder);
    }

    @Transactional
    public void registerNewMember(RegisterRequestDto registerRequestDto){
        memberInstanceValidator.validateRegisterRequest(registerRequestDto);
        registerRequestDto.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        Member member = new Member(registerRequestDto);
        memberRepository.save(member);
    }

    @Transactional
    public TokenResponseDto signIn(SignInRequestDto signInRequestDto){
        Authentication authentication = getAuthenticationToSignIn(signInRequestDto);
        return createTokenDtoByAuthentication(authentication);
    }

    // SignIn을 위한 로직1
    private Authentication getAuthenticationToSignIn(SignInRequestDto signInRequestDto){
        memberInstanceValidator.validateSignInRequest(signInRequestDto);
        UsernamePasswordAuthenticationToken authenticationToken = signInRequestDto.toAuthentication();
        return authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    }

    //SignIn을 위한 로직2
    private TokenResponseDto createTokenDtoByAuthentication(Authentication authentication){
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);
        return new TokenResponseDto(tokenDto);
    }

    @Transactional
    public TokenResponseDto reissue(ReissueRequestDto req) {
        validateRefreshToken(req);
        RefreshToken refreshToken = refreshTokenRepository.findByKey(getAuthentication(req).getName())
                .orElseThrow(LogOutMemberException::new);
        validateTokenInfo(refreshToken, req);
        TokenDto tokenDto = tokenProvider.generateTokenDto(getAuthentication(req));
        refreshToken.updateValue(tokenDto.getRefreshToken());
        return new TokenResponseDto(tokenDto);
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
}
