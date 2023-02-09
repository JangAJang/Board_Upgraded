package com.board.Board_Upgraded.service;

import com.board.Board_Upgraded.dto.member.*;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.exception.member.*;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import com.board.Board_Upgraded.repository.member.SearchType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public void registerNewMember(RegisterRequestDto registerRequestDto){
        validateUsername(registerRequestDto.getUsername());
        validateNickname(registerRequestDto.getNickname());
        validateEmail(registerRequestDto.getEmail());
        validatePasswordCheck(registerRequestDto.getPassword(), registerRequestDto.getPasswordCheck());
        registerRequestDto.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
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

    private void validateWithCurrentPassword(ChangePasswordRequestDto changePasswordRequestDto, String currentPassword){
        if(passwordEncoder.matches(changePasswordRequestDto.getNewPassword(), currentPassword))
            throw new PasswordNotChangedException();
    }

    @Transactional
    public void changeMemberEmail(ChangeEmailRequestDto changeEmailRequestDto, Member member){
        validateEmail(changeEmailRequestDto.getNewEmail());
        member.changeEmail(changeEmailRequestDto);
    }

    @Transactional
    public void changeMemberNickname(ChangeNicknameRequestDto changeNicknameRequestDto, Member member){
        validateNickname(changeNicknameRequestDto.getNewNickname());
        member.changeNickname(changeNicknameRequestDto);
    }

    @Transactional
    public void changeMemberPassword(ChangePasswordRequestDto changePasswordRequestDto, Member member){
        validatePasswordCheck(changePasswordRequestDto.getNewPassword(), changePasswordRequestDto.getNewPasswordCheck());
        validateWithCurrentPassword(changePasswordRequestDto, member.getPassword());
        changePasswordRequestDto.setNewPasswordCheck(passwordEncoder.encode(changePasswordRequestDto.getNewPassword()));
        member.changePassword(changePasswordRequestDto);
    }

    @Transactional(readOnly = true)
    public Page<SearchMemberDto> search(SearchMemberDto searchMemberDto, Pageable pageable, SearchType searchType){
        return memberRepository.search(searchMemberDto, pageable, searchType);
    }

    private void validateSignInRequest(SignInRequestDto signInRequestDto){
        Member member = memberRepository.findByUsername(signInRequestDto.getUsername())
                .orElseThrow(MemberNotFoundException::new);
        if(!passwordEncoder.matches(signInRequestDto.getPassword(), member.getPassword()))
            throw new PasswordNotMatchingException();
    }

    // SignIn을 위한 로직1
    @Transactional
    public Authentication getAuthenticationToSignIn(SignInRequestDto signInRequestDto){
        validateSignInRequest(signInRequestDto);
        UsernamePasswordAuthenticationToken authenticationToken = signInRequestDto.toAuthentication();
        return authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    }
}
