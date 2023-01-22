package com.board.Board_Upgraded.controller;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.dto.member.SignInRequestDto;
import com.board.Board_Upgraded.dto.token.TokenDto;
import com.board.Board_Upgraded.dto.token.TokenResponseDto;
import com.board.Board_Upgraded.response.Response;
import com.board.Board_Upgraded.service.MemberService;
import com.board.Board_Upgraded.service.RefreshTokenService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @ApiOperation(value = "회원가입", notes = "회원가입 페이지 입니다.")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/join")
    public Response registerMember(RegisterRequestDto registerRequestDto){
        memberService.registerNewMember(registerRequestDto);
        return Response.success("회원 가입 성공");
    }
}
