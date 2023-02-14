package com.board.Board_Upgraded.service.member;

import com.board.Board_Upgraded.dto.member.ChangeEmailRequestDto;
import com.board.Board_Upgraded.dto.member.ChangeNicknameRequestDto;
import com.board.Board_Upgraded.dto.member.ChangePasswordRequestDto;
import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.exception.member.EmailAlreadyInUseException;
import com.board.Board_Upgraded.exception.member.NicknameAlreadyInUseException;
import com.board.Board_Upgraded.exception.member.PasswordNotChangedException;
import com.board.Board_Upgraded.exception.member.PasswordNotMatchingException;
import com.board.Board_Upgraded.service.AuthService;
import com.board.Board_Upgraded.service.MemberService;
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
    @Autowired
    private AuthService authService;

    private Member makeMember(String index){
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("test" + index)
                .nickname("test" + index)
                .email("test" + index + "@test.com")
                .password("password" + index)
                .passwordCheck("password" + index)
                .build();
        authService.registerNewMember(registerRequestDto);
        return new Member(registerRequestDto);
    }

    @Test
    @DisplayName("")
    public void 닉네임_변경_중복() throws Exception{
        //given
        makeMember("1");
        Member member2 = makeMember("2");
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
        makeMember("1");
        Member member2 = makeMember("2");
        //when
        ChangeNicknameRequestDto changeNicknameRequestDto = new ChangeNicknameRequestDto("test3");
        //then
        memberService.changeMemberNickname(changeNicknameRequestDto, member2);
    }

    @Test
    @DisplayName("")
    public void 이메일_변경_중복() throws Exception{
        //given
        makeMember("1");
        Member member2 = makeMember("2");
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
        makeMember("1");
        Member member2 = makeMember("2");
        //when
        ChangeEmailRequestDto changeEmailRequestDto = new ChangeEmailRequestDto("test3@test.com");
        //then
        memberService.changeMemberEmail(changeEmailRequestDto, member2);
    }

    @Test
    @DisplayName("")
    public void 비밀번호_변경_기존과_동일() throws Exception{
        //given
        makeMember("1");
        Member member2 = makeMember("2");
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
        makeMember("1");
        Member member2 = makeMember("2");
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
        makeMember("1");
        Member member2 = makeMember("2");
        //when
        ChangePasswordRequestDto changePasswordRequestDto
                = new ChangePasswordRequestDto("password3", "password3");
        //then
        memberService.changeMemberPassword(changePasswordRequestDto, member2);
    }
}
