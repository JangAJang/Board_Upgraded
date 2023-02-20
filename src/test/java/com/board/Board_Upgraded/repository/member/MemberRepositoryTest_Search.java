package com.board.Board_Upgraded.repository.member;

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
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberRepositoryTest_Search {


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
    @DisplayName("testUser1로 검색하면, 1, 10, 11, 12, 13, 14, 15, 16, 17, 18이 1페이지에 나온다. 19, 100은 2페이지에 나온다")
    public void searchWith_testUser1() throws Exception{
        //given
        SearchMemberDto searchMemberDto = new SearchMemberDto("testUser1", null, null);
        PageRequest pageRequest1 = PageRequest.of(0, 10);
        PageRequest pageRequest2 = PageRequest.of(1, 10);
        //when
        Page<SearchMemberDto> result1 = memberRepository.search(searchMemberDto, pageRequest1);
        Page<SearchMemberDto> result2 = memberRepository.search(searchMemberDto, pageRequest2);
        System.out.println("result1 : " + result1.getContent().toString());
        //then
        assertThat(result1.getContent().stream().map(SearchMemberDto::getUsername))
                .containsExactly("testUser1", "testUser10", "testUser11", "testUser12", "testUser13",
                        "testUser14", "testUser15", "testUser16", "testUser17", "testUser18");
        assertThat(result2.getContent().stream().map(SearchMemberDto::getUsername))
                .containsExactly("testUser19", "testUser100");
    }

    @Test
    @DisplayName("test1으로 검색하면 위와 같은 아이디가 나온다. ")
    public void searchWith_test1() throws Exception{
        //given
        SearchMemberDto searchMemberDto = new SearchMemberDto(null, "test1", null);
        PageRequest pageRequest1 = PageRequest.of(0, 10);
        PageRequest pageRequest2 = PageRequest.of(1, 10);
        //when
        Page<SearchMemberDto> result1 = memberRepository.search(searchMemberDto, pageRequest1);
        Page<SearchMemberDto> result2 = memberRepository.search(searchMemberDto, pageRequest2);
        System.out.println("result1 : " + result1.getContent().toString());
        //then
        assertThat(result1.getContent().stream().map(SearchMemberDto::getUsername))
                .containsExactly("testUser1", "testUser10", "testUser11", "testUser12", "testUser13",
                        "testUser14", "testUser15", "testUser16", "testUser17", "testUser18");
        assertThat(result2.getContent().stream().map(SearchMemberDto::getUsername))
                .containsExactly("testUser19", "testUser100");
    }
    @Test
    @DisplayName("test1을 이메일로 검색하면 위와 같은 아이디가 나온다. ")
    public void searchWith_test1Email() throws Exception{
        //given
        SearchMemberDto searchMemberDto = new SearchMemberDto(null, null, "test1");
        PageRequest pageRequest1 = PageRequest.of(0, 10);
        PageRequest pageRequest2 = PageRequest.of(1, 10);
        //when
        Page<SearchMemberDto> result1 = memberRepository.search(searchMemberDto, pageRequest1);
        Page<SearchMemberDto> result2 = memberRepository.search(searchMemberDto, pageRequest2);
        System.out.println("result1 : " + result1.getContent().toString());
        //then
        assertThat(result1.getContent().stream().map(SearchMemberDto::getUsername))
                .containsExactly("testUser1", "testUser10", "testUser11", "testUser12", "testUser13",
                        "testUser14", "testUser15", "testUser16", "testUser17", "testUser18");
        assertThat(result2.getContent().stream().map(SearchMemberDto::getUsername))
                .containsExactly("testUser19", "testUser100");
    }

    @Test
    @DisplayName("test를 이메일으로 검색하면 전체가 조회되고, 첫 페이지에는 1~10이 나온다. ")
    public void searchWith_testEmail() throws Exception{
        //given
        SearchMemberDto searchMemberDto = new SearchMemberDto(null, null, "test");
        PageRequest pageRequest1 = PageRequest.of(0, 10);
        PageRequest pageRequest2 = PageRequest.of(1, 10);
        //when
        Page<SearchMemberDto> result1 = memberRepository.search(searchMemberDto, pageRequest1);
        Page<SearchMemberDto> result2 = memberRepository.search(searchMemberDto, pageRequest2);
        System.out.println("result1 : " + result1.getContent().toString());
        //then
        assertThat(result1.getTotalElements()).isEqualTo(100);
        assertThat(result1.getContent().stream().map(SearchMemberDto::getNickname))
                .containsExactly("test1", "test2", "test3", "test4", "test5",
                        "test6", "test7", "test8", "test9", "test10");
    }

    @Test
    @DisplayName("")
    public void 서로_다른_객체를_요구하며_검색하기() throws Exception{
        //given
        SearchMemberDto searchMemberDto = new SearchMemberDto("11", null, "22");
        PageRequest pageRequest = PageRequest.of(0, 10);
        //when
        Page<SearchMemberDto> result = memberRepository.search(searchMemberDto, pageRequest);
        //then
        assertThat(result.getTotalElements()).isEqualTo(2L);
        assertThat(result.getContent().stream().map(SearchMemberDto::getNickname))
                .containsExactly("test11", "test22");
    }

    @Test
    @DisplayName("")
    public void 서로_값을_넣었을때_포함값을_가지며_다른_입력에_대한_쿼리가_or로_처리되는지() throws Exception{
        //given
        SearchMemberDto searchMemberDto = new SearchMemberDto("1", null, "2");
        PageRequest pageRequest = PageRequest.of(0, 10);
        //when
        Page<SearchMemberDto> result = memberRepository.search(searchMemberDto, pageRequest);
        //then
        assertThat(result.getTotalElements()).isEqualTo(37L);
    }
}
