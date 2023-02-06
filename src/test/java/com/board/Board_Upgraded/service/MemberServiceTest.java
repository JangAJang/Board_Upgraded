package com.board.Board_Upgraded.service;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.exception.member.EmailAlreadyInUseException;
import com.board.Board_Upgraded.exception.member.NicknameAlreadyInUseException;
import com.board.Board_Upgraded.exception.member.PasswordNotMatchingException;
import com.board.Board_Upgraded.exception.member.UsernameAlreadyInUseException;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MemberServiceTest {

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
        }
    }

    @Test
    @DisplayName("")
    public void 아이디_중복() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("testUser1")
                .nickname("아장아장")
                .email("jangajang@email.com")
                .nickname("아장아장")
                .passwordCheck("아장아장")
                .build();
        //when

        //then
        assertThatThrownBy(()-> memberService.registerNewMember(registerRequestDto))
                .isInstanceOf(UsernameAlreadyInUseException.class);
    }
    
    @Test
    @DisplayName("")        
    public void 닉네임_중복() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("jangajang")
                .nickname("testUser1")
                .email("jangajang@email.com")
                .nickname("아장아장")
                .passwordCheck("아장아장")
                .build();
        //when

        //then
        assertThatThrownBy(()-> memberService.registerNewMember(registerRequestDto))
                .isInstanceOf(NicknameAlreadyInUseException.class);
    }
    
    @Test
    @DisplayName("")        
    public void 이메일_중복() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("jangajang")
                .nickname("아장아장")
                .email("test1@test.com")
                .nickname("아장아장")
                .passwordCheck("아장아장")
                .build();
        //when

        //then
        assertThatThrownBy(()-> memberService.registerNewMember(registerRequestDto))
                .isInstanceOf(EmailAlreadyInUseException.class);
    }
    
    @Test
    @DisplayName("")        
    public void 비밀번호_불일치() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("jangajang")
                .nickname("아장아장")
                .email("jangajang@email.com")
                .nickname("아장아장")
                .passwordCheck("아장아장1")
                .build();
        //when

        //then
        assertThatThrownBy(()-> memberService.registerNewMember(registerRequestDto))
                .isInstanceOf(PasswordNotMatchingException.class);
    }
    
    @Test
    @DisplayName("")        
    public void 회원가입_정상동작() throws Exception{
        //given
        
        //when
        
        //then
        
    }
}
