package com.board.Board_Upgraded.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Data
public class RegisterRequestDto {

    @NotNull(message = "")
    private String username;

    @NotNull(message = "")
    private String nickname;

    @NotNull(message = "")
    private String email;

    @NotNull(message = "")
    private String password;

    @NotNull(message = "")
    private String passwordCheck;
}
