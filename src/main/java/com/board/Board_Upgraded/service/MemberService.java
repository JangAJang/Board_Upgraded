package com.board.Board_Upgraded.service;

import com.board.Board_Upgraded.dto.member.*;
import com.board.Board_Upgraded.dto.token.TokenDto;
import com.board.Board_Upgraded.dto.token.TokenResponseDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.exception.member.*;
import com.board.Board_Upgraded.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public void registerNewMember(RegisterRequestDto registerRequestDto){
        validateUsername(registerRequestDto.getUsername());
        validateNickname(registerRequestDto.getNickname());
        validateEmail(registerRequestDto.getEmail());
        registerRequestDto.setPassword(bCryptPasswordEncoder.encode(registerRequestDto.getPassword()));
        registerRequestDto.setPasswordCheck(bCryptPasswordEncoder.encode(registerRequestDto.getPasswordCheck()));
        Member member = new Member(registerRequestDto);
        memberRepository.save(member);
    }

    private void validateUsername(String username){
        if(memberRepository.findByUsername(username).isPresent())
            throw new UsernameAlreadyInUseException();
    }

    private void validateNickname(String nickname){
        if(memberRepository.findByNickname(nickname).isPresent())
            throw new NicknameAlreadyInUseException();
    }

    private void validateEmail(String email){
        if(memberRepository.findByEmail(email).isPresent())
            throw new EmailAlreadyInUseException();
    }

    @Transactional
    public void changeMemberEmail(ChangeEmailRequestDto changeEmailRequestDto, Member member){
        validateEmail(changeEmailRequestDto.getNewEmail());
        member.changeEmail(changeEmailRequestDto);
        memberRepository.save(member);
    }

    @Transactional
    public void changeMemberNickname(ChangeNicknameRequestDto changeNicknameRequestDto, Member member){
        validateNickname(changeNicknameRequestDto.getNewNickname());
        member.changeNickname(changeNicknameRequestDto);
        memberRepository.save(member);
    }

    @Transactional
    public void changeMemberPassword(ChangePasswordRequestDto changePasswordRequestDto, Member member){
        changePasswordRequestDto.setNewPassword(bCryptPasswordEncoder.encode(changePasswordRequestDto.getNewPassword()));
        changePasswordRequestDto.setNewPasswordCheck(bCryptPasswordEncoder.encode(changePasswordRequestDto.getNewPasswordCheck()));
        member.changePassword(changePasswordRequestDto);
        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public MemberInfoDto searchMember(SearchMemberDto searchMemberDto){
        Member member = memberRepository.findByNickname(searchMemberDto.getNickname()).orElseThrow(MemberNotFoundException::new);
        return new MemberInfoDto(member);
    }

    @Transactional
    public TokenResponseDto signIn(SignInRequestDto signInRequestDto, RefreshTokenService refreshTokenService){
        validateSignInRequest(signInRequestDto);
        TokenDto tokenDto = getTokenDto(signInRequestDto, refreshTokenService);
        return new TokenResponseDto(tokenDto.getAccessToken(), tokenDto.getRefreshToken());
    }

    private void validateSignInRequest(SignInRequestDto signInRequestDto){
        Member member = memberRepository.findByUsername(signInRequestDto.getUsername()).orElseThrow(MemberNotFoundException::new);
        if(!member.isPasswordRight(bCryptPasswordEncoder.encode(signInRequestDto.getPassword()))) throw new PasswordNotMatchingException();
    }

    private TokenDto getTokenDto(SignInRequestDto signInRequestDto, RefreshTokenService refreshTokenService){
        UsernamePasswordAuthenticationToken authenticationToken = signInRequestDto.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return refreshTokenService.createTokenDtoByAuthentication(authentication);
    }
}
