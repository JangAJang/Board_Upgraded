package com.board.Board_Upgraded.service;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.exception.member.EmailAlreadyInUseException;
import com.board.Board_Upgraded.exception.member.NicknameAlreadyInUseException;
import com.board.Board_Upgraded.exception.member.PasswordNotMatchingException;
import com.board.Board_Upgraded.exception.member.UsernameAlreadyInUseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberServiceTest_Register {

    @Autowired
    private MemberService memberService;

    @BeforeEach
    void initData(){
        for(int index = 1; index <=10; index++){
            RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                    .username("testUser" + index)
                    .nickname("test" + index)
                    .email("test"  + index + "@test.com")
                    .password("테스트" + index)
                    .passwordCheck("테스트" + index)
                    .build();
            memberService.registerNewMember(registerRequestDto);
            System.out.println("test data activated");
        }
    }

    @Test
    @DisplayName("회원가입_아이디 중복")
    public void 아이디_중복() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("testUser1")
                .nickname("아장아장")
                .email("jangajang@email.com")
                .nickname("아장아장")
                .password("아장아장")
                .passwordCheck("아장아장")
                .build();
        //when

        //then
        assertThatThrownBy(()-> memberService.registerNewMember(registerRequestDto))
                .isInstanceOf(UsernameAlreadyInUseException.class);
    }
    
    @Test
    @DisplayName("회원가입_닉네임 중복")
    public void 닉네임_중복() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("jangajang")
                .nickname("test1")
                .email("jangajang@email.com")
                .password("아장아장")
                .passwordCheck("아장아장")
                .build();
        //when

        //then
        assertThatThrownBy(()-> memberService.registerNewMember(registerRequestDto))
                .isInstanceOf(NicknameAlreadyInUseException.class);
    }
    
    @Test
    @DisplayName("회원가입_이메일 중복")
    public void 이메일_중복() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("jangajang")
                .nickname("아장아장")
                .email("test1@test.com")
                .nickname("아장아장")
                .password("아장아장")
                .passwordCheck("아장아장")
                .build();
        //when

        //then
        assertThatThrownBy(()-> memberService.registerNewMember(registerRequestDto))
                .isInstanceOf(EmailAlreadyInUseException.class);
    }
    
    @Test
    @DisplayName("회원가입_비밀번호 불일치")
    public void 비밀번호_불일치() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("jangajang")
                .nickname("아장아장")
                .email("jangajang@email.com")
                .nickname("아장아장")
                .password("아장아장")
                .passwordCheck("아장아장1")
                .build();
        //when

        //then
        assertThatThrownBy(()-> memberService.registerNewMember(registerRequestDto))
                .isInstanceOf(PasswordNotMatchingException.class);
    }
    
    @Test
    @DisplayName("회원가입_정상동작")
    public void 회원가입_정상동작() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("jangajang")
                .nickname("아장아장")
                .email("jangajang@test.com")
                .nickname("아장아장")
                .password("아장아장")
                .passwordCheck("아장아장")
                .build();
        //when

        //then
        memberService.registerNewMember(registerRequestDto);
    }
}
