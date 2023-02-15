package com.board.Board_Upgraded.controller;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.dto.member.SignInRequestDto;
import com.board.Board_Upgraded.dto.token.TokenResponseDto;
import com.board.Board_Upgraded.response.Response;
import com.board.Board_Upgraded.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/join")
    public Response register(@RequestBody @Valid RegisterRequestDto registerRequestDto){
        authService.registerNewMember(registerRequestDto);
        return Response.success("회원가입 완료");
    }

    @PostMapping("/sign_in")
    public Response signIn(@RequestHeader @Valid SignInRequestDto signInRequestDto){
        TokenResponseDto membersToken = authService.signIn(signInRequestDto);
        return Response.success(membersToken);
    }
}
