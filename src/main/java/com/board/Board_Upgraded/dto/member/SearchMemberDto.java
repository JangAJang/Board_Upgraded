package com.board.Board_Upgraded.dto.member;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SearchMemberDto {

    private String username;
    private String nickname;
    private String email;

    public SearchMemberDto(String username, String nickname, String email) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
    }
}
