package com.board.Board_Upgraded.dto.member;

import com.board.Board_Upgraded.domain.member.MemberInfo;
import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EditMemberRequestDto {

    private String nickname;
    private String email;

    public MemberInfo toMemberInfo(){
        return new MemberInfo(nickname, email);
    }
}
