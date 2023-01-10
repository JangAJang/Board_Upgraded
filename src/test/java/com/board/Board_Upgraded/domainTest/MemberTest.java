package com.board.Board_Upgraded.domainTest;

import com.board.Board_Upgraded.dto.member.ChangeEmailRequestDto;
import com.board.Board_Upgraded.dto.member.ChangeNicknameRequestDto;
import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.exception.member.EmailNotFormatException;
import com.board.Board_Upgraded.exception.member.PasswordNotMatchingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class MemberTest {



    @Test
    @DisplayName("회원가입상의 문제가 없을 경우, 회원가입이 진행된다. ")
    void registerTest(){
       assertThatThrownBy(()->new Member(makeTestRegister()))
               .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("비밀번호와 비밀번호 확인이 다를 경우 회원가입에 예외가 발생된다.")
    void notSamePasswordRegister(){
        RegisterRequestDto test = makeTestRegister();
        test.setPasswordCheck("wrong");
        assertThatThrownBy(()-> new Member(test))
                .isInstanceOf(PasswordNotMatchingException.class);
    }

    @Test
    @DisplayName("이메일 형식이 올바르지 않을 경우 예외처리된다. ")
    void notRightEmailFormat(){
        RegisterRequestDto registerRequestDto = makeTestRegister();
        registerRequestDto.setEmail("wrongEamail");
        assertThatThrownBy(()-> new Member(registerRequestDto))
                .isInstanceOf(EmailNotFormatException.class);
    }

    @Test
    @DisplayName("닉네임을 수정하면, 변경된 닉네임이 나온다. ")
    void changeNicknameTest(){
        Member member = new Member(makeTestRegister());
        ChangeNicknameRequestDto changeNicknameRequestDto = new ChangeNicknameRequestDto("newNickname");
        member.changeNickname(changeNicknameRequestDto);
        assertThat(member.getNickname()).isEqualTo("newNickname");
    }

    @Test
    @DisplayName("이메일을 수정하면, 변경된 닉네임이 나온다. ")
    void changeEmailTest(){
        Member member = new Member(makeTestRegister());
        ChangeEmailRequestDto changeNicknameRequestDto = new ChangeEmailRequestDto("newEmail@email.com");
        member.changEmail(changeNicknameRequestDto);
        assertThat(member.getNickname()).isEqualTo("newEmail@email.com");
    }

    @Test
    @DisplayName("비밀번호를 정상적으로 수정하면, 성공되고, 로그인시 다른 비밀번호로 로그인해야한다. ")
    void changePasswordTest(){

    }

    @Test
    @DisplayName("비밀 번호수정시, 두번 쓴 비밀번호가 서로 다르면 예외처리한다.  ")
    void passwordNotMatch(){

    }

    private RegisterRequestDto makeTestRegister(){
        return new RegisterRequestDto("username", "nickname", "email@email.com"
                , "password", "password");
    }
}
