package com.board.Board_Upgraded.dto.member;

import com.board.Board_Upgraded.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Data
@Builder
public class MemberInfoDto {

    @NotNull
    private String username;
    @NotNull
    private String nickname;

    @NotNull
    private String email;

    public MemberInfoDto(Member member){
        this.username = member.getUsername();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
    }
}
