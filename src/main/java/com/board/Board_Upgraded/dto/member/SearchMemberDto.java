package com.board.Board_Upgraded.dto.member;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SearchMemberDto {

    private String nickname;
    private String email;
    private String username;
}
