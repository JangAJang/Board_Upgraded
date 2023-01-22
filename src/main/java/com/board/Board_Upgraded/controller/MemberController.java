package com.board.Board_Upgraded.controller;

import com.board.Board_Upgraded.dto.member.*;
import com.board.Board_Upgraded.dto.token.TokenDto;
import com.board.Board_Upgraded.dto.token.TokenResponseDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.exception.member.MemberNotFoundException;
import com.board.Board_Upgraded.repository.MemberRepository;
import com.board.Board_Upgraded.response.Response;
import com.board.Board_Upgraded.service.MemberService;
import com.board.Board_Upgraded.service.RefreshTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final RefreshTokenService refreshTokenService;
    private final MemberRepository memberRepository;

    @ApiOperation(value = "회원가입", notes = "회원가입 페이지 입니다.")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/join")
    public Response registerMember(RegisterRequestDto registerRequestDto){
        memberService.registerNewMember(registerRequestDto);
        return Response.success("회원 가입 성공");
    }

    @ApiOperation(value = "로그인", notes = "로그인 페이지입니다.")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/signIn")
    public TokenResponseDto signIn(SignInRequestDto signInRequestDto){
        return refreshTokenService.createTokenDtoByAuthentication(memberService.getAuthenticationToSignIn(signInRequestDto));
    }

    @ApiOperation(value = "닉네임 변경", notes = "회원의 닉네임을 변경하는 기능 구현")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/changeNickname")
    public MemberInfoDto changeNickname(ChangeNicknameRequestDto changeNicknameRequestDto){
        Member member = findMemberByUsername();
        memberService.changeMemberNickname(changeNicknameRequestDto, member);
        return new MemberInfoDto(member);
    }

    @ApiOperation(value = "이메일 변경", notes = "회원의 이메일을 변경하는 기능 구현")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/changeEmail")
    public MemberInfoDto changeEmail(ChangeEmailRequestDto changeEmailRequestDto){
        Member member = findMemberByUsername();
        memberService.changeMemberEmail(changeEmailRequestDto, member);
        return new MemberInfoDto(member);
    }

    @ApiOperation(value = "비밀번호 변경", notes = "회원의 비밀번호를 변경하는 기능 구현")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/changePassword")
    public Response changePassword(ChangePasswordRequestDto changePasswordRequestDto){
        Member member = findMemberByUsername();
        memberService.changeMemberPassword(changePasswordRequestDto, member);
        return Response.success(member.getNickname() + "님의 비밀번호 변경을 성공했습니다");
    }

    private Member findMemberByUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return memberRepository.findByUsername(authentication.getName()).orElseThrow(MemberNotFoundException::new);
    }
}
