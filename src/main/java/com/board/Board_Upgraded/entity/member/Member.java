package com.board.Board_Upgraded.entity.member;

import com.board.Board_Upgraded.dto.member.ChangePasswordRequestDto;
import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.entity.base.BaseEntity;
import com.board.Board_Upgraded.entity.base.DueTime;
import com.board.Board_Upgraded.exception.member.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

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
        if(isEmailNotFormat(registerRequestDto.getEmail())) throw new EmailNotFormatException();
        this.username = registerRequestDto.getUsername();
        this.nickname = registerRequestDto.getNickname();
        this.email = registerRequestDto.getEmail();
        this.role = Role.USER;
        this.password = registerRequestDto.getPassword();
        this.setLastModifiedDate(LocalDateTime.now());
    }

    public void changeNickname(String nickname){
        this.nickname = nickname;
    }

    public void changeEmail(String email){
        if(isEmailNotFormat(email)) throw new EmailNotFormatException();
        this.email = email;
    }

    private boolean isEmailNotFormat(String email){
        return !Pattern.matches("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", email);
    }

    public void changePassword(ChangePasswordRequestDto changePasswordRequestDto){
        this.password = changePasswordRequestDto.getNewPassword();
        this.setLastModifiedDate(LocalDateTime.now());
    }

    public boolean isPasswordOutdated(){
        return isLastModifiedDateAfter(DueTime.PASSWORD_CHANGE_DUETIME.getDays());
    }
}
