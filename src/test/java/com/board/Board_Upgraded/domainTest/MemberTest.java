package com.board.Board_Upgraded.domainTest;

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

    private RegisterRequestDto makeTestRegister(){
        return new RegisterRequestDto("username", "nickname", "email@email.com"
                , "password", "password");
    }
}
