package com.board.Board_Upgraded.controller;

import com.board.Board_Upgraded.dto.member.*;
import com.board.Board_Upgraded.dto.token.ReissueRequestDto;
import com.board.Board_Upgraded.dto.token.TokenResponseDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.exception.member.MemberNotFoundException;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import com.board.Board_Upgraded.response.Response;
import com.board.Board_Upgraded.service.MemberService;
import com.board.Board_Upgraded.service.RefreshTokenService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final RefreshTokenService refreshTokenService;
    private final MemberRepository memberRepository;

    @ApiOperation(value = "회원가입", notes = "회원가입 페이지 입니다.")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/join")
    public Response registerMember(@RequestBody @Valid RegisterRequestDto registerRequestDto){
        System.out.println(registerRequestDto.toString());
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
    @PostMapping("/myPage/changeNickname")
    public SearchMemberDto changeNickname(ChangeNicknameRequestDto changeNicknameRequestDto){
        Member member = findMemberByUsername();
        memberService.changeMemberNickname(changeNicknameRequestDto, member);
        return new SearchMemberDto(member);
    }

    @ApiOperation(value = "이메일 변경", notes = "회원의 이메일을 변경하는 기능 구현")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/myPage/changeEmail")
    public SearchMemberDto changeEmail(ChangeEmailRequestDto changeEmailRequestDto){
        Member member = findMemberByUsername();
        memberService.changeMemberEmail(changeEmailRequestDto, member);
        return new SearchMemberDto(member);
    }

    @ApiOperation(value = "비밀번호 변경", notes = "회원의 비밀번호를 변경하는 기능 구현")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/myPage/changePassword")
    public Response changePassword(ChangePasswordRequestDto changePasswordRequestDto){
        Member member = findMemberByUsername();
        memberService.changeMemberPassword(changePasswordRequestDto, member);
        return Response.success(member.getNickname() + "님의 비밀번호 변경을 성공했습니다");
    }

    @ApiOperation(value = "회원 조회", notes = "회원을 검색해 기본 정보를 반환받는 기능")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/searchMember")
    public SearchMemberDto searchMember(SearchMemberDto searchMemberDto){
        return memberService.searchMember(searchMemberDto);
    }

    @ApiOperation(value = "마이페이지", notes = "로그인시에 개인 정보를 반환받는 기능")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/myPage")
    public SearchMemberDto getMyPage(){
        return new SearchMemberDto(findMemberByUsername());
    }

    @ApiOperation(value = "토큰 재발행", notes = "리프레쉬 토큰을 재발행받는 기능 구현")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/myPage/reissue")
    public TokenResponseDto reissue(ReissueRequestDto reissueRequestDto){
        return refreshTokenService.reissue(reissueRequestDto);
    }

    private Member findMemberByUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return memberRepository.findByUsername(authentication.getName()).orElseThrow(MemberNotFoundException::new);
    }
}
