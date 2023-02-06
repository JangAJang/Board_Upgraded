package com.board.Board_Upgraded.service;

import com.board.Board_Upgraded.dto.member.ChangeEmailRequestDto;
import com.board.Board_Upgraded.dto.member.ChangeNicknameRequestDto;
import com.board.Board_Upgraded.dto.member.ChangePasswordRequestDto;
import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.exception.member.EmailAlreadyInUseException;
import com.board.Board_Upgraded.exception.member.NicknameAlreadyInUseException;
import com.board.Board_Upgraded.exception.member.PasswordNotChangedException;
import com.board.Board_Upgraded.exception.member.PasswordNotMatchingException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberServiceTest_Update {

    @Autowired
    private MemberService memberService;

    private Member makeMember1(){
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("test1")
                .nickname("test1")
                .email("test1@test.com")
                .password("password1")
                .passwordCheck("password1")
                .build();
        memberService.registerNewMember(registerRequestDto);
        return new Member(registerRequestDto);
    }

    private Member makeMember2(){
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("test2")
                .nickname("test2")
                .email("test2@test.com")
                .password("password2")
                .passwordCheck("password2")
                .build();
        memberService.registerNewMember(registerRequestDto);
        return new Member(registerRequestDto);
    }

    @Test
    @DisplayName("")
    public void 닉네임_변경_중복() throws Exception{
        //given
        makeMember1();
        Member member2 = makeMember2();
        //when
        ChangeNicknameRequestDto changeNicknameRequestDto = new ChangeNicknameRequestDto("test1");
        //then
        assertThatThrownBy(()-> memberService.changeMemberNickname(changeNicknameRequestDto, member2))
                .isInstanceOf(NicknameAlreadyInUseException.class);
    }

    @Test
    @DisplayName("")
    public void 닉네임_변경_성공() throws Exception{
        //given
        makeMember1();
        Member member2 = makeMember2();
        //when
        ChangeNicknameRequestDto changeNicknameRequestDto = new ChangeNicknameRequestDto("test3");
        //then
        memberService.changeMemberNickname(changeNicknameRequestDto, member2);
    }

    @Test
    @DisplayName("")
    public void 이메일_변경_중복() throws Exception{
        //given
        makeMember1();
        Member member2 = makeMember2();
        //when
        ChangeEmailRequestDto changeEmailRequestDto = new ChangeEmailRequestDto("test1@test.com");
        //then
        assertThatThrownBy(()-> memberService.changeMemberEmail(changeEmailRequestDto, member2))
                .isInstanceOf(EmailAlreadyInUseException.class);
    }

    @Test
    @DisplayName("")
    public void 이메일_변경_성공() throws Exception{
        //given
        makeMember1();
        Member member2 = makeMember2();
        //when
        ChangeEmailRequestDto changeEmailRequestDto = new ChangeEmailRequestDto("test3@test.com");
        //then
        memberService.changeMemberEmail(changeEmailRequestDto, member2);
    }

    @Test
    @DisplayName("")
    public void 비밀번호_변경_기존과_동일() throws Exception{
        //given
        makeMember1();
        Member member2 = makeMember2();
        //when
        ChangePasswordRequestDto changePasswordRequestDto
                = new ChangePasswordRequestDto("password2", "password2");
        //then
        assertThatThrownBy(()-> memberService.changeMemberPassword(changePasswordRequestDto, member2))
                .isInstanceOf(PasswordNotChangedException.class);
    }

    @Test
    @DisplayName("")
    public void 비밀번호_변경_입력이_서로_불일치() throws Exception{
        //given
        makeMember1();
        Member member2 = makeMember2();
        //when
        ChangePasswordRequestDto changePasswordRequestDto
                = new ChangePasswordRequestDto("password2", "password3");
        //then
        assertThatThrownBy(()-> memberService.changeMemberPassword(changePasswordRequestDto, member2))
                .isInstanceOf(PasswordNotMatchingException.class);
    }

    @Test
    @DisplayName("")
    public void 비밀번호_변경_성공() throws Exception{
        //given
        makeMember1();
        Member member2 = makeMember2();
        //when
        ChangePasswordRequestDto changePasswordRequestDto
                = new ChangePasswordRequestDto("password3", "password3");
        //then
        memberService.changeMemberPassword(changePasswordRequestDto, member2);
    }
}
