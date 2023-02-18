package com.board.Board_Upgraded.domainTest;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.entity.member.Role;
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

    // 닉네임 변경 테스트
    @Test
    @DisplayName("닉네임을 수정하면, 변경된 닉네임이 나온다. ")
    void changeNicknameTest(){
        Member member = new Member(makeTestRegister());
        member.changeNickname("newNickname");
        assertThat(member.getNickname()).isEqualTo("newNickname");
    }

    // 이메일 변경 테스트
    @Test
    @DisplayName("이메일을 수정하면, 변경된 이메일이 나온다. ")
    void changeEmailTest(){
        Member member = new Member(makeTestRegister());
        member.changeEmail("newEmail@email.com");
        assertThat(member.getEmail()).isEqualTo("newEmail@email.com");
    }

    // 비밀번호 수정
    @Test
    @DisplayName("비밀번호를 정상적으로 수정하면, 성공되고, 로그인시 다른 비밀번호로 로그인해야한다. ")
    void changePasswordTest(){
        Member member = new Member(makeTestRegister());
        member.changePassword("newP");
        assertThat(member.getPassword()).isEqualTo("newP");
    }

    @Test
    @DisplayName("최종 수정일이 한달이 되지 않았을 경우, 거짓을 반환한다.")
    void isLastModifiedLessThanMonthTest(){
        Member member = new Member(makeTestRegister());
        member.changePassword("newP");
        assertThat(member.isPasswordOutdated()).isFalse();
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
