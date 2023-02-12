package com.board.Board_Upgraded.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@Builder
public class RegisterRequestDto {

    @NotNull(message = "아이디를 입력해야 합니다.")
    @NotBlank(message = "아이디를 입력해야 합니다.")
    private String username;

    @NotNull(message = "닉네임을 입력해야 합니다.")
    @NotBlank(message = "닉네임을 입력해야 합니다.")
    private String nickname;

    @NotNull(message = "이메일을 입력해야 합니다.")
    @NotBlank(message = "이메일을 입력해야 합니다.")
    private String email;

    @NotNull(message = "비밀번호를 입력해야 합니다.")
    @NotBlank(message = "비밀번호를 입력해야 합니다.")
    private String password;

    @NotNull(message = "비밀번호를 다시 입력해야 합니다.")
    @NotBlank(message = "비밀번호를 다시 입력해야 합니다.")
    private String passwordCheck;

    protected RegisterRequestDto() {}

    public RegisterRequestDto(String username, String nickname, String email, String password, String passwordCheck) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.passwordCheck = passwordCheck;
    }
}
