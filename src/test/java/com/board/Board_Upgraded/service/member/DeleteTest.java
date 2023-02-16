package com.board.Board_Upgraded.service.member;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.entity.member.Role;
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
public class DeleteTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthService authService;

    @Test
    @DisplayName("삭제 요청시에 회원이 존재하면 삭제시킨다. ")
    public void deleteSuccess() throws Exception{
        //given
        authService.registerNewMember(RegisterRequestDto.builder()
                .username("test")
                .nickname("test")
                .email("test@test.com")
                .password("test")
                .passwordCheck("test")
                .build());
        //when
        Member member = memberRepository.findByUsername("test").orElseThrow(MemberNotFoundException::new);
        //then
        Assertions.assertThat(memberService.deleteMember(member))
                .isEqualTo("회원이 삭제되었습니다. 그동한 감사합니다.");
    }

    @Test
    @DisplayName("삭제 실패시에 해당 회원이 존재하지 않는 것이기 때문에, MemberNotFoundException을 반환한다. ")
    public void deleteFail() throws Exception{
        //given
        //when
        Member member = Member.builder()
                .username("test")
                .nickname("test")
                .email("test@test.com")
                .password("test")
                .role(Role.USER).build();
        memberRepository.deleteAll();
        //then
        Assertions.assertThatThrownBy(()->memberService.deleteMember(member))
                .isInstanceOf(MemberNotFoundException.class);
    }
}
