package com.board.Board_Upgraded.entity.member;

import com.board.Board_Upgraded.dto.member.ChangeEmailRequestDto;
import com.board.Board_Upgraded.dto.member.ChangeNicknameRequestDto;
import com.board.Board_Upgraded.dto.member.ChangePasswordRequestDto;
import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
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

    public Member(RegisterRequestDto registerRequestDto){

    }

    public void changeNickname(ChangeNicknameRequestDto changeNicknameRequestDto){

    }
    
    private boolean isNicknameSameWithFormal(ChangeNicknameRequestDto changeNicknameRequestDto){
        return false;
    }

    public void changeEmail(ChangeEmailRequestDto changeEmailRequestDto){

    }
    
    private boolean isEmailSameWithFormal(ChangeEmailRequestDto changeEmailRequestDto){
        return false;
    }

    public void changePassword(ChangePasswordRequestDto changePasswordRequestDto){

    }

    private boolean isPasswordNotSame(ChangePasswordRequestDto changePasswordRequestDto){
        return false;
    }

    private boolean isPasswordSameWithFormal(ChangePasswordRequestDto changePasswordRequestDto){
        return false;
    }

    private boolean isPasswordOutdated(){
        return false;
    }
}
