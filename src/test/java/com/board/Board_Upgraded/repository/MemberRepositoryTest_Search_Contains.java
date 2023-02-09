package com.board.Board_Upgraded.repository;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.dto.member.SearchMemberDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static com.board.Board_Upgraded.repository.member.SearchType.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberRepositoryTest_Search_Contains {


    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void initData(){
        for(int index = 1; index <= 100; index++){
            RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                    .username("testUser" + index)
                    .nickname("test" + index)
                    .email("test" + index + "@test.com")
                    .password("password" + index)
                    .passwordCheck("password" + index)
                    .build();
            Member member = new Member(registerRequestDto);
            memberRepository.save(member);
        }
    }

    @Test
    @DisplayName("testUser1로 검색하면, 1, 10, 11, 12, 13, 14, 15, 16, 17, 18이 1페이지에 나온다. ")
    public void searchWith_testUser1() throws Exception{
        //given
        SearchMemberDto searchMemberDto = new SearchMemberDto("testUser1", null, null);
        PageRequest pageRequest1 = PageRequest.of(0, 10);
        PageRequest pageRequest2 = PageRequest.of(1, 10);
        //when
        Page<SearchMemberDto> result1 = memberRepository.search(searchMemberDto, pageRequest1, CONTAINS);
        Page<SearchMemberDto> result2 = memberRepository.search(searchMemberDto, pageRequest2, CONTAINS);
        System.out.println("result1 : " + result1.getContent().toString());
        //then
        assertThat(result1.getContent().stream().map(SearchMemberDto::getUsername))
                .containsExactly("testUser1", "testUser10", "testUser11", "testUser12", "testUser13",
                        "testUser14", "testUser15", "testUser16", "testUser17", "testUser18");
        assertThat(result2.getContent().stream().map(SearchMemberDto::getUsername))
                .containsExactly("testUser19", "testUser100");
    }
}
