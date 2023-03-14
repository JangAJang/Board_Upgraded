package com.board.Board_Upgraded.domain.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberInfo {

    @Column(name = "USERNAME")
    private String nickname;
    @Column(name = "EMAIL")
    private String email;
}
