package com.board.Board_Upgraded.dto.member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateRequestDto {

    private final String nickname;
    private final String email;
    private final String password;
    private final String passwordCheck;

    @Builder
    public UpdateRequestDto(String nickname, String email, String password, String passwordCheck) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.passwordCheck = passwordCheck;
    }
}
