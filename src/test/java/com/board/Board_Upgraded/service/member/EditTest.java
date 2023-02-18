package com.board.Board_Upgraded.service.member;

import com.board.Board_Upgraded.dto.member.EditMemberRequestDto;
import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.exception.member.*;
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
    @DisplayName("수정 dto에서 password, passwordCheck 제외한 값이 null일 때, 비밀번호를 변경하고 나머지는 원래 값을 유지한다.")
    public void editPassword_Success() throws Exception{
        //given
        authService.registerNewMember(RegisterRequestDto.builder()
                .username("test")
                .email("test@test.com")
                .nickname("test")
                .passwordCheck("test")
                .password("test").build());
        Member member = memberRepository.findByUsername("test").orElseThrow(MemberNotFoundException::new);
        String formerPassword = member.getPassword();
        //when
        EditMemberRequestDto editMemberRequestDto = EditMemberRequestDto.builder()
                .nickname(null)
                .email(null)
                .password("new")
                .passwordCheck("new")
                .build();
        memberService.editMember(editMemberRequestDto, member);
        //then
        Assertions.assertThat(member.getUsername()).isEqualTo("test");
        Assertions.assertThat(member.getPassword()).isNotEqualTo(formerPassword);
    }

    @Test
    @DisplayName("입력값이 전부 null이면 NeedToAddEditCondition예외를 반환한다.")
    public void editFail_AllNull() throws Exception{
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
                .build();
        //then
        Assertions.assertThatThrownBy(()->memberService.editMember(editMemberRequestDto, member))
                .isInstanceOf(NeedToAddEditConditionException.class);
    }

    @Test
    @DisplayName("PasswordCheck는 null이고 Password는 null이 아니면 비밀번호를 변경할 수 없음을 예외처리한다.")
    public void editFail_PasswordCheckNull() throws Exception{
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
                .password("new")
                .build();
        //then
        Assertions.assertThatThrownBy(()->memberService.editMember(editMemberRequestDto, member))
                .isInstanceOf(NeedToPutPasswordTwiceToEditException.class);
    }

    @Test
    @DisplayName("Password는 null이고 PasswordCheck는 null이 아니면 비밀번호를 변경할 수 없음을 예외처리한다.")
    public void editFail_PasswordNull() throws Exception{
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
                .passwordCheck("new")
                .build();
        //then
        Assertions.assertThatThrownBy(()->memberService.editMember(editMemberRequestDto, member))
                .isInstanceOf(NeedToPutPasswordTwiceToEditException.class);
    }

    @Test
    @DisplayName("이미 존재하는 닉네임으로 변경하려 할 때, NicknameAlreadyInUse로 예외처리한다. ")
    public void editFail_NicknameAlreadyExists() throws Exception{
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
                .nickname("test")
                .build();
        //then
        Assertions.assertThatThrownBy(()->memberService.editMember(editMemberRequestDto, member))
                .isInstanceOf(NicknameAlreadyInUseException.class);
    }

    @Test
    @DisplayName("이미 존재하는 이메일로 변경하려 할 때, EmailAlreadyInUse로 예외처리한다.")
    public void editFail_EmailAlreadyExists() throws Exception{
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
                .email("test@test.com")
                .build();
        //then
        Assertions.assertThatThrownBy(()->memberService.editMember(editMemberRequestDto, member))
                .isInstanceOf(EmailAlreadyInUseException.class);
    }

    @Test
    @DisplayName("이메일 형식이 올바르지 않을 때 예외처리된다. ")
    public void editFail_EmailFormat() throws Exception{
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
                .email("newtest.com")
                .build();
        //then
        Assertions.assertThatThrownBy(()->memberService.editMember(editMemberRequestDto, member))
                .isInstanceOf(EmailNotFormatException.class);
    }

    @Test
    @DisplayName("입력한 비밀번호가 서로 일치하지 않을 때 예외처리시킨다.")
    public void editFail_PasswordNotMatching() throws Exception{
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
                .password("new")
                .passwordCheck("password")
                .build();
        //then
        Assertions.assertThatThrownBy(()->memberService.editMember(editMemberRequestDto, member))
                .isInstanceOf(PasswordNotMatchingException.class);
    }

    @Test
    @DisplayName("변경하려는 비밀번호가 기존과 같을 떄, 예외처리시킨다.")
    public void editFail_PasswordNotChanged() throws Exception{
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
                .password("test")
                .passwordCheck("test")
                .build();
        //then
        Assertions.assertThatThrownBy(()->memberService.editMember(editMemberRequestDto, member))
                .isInstanceOf(PasswordNotChangedException.class);
    }
}
