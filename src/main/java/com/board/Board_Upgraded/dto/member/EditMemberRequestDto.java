package com.board.Board_Upgraded.dto.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EditMemberRequestDto {

    private String nickname;
    private String email;
    private String password;
    private String passwordCheck;

    @Builder
    public EditMemberRequestDto(String nickname, String email, String password, String passwordCheck) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.passwordCheck = passwordCheck;
    }
}
