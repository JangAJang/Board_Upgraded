package com.board.Board_Upgraded.dto.member;

import com.board.Board_Upgraded.entity.member.Member;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchMemberDto {

    private String username;
    private String nickname;
    private String email;

    @QueryProjection
    @Builder
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
