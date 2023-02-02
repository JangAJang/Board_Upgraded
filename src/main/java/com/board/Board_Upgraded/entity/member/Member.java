package com.board.Board_Upgraded.entity.member;

import com.board.Board_Upgraded.dto.member.ChangeEmailRequestDto;
import com.board.Board_Upgraded.dto.member.ChangeNicknameRequestDto;
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

    public void changeNickname(ChangeNicknameRequestDto changeNicknameRequestDto){
        if(isNicknameSameWithFormal(changeNicknameRequestDto)) throw new NicknameAlreadyInUseException();
        this.nickname = changeNicknameRequestDto.getNewNickname();
    }
    
    private boolean isNicknameSameWithFormal(ChangeNicknameRequestDto changeNicknameRequestDto){
        return this.nickname.equals(changeNicknameRequestDto.getNewNickname());
    }

    public void changeEmail(ChangeEmailRequestDto changeEmailRequestDto){
        if(isEmailNotFormat(changeEmailRequestDto.getNewEmail())) throw new EmailNotFormatException();
        if(isEmailSameWithFormal(changeEmailRequestDto)) throw new EmailAlreadyInUseException();
        this.email = changeEmailRequestDto.getNewEmail();
    }
    
    private boolean isEmailSameWithFormal(ChangeEmailRequestDto changeEmailRequestDto){
        return this.email.equals(changeEmailRequestDto.getNewEmail());
    }

    private boolean isEmailNotFormat(String email){
        return !Pattern.matches("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", email);
    }

    public void changePassword(ChangePasswordRequestDto changePasswordRequestDto){
        if(isPasswordSameWithFormal(changePasswordRequestDto.getNewPassword()))
            throw new PasswordNotChangedException();
        this.password = changePasswordRequestDto.getNewPassword();
        this.setLastModifiedDate(LocalDateTime.now());
    }

    private boolean isPasswordSameWithFormal(String pw1){
        return this.password.equals(pw1);
    }

    public boolean isPasswordOutdated(){
        return isLastModifiedDateAfter(DueTime.PASSWORD_CHANGE_DUETIME.getDays());
    }

    public boolean isPasswordRight(String password){
        return password.equals(this.password);
    }
}
