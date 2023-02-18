package com.board.Board_Upgraded.service.member;

import com.board.Board_Upgraded.dto.member.EditMemberRequestDto;
import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.exception.member.MemberNotFoundException;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import com.board.Board_Upgraded.service.auth.AuthService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class EditTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("수정 dto에서 닉네임을 제외한 값이 null일 때, 닉네임을 변경하고 나머지는 원래 값을 유지한다.")
    public void editNickname_Success() throws Exception{
        //given
        authService.registerNewMember(RegisterRequestDto.builder()
                .username("test")
                .email("test@test.com")
                .nickname("test")
                .passwordCheck("test")
                .password("test").build());
        Member member = memberRepository.findByUsername("test").orElseThrow(MemberNotFoundException::new);
        //when
        EditMemberRequestDto editMemberRequestDto = EditMemberRequestDto.builder()
                .nickname("newNick")
                .email(null)
                .password(null)
                .passwordCheck(null)
                .build();
        memberService.editMember(editMemberRequestDto, member);
        //then
        Assertions.assertThat(member.getNickname()).isEqualTo("newNick");
        Assertions.assertThat(member.getEmail()).isEqualTo("test@test.com");
    }

    @Test
    @DisplayName("수정 dto에서 이메일을 제외한 값이 null일 때, 이메일 변경하고 나머지는 원래 값을 유지한다.")
    public void editEmail_Success() throws Exception{
        //given
        authService.registerNewMember(RegisterRequestDto.builder()
                .username("test")
                .email("test@test.com")
                .nickname("test")
                .passwordCheck("test")
                .password("test").build());
        Member member = memberRepository.findByUsername("test").orElseThrow(MemberNotFoundException::new);
        //when
        EditMemberRequestDto editMemberRequestDto = EditMemberRequestDto.builder()
                .nickname(null)
                .email("new@test.com")
                .password(null)
                .passwordCheck(null)
                .build();
        memberService.editMember(editMemberRequestDto, member);
        //then
        Assertions.assertThat(member.getNickname()).isEqualTo("test");
        Assertions.assertThat(member.getEmail()).isEqualTo("new@test.com");
    }

    @Test
    @DisplayName("")
    public void editPassword_Success() throws Exception{
        //given

        //when

        //then

    }

    @Test
    @DisplayName("")
    public void editFail_AllNull() throws Exception{
        //given

        //when

        //then

    }

    @Test
    @DisplayName("")
    public void editFail_PasswordNull() throws Exception{
        //given

        //when

        //then

    }

    @Test
    @DisplayName("")
    public void editFail_NicknameAlreadyExists() throws Exception{
        //given

        //when

        //then

    }

    @Test
    @DisplayName("")
    public void editFail_EmailAlreadyExists() throws Exception{
        //given

        //when

        //then

    }

    @Test
    @DisplayName("")
    public void editFail_PasswordNotMatching() throws Exception{
        //given

        //when

        //then

    }

    @Test
    @DisplayName("")
    public void editFail_PasswordNotChanged() throws Exception{
        //given

        //when

        //then

    }
}
