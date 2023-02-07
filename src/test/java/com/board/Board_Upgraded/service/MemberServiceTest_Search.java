package com.board.Board_Upgraded.service;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.dto.member.SearchMemberDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberServiceTest_Search {

    @Autowired
    private MemberService memberService;

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
    @DisplayName("아이디로만 검색했을 때 검색한 멤버의 닉네임이 같은 번호로 나온다. ")
    public void searchByUsernameTest() throws Exception{
        //given
        SearchMemberDto searchMemberDto = new SearchMemberDto("testUser3", null, null);

        //when
        List<SearchMemberDto> search = memberService.search(searchMemberDto);
        //then
        assertThat(search.get(0).getNickname()).isEqualTo("test3");
    }

    @Test
    @DisplayName("닉네임으로만 검색했을 때 검색한 멤버의 아이디가 같은 번호로 나온다. ")
    public void searchByNickname() throws Exception{
        //given

        SearchMemberDto searchMemberDto = new SearchMemberDto(null, "test3", null);

        //when
        List<SearchMemberDto> search = memberService.search(searchMemberDto);
        //then
        assertThat(search.get(0).getUsername()).isEqualTo("testUser3");
    }

    @Test
    @DisplayName("이메일로만 검색했을 때 검색한 멤버의 아이디가 같은 번호로 나온다. ")
    public void searchByEmail() throws Exception{
        //given

        SearchMemberDto searchMemberDto = new SearchMemberDto(null, null, "test3@test.com");

        //when
        List<SearchMemberDto> search = memberService.search(searchMemberDto);
        //then
        assertThat(search.get(0).getUsername()).isEqualTo("testUser3");
    }

    @Test
    @DisplayName("아이디와 닉네임으로 검색하면 같은 번호의 이메일이 나온다.")
    public void searchByUsernameAndNickname() throws Exception{
        //given

        SearchMemberDto searchMemberDto = new SearchMemberDto("testUser3", "test3", null);

        //when
        List<SearchMemberDto> search = memberService.search(searchMemberDto);
        //then
        assertThat(search.get(0).getEmail()).isEqualTo("test3@test.com");
    }

    @Test
    @DisplayName("아이디와 이메일로 검색하면 같은 번호의 닉네임이 나온다.")
    public void searchByUsernameAndEmail() throws Exception{
        //given

        SearchMemberDto searchMemberDto = new SearchMemberDto("testUser3", null, "test3@test.com");

        //when
        List<SearchMemberDto> search = memberService.search(searchMemberDto);
        //then
        assertThat(search.get(0).getNickname()).isEqualTo("test3");
    }

    @Test
    @DisplayName("닉네임과 이메일로 검색하면 같은 번호의 아이디가 나온다.")
    public void searchByNicknameAndEmail() throws Exception{
        //given

        SearchMemberDto searchMemberDto = new SearchMemberDto(null, "test3", "test3@test.com");

        //when
        List<SearchMemberDto> search = memberService.search(searchMemberDto);
        //then
        assertThat(search.get(0).getUsername()).isEqualTo("testUser3");
    }

    @Test
    @DisplayName("모든 요소로 검색하면 같은 번호의 비밀번호가 나온다. ")
    public void searchByAll() throws Exception{
        //given
        SearchMemberDto searchMemberDto = new SearchMemberDto("testUser3", "test3", "test3@test.com");

        //when
        List<SearchMemberDto> search = memberService.search(searchMemberDto);

        //then
        assertThat(search.get(0).getUsername()).isEqualTo("testUser3");
        assertThat(search.get(0).getNickname()).isEqualTo("test3");
        assertThat(search.get(0).getEmail()).isEqualTo("test3@test.com");
    }
}
