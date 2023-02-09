package com.board.Board_Upgraded.service;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.dto.member.SearchMemberDto;
import com.board.Board_Upgraded.exception.member.NeedToAddSearchConditionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberServiceTest_Search {

    @Autowired
    private MemberService memberService;

    private final PageRequest page = PageRequest.of(0, 3);

    @BeforeEach
    void createData() {
        for(int index = 0; index < 10; index++){
            RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                    .username("testUser" + index)
                    .nickname("test" + index)
                    .email("test" + index + "@test.com")
                    .password("password" + index)
                    .passwordCheck("password" + index)
                    .build();
            memberService.registerNewMember(registerRequestDto);
        }
    }

    @Test
    @DisplayName("전부 다 null값으로 검색하면 예외처리된다. ")
    public void searchWithAllNull() throws Exception{
        //given

        //when
        SearchMemberDto searchMemberDto = new SearchMemberDto(null, null, null);
        //then
        assertThatThrownBy(()-> memberService.search(searchMemberDto, page))
                .isInstanceOf(NeedToAddSearchConditionException.class);
    }

    @Test
    @DisplayName("아이디로만 검색했을 때 검색한 멤버의 닉네임이 같은 번호로 나온다. ")
    public void searchByUsernameTest() throws Exception{
        //given
        SearchMemberDto searchMemberDto = new SearchMemberDto("testUser3", null, null);

        //when
        Page<SearchMemberDto> search = memberService.search(searchMemberDto, page);
        //then
        assertThat(search.getContent().size()).isEqualTo(1);
        assertThat(search.getContent().stream().map(SearchMemberDto::getNickname))
                .containsExactly("test3");
    }

    @Test
    @DisplayName("닉네임으로만 검색했을 때 검색한 멤버의 아이디가 같은 번호로 나온다. ")
    public void searchByNickname() throws Exception{
        //given
        SearchMemberDto searchMemberDto = new SearchMemberDto(null, "test3", null);
        //when
        Page<SearchMemberDto> search = memberService.search(searchMemberDto, page);
        //then
        assertThat(search.getContent().size()).isEqualTo(1);
        assertThat(search.getContent().stream().map(SearchMemberDto::getUsername))
                .containsExactly("testUser3");
    }

    @Test
    @DisplayName("이메일로만 검색했을 때 검색한 멤버의 아이디가 같은 번호로 나온다. ")
    public void searchByEmail() throws Exception{
        //given
        SearchMemberDto searchMemberDto = new SearchMemberDto(null, null, "test3@test.com");
        //when
        Page<SearchMemberDto> search = memberService.search(searchMemberDto, page);
        //then
        assertThat(search.getContent().size()).isEqualTo(1);
        assertThat(search.getContent().stream().map(SearchMemberDto::getUsername))
                .containsExactly("testUser3");
    }

    @Test
    @DisplayName("아이디와 닉네임으로 검색하면 같은 번호의 이메일이 나온다.")
    public void searchByUsernameAndNickname() throws Exception{
        //given
        SearchMemberDto searchMemberDto = new SearchMemberDto("testUser3", "test3", null);
        //when
        Page<SearchMemberDto> search = memberService.search(searchMemberDto, page);
        //then
        assertThat(search.getContent().size()).isEqualTo(1);
        assertThat(search.getContent().stream().map(SearchMemberDto::getEmail))
                .containsExactly("test3@test.com");
    }

    @Test
    @DisplayName("아이디와 이메일로 검색하면 같은 번호의 닉네임이 나온다.")
    public void searchByUsernameAndEmail() throws Exception{
        //given

        SearchMemberDto searchMemberDto = new SearchMemberDto("testUser3", null, "test3@test.com");
        //when
        Page<SearchMemberDto> search = memberService.search(searchMemberDto, page);
        //then
        assertThat(search.getContent().size()).isEqualTo(1);
        assertThat(search.getContent().stream().map(SearchMemberDto::getNickname))
                .containsExactly("test3");
    }

    @Test
    @DisplayName("닉네임과 이메일로 검색하면 같은 번호의 아이디가 나온다.")
    public void searchByNicknameAndEmail() throws Exception{
        //given
        SearchMemberDto searchMemberDto = new SearchMemberDto(null, "test3", "test3@test.com");
        //when
        Page<SearchMemberDto> search = memberService.search(searchMemberDto, page);
        //then
        assertThat(search.getContent().size()).isEqualTo(1);
        assertThat(search.getContent().stream().map(SearchMemberDto::getUsername))
                .containsExactly("testUser3");
    }

    @Test
    @DisplayName("모든 요소로 검색하면 같은 번호의 정보가 나온다. ")
    public void searchByAll() throws Exception{
        //given
        SearchMemberDto searchMemberDto = new SearchMemberDto("testUser3", "test3", "test3@test.com");
        //when
        Page<SearchMemberDto> search = memberService.search(searchMemberDto, page);
        //then
        assertThat(search.getContent().size()).isEqualTo(1);
        assertThat(search.getContent().stream().map(SearchMemberDto::getUsername))
                .containsExactly("testUser3");
        assertThat(search.getContent().stream().map(SearchMemberDto::getNickname))
                .containsExactly("test3");
        assertThat(search.getContent().stream().map(SearchMemberDto::getEmail))
                .containsExactly("test3@test.com");
    }
}
