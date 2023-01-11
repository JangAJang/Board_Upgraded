package com.board.Board_Upgraded.domainTest;

import com.board.Board_Upgraded.dto.member.ChangeEmailRequestDto;
import com.board.Board_Upgraded.dto.member.ChangeNicknameRequestDto;
import com.board.Board_Upgraded.dto.member.ChangePasswordRequestDto;
import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.entity.member.Role;
import com.board.Board_Upgraded.exception.member.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class MemberTest {

    // 회원가입 테스트
    @Test
    @DisplayName("회원가입상의 문제가 없을 경우, 회원가입이 진행된다. ")
    void registerTest(){
        Member member = new Member(makeTestRegister());
        assertThat(member.getNickname()).isEqualTo("nickname");
        assertThat(member.getEmail()).isEqualTo("email@email.com");
        assertThat(member.getUsername()).isEqualTo("username");
        assertThat(member.getRole()).isEqualTo(Role.USER);
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
    @DisplayName("비밀번호와 비밀번호 확인이 다를 경우 회원가입에 예외가 발생된다.")
    void notSamePasswordRegister(){
        RegisterRequestDto test = makeTestRegister();
        test.setPasswordCheck("wrong");
        assertThatThrownBy(()-> new Member(test))
                .isInstanceOf(PasswordNotMatchingException.class);
    }

    // 닉네임 변경 테스트
    @Test
    @DisplayName("닉네임을 수정하면, 변경된 닉네임이 나온다. ")
    void changeNicknameTest(){
        Member member = new Member(makeTestRegister());
        member.changeNickname(new ChangeNicknameRequestDto("newNickname"));
        assertThat(member.getNickname()).isEqualTo("newNickname");
    }

    @Test
    @DisplayName("기존의 닉네임으로 닉네임을 수정하면 예외처리된다.")
    void changeSameNickname(){
        Member member = new Member(makeTestRegister());
        assertThatThrownBy(()->member.changeNickname(new ChangeNicknameRequestDto("nickname")))
                .isInstanceOf(NicknameAlreadyInUseException.class);
    }

    // 이메일 변경 테스트
    @Test
    @DisplayName("이메일을 수정하면, 변경된 이메일이 나온다. ")
    void changeEmailTest(){
        Member member = new Member(makeTestRegister());
        member.changeEmail(new ChangeEmailRequestDto("newEmail@email.com"));
        assertThat(member.getEmail()).isEqualTo("newEmail@email.com");
    }

    @Test
    @DisplayName("기존의 이메일로 이메일을 수정하면 예외처리된다. ")
    void changeSameEmail(){
        Member member = new Member(makeTestRegister());
        assertThatThrownBy(()->member.changeEmail(new ChangeEmailRequestDto(member.getEmail())))
                .isInstanceOf(EmailAlreadyInUseException.class);
    }

    @Test
    @DisplayName("수정하려는 이메일의 형식이 올바르지 않으면 예외처리한다. ")
    void emailNotFormat(){
        Member member = new Member(makeTestRegister());
        assertThatThrownBy(()->member.changeEmail(new ChangeEmailRequestDto("wrong")))
                .isInstanceOf(EmailNotFormatException.class);
    }

    // 비밀번호 수정
    @Test
    @DisplayName("비밀번호를 정상적으로 수정하면, 성공되고, 로그인시 다른 비밀번호로 로그인해야한다. ")
    void changePasswordTest(){
        Member member = new Member(makeTestRegister());
        ChangePasswordRequestDto changePasswordRequestDto = new ChangePasswordRequestDto("newP", "newP");
        member.changePassword(changePasswordRequestDto);
        assertThat(member.getPassword()).isEqualTo("newP");
    }

    @Test
    @DisplayName("비밀 번호수정시, 두번 쓴 비밀번호가 서로 다르면 예외처리한다.  ")
    void passwordNotMatch(){
        Member member = new Member(makeTestRegister());
        ChangePasswordRequestDto changePasswordRequestDto = new ChangePasswordRequestDto("newP", "mextP");
        assertThatThrownBy(()-> member.changePassword(changePasswordRequestDto))
                .isInstanceOf(PasswordNotMatchingException.class);
    }

    @Test
    @DisplayName("비밀번호가 이전에 사용하던 비밀번호와 같으면 예외처리한다.")
    void passwordFormalSame(){
        Member member = new Member(makeTestRegister());
        ChangePasswordRequestDto changePasswordRequestDto = new ChangePasswordRequestDto("password", "password");
        assertThatThrownBy(()-> member.changePassword(changePasswordRequestDto))
                .isInstanceOf(PasswordNotChangedException.class);
    }

    private RegisterRequestDto makeTestRegister(){
        return RegisterRequestDto.builder()
                .username("username")
                .nickname("nickname")
                .email("email@email.com")
                .password("password")
                .passwordCheck("password")
                .build();
    }
}
