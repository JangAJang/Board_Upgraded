package com.board.Board_Upgraded.repository;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.entity.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("")
    public void createTest() throws Exception{
        try{
            //given
            RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                    .username("testUser")
                    .nickname("test")
                    .email("test@test.com")
                    .password("password")
                    .passwordCheck("password")
                    .build();
            //when
            Member member = new Member(registerRequestDto);
            //then
            assertThatThrownBy(()->memberRepository.save(member)).doesNotThrowAnyException();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
