package com.board.Board_Upgraded.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Data
public class SignInRequestDto {

    @NotNull(message = "아이디를 입력해주세요.")
    private String username;

    @NotNull(message = "비밀번호를 입력해주세요.")
    private String password;

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(username, password);
    }
}
