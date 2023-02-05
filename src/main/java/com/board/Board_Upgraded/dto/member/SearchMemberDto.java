package com.board.Board_Upgraded.dto.member;

import com.board.Board_Upgraded.entity.member.Member;
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


    public SearchMemberDto(Member member){
        this.username = member.getUsername();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
    }
}
