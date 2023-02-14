package com.board.Board_Upgraded.service;

import com.board.Board_Upgraded.dto.member.ChangePasswordRequestDto;
import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.dto.member.SignInRequestDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.exception.member.*;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class MemberInstanceValidator {

    private MemberRepository memberRepository;
    private PasswordEncoder passwordEncoder;

    public void validateRegisterRequest(RegisterRequestDto registerRequestDto){
        validateUsername(registerRequestDto.getUsername());
        validateNickname(registerRequestDto.getNickname());
        validateEmail(registerRequestDto.getEmail());
        validatePasswordCheck(registerRequestDto.getPassword(), registerRequestDto.getPasswordCheck());
    }

    public void validateSignInRequest(SignInRequestDto signInRequestDto){
        Member member = memberRepository.findByUsername(signInRequestDto.getUsername())
                .orElseThrow(MemberNotFoundException::new);
        if(!passwordEncoder.matches(signInRequestDto.getPassword(), member.getPassword()))
            throw new PasswordNotMatchingException();
    }

    public void validateUsername(String username){
        if(memberRepository.findByUsername(username).isPresent())
            throw new UsernameAlreadyInUseException();
    }

    public void validateNickname(String nickname){
        if(memberRepository.findByNickname(nickname).isPresent())
            throw new NicknameAlreadyInUseException();
    }

    public void validateEmail(String email){
        if(memberRepository.findByEmail(email).isPresent())
            throw new EmailAlreadyInUseException();
    }

    public void validatePasswordCheck(String password, String passwordCheck){
        if(!password.equals(passwordCheck)){
            throw new PasswordNotMatchingException();
        }
    }

    public void validateWithCurrentPassword(ChangePasswordRequestDto changePasswordRequestDto, String currentPassword){
        if(passwordEncoder.matches(changePasswordRequestDto.getNewPassword(), currentPassword))
            throw new PasswordNotChangedException();
    }
}
