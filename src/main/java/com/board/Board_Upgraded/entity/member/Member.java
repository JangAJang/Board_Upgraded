package com.board.Board_Upgraded.entity.member;

import com.board.Board_Upgraded.dto.member.ChangeEmailRequestDto;
import com.board.Board_Upgraded.dto.member.ChangeNicknameRequestDto;
import com.board.Board_Upgraded.dto.member.ChangePasswordRequestDto;
import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.entity.BaseEntity;
import com.board.Board_Upgraded.exception.member.EmailAlreadyInUseException;
import com.board.Board_Upgraded.exception.member.EmailNotFormatException;
import com.board.Board_Upgraded.exception.member.NicknameAlreadyInUseException;
import com.board.Board_Upgraded.exception.member.PasswordNotMatchingException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
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
        if(isPasswordNotSame(registerRequestDto.getPassword(), registerRequestDto.getPasswordCheck()))
            throw new PasswordNotMatchingException();
        this.username = registerRequestDto.getUsername();
        this.nickname = registerRequestDto.getNickname();
        this.email = registerRequestDto.getEmail();
        this.role = Role.USER;
        this.password = encryptPassword(registerRequestDto.getPassword());
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

    }

    private boolean isPasswordNotSame(String pw1, String pw2){
        return !pw1.equals(pw2);
    }

    private boolean isPasswordSameWithFormal(String pw1){
        return false;
    }

    private boolean isPasswordOutdated(){
        return false;
    }

    private String encryptPassword(String password){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }
}
