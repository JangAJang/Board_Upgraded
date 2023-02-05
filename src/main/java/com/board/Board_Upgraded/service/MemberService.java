package com.board.Board_Upgraded.service;

import com.board.Board_Upgraded.dto.member.*;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.exception.member.*;
import com.board.Board_Upgraded.repository.member.MemberRepository;
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
        validatePasswordCheck(registerRequestDto.getPassword(), registerRequestDto.getPasswordCheck());
        registerRequestDto.setPassword(bCryptPasswordEncoder.encode(registerRequestDto.getPassword()));
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

    private void validatePasswordCheck(String password, String passwordCheck){
        if(!password.equals(passwordCheck)){
            throw new PasswordNotMatchingException();
        }
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
        validatePasswordCheck(changePasswordRequestDto.getNewPassword(), changePasswordRequestDto.getNewPasswordCheck());
        member.changePassword(changePasswordRequestDto);
    }

    @Transactional(readOnly = true)
    public SearchMemberDto searchMember(SearchMemberDto searchMemberDto){
        Member member = memberRepository.findByNickname(searchMemberDto.getNickname()).orElseThrow(MemberNotFoundException::new);
        return new SearchMemberDto(member);
    }

    private void validateSignInRequest(SignInRequestDto signInRequestDto){
        Member member = memberRepository.findByUsername(signInRequestDto.getUsername()).orElseThrow(MemberNotFoundException::new);
        if(!member.isPasswordRight(bCryptPasswordEncoder.encode(signInRequestDto.getPassword()))) throw new PasswordNotMatchingException();
    }

    // SignIn을 위한 로직1
    @Transactional
    public Authentication getAuthenticationToSignIn(SignInRequestDto signInRequestDto){
        validateSignInRequest(signInRequestDto);
        UsernamePasswordAuthenticationToken authenticationToken = signInRequestDto.toAuthentication();
        return authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    }
}
