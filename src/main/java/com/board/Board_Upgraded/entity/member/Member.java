package com.board.Board_Upgraded.entity.member;

import com.board.Board_Upgraded.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member extends BaseEntity {

    @Column(name = "MEMBER_USERNAME")
    private String username;

    @Column(name = "MEMBER_NICKNAME")
    private String nickname;

    @Column(name = "MEMBER_EMAIL")
    private String email;

    @Column(name = "MEMBER_PASSWORD")
    private String password;

    @Column(name = "MEMBER_ROLE")
    @Enumerated(value = EnumType.STRING)
    private Role role;
}