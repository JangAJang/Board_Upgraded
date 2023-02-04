package com.board.Board_Upgraded.dto.member;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SearchMemberDto {

    @NotNull(message = "검색할 대상의 닉네임을 입력해주세요.")
    private String nickname;

    @NotNull(message = "검색할 대상의 이메일을 입력하세요.")
    private String email;

    @NotNull(message = "검색할 대상의 아이디를 입력하세요.")
    private String username;
}
