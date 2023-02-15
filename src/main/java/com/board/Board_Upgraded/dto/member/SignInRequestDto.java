package com.board.Board_Upgraded.dto.member;

import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInRequestDto {

    @NotNull(message = "아이디를 입력해야 합니다.")
    @NotBlank(message = "아이디를 입력해야 합니다.")
    private String username;

    @NotNull(message = "비밀번호를 입력해야 합니다.")
    @NotBlank(message = "비밀번호를 입력해야 합니다.")
    private String password;

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(username, password);
    }
}
