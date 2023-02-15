package com.board.Board_Upgraded.controller;

import com.board.Board_Upgraded.config.jwt.TokenPath;
import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.dto.member.SignInRequestDto;
import com.board.Board_Upgraded.dto.token.ReissueRequestDto;
import com.board.Board_Upgraded.dto.token.TokenResponseDto;
import com.board.Board_Upgraded.response.Response;
import com.board.Board_Upgraded.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final TokenPath tokenPath;

    @PostMapping("/join")
    public Response register(@RequestBody @Valid RegisterRequestDto registerRequestDto){
        authService.registerNewMember(registerRequestDto);
        return Response.success("회원가입 완료");
    }

    @PostMapping("/sign_in")
    public Response signIn(@RequestBody @Valid SignInRequestDto signInRequestDto, HttpServletResponse response){
        TokenResponseDto membersToken = authService.signIn(signInRequestDto);
        tokenPath.putTokensOnHeader(response, membersToken);
        return Response.success(membersToken);
    }

    @PostMapping("/reissue")
    public Response reissue(HttpServletRequest request, HttpServletResponse response){
        TokenResponseDto tokenResponseDto = authService.reissue(tokenPath.getReissueResponseDtoFromHeader(request));
        tokenPath.putTokensOnHeader(response, tokenResponseDto);
        return Response.success(tokenResponseDto);
    }
}
