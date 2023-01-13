package com.board.Board_Upgraded.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Data
public class SearchMemberDto {

    @NotNull(message = "검색할 대상의 닉네임을 입력해주세요.")
    private String nickname;
}
