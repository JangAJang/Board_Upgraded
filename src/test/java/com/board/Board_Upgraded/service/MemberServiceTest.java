package com.board.Board_Upgraded.service;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

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
            Member member = new Member(registerRequestDto);
            memberRepository.save(member);
        }
    }

    @Test
    @DisplayName("")
    public void 아이디_중복() throws Exception{
        //given

        //when

        //then

    }
    
    @Test
    @DisplayName("")        
    public void 닉네임_중복() throws Exception{
        //given
        
        //when
        
        //then
        
    }
    
    @Test
    @DisplayName("")        
    public void 이메일_중복() throws Exception{
        //given
        
        //when
        
        //then
        
    }
    
    @Test
    @DisplayName("")        
    public void 비밀번호_불일치() throws Exception{
        //given
        
        //when
        
        //then
        
    }
    
    @Test
    @DisplayName("")        
    public void 회원가입_정상동작() throws Exception{
        //given
        
        //when
        
        //then
        
    }
}
