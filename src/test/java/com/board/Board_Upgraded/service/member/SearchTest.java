package com.board.Board_Upgraded.service.member;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.dto.member.SearchMemberDto;
import com.board.Board_Upgraded.exception.member.NeedToAddSearchConditionException;
import com.board.Board_Upgraded.service.AuthService;
import com.board.Board_Upgraded.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class SearchTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private AuthService authService;

    private static final PageRequest page = PageRequest.of(0, 3);

    @BeforeEach
    void createData() {
        IntStream.range(1, 31)
                .forEach( i ->
                        authService.registerNewMember(
                                RegisterRequestDto.builder()
                                        .username("testUser"+i)
                                        .nickname("test"+i)
                                        .email("test"+i+"@test.com")
                                        .password("test")
                                        .passwordCheck("test").build()));
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
        assertThat(search.getContent().size()).isEqualTo(2);
        assertThat(search.getContent().stream().map(SearchMemberDto::getNickname))
                .containsExactly("test3", "test30");
    }

    @Test
    @DisplayName("닉네임으로만 검색했을 때 검색한 멤버의 아이디가 같은 번호로 나온다. ")
    public void searchByNickname() throws Exception{
        //given
        SearchMemberDto searchMemberDto = new SearchMemberDto(null, "test3", null);
        //when
        Page<SearchMemberDto> search = memberService.search(searchMemberDto, page);
        //then
        assertThat(search.getContent().size()).isEqualTo(2);
        assertThat(search.getContent().stream().map(SearchMemberDto::getUsername))
                .containsExactly("testUser3", "testUser30");
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
    @DisplayName("아이디와 닉네임으로 검색하면 3, 30이 나온다. ")
    public void searchByUsernameAndNickname() throws Exception{
        //given
        SearchMemberDto searchMemberDto = new SearchMemberDto("testUser3", "test3", null);
        //when
        Page<SearchMemberDto> search = memberService.search(searchMemberDto, page);
        //then
        assertThat(search.getContent().size()).isEqualTo(2);
        assertThat(search.getContent().stream().map(SearchMemberDto::getEmail))
                .containsExactly("test3@test.com", "test30@test.com");
    }

    @Test
    @DisplayName("아이디와 이메일로 검색하면 3, 30이 나온다. ")
    public void searchByUsernameAndEmail() throws Exception{
        //given

        SearchMemberDto searchMemberDto = new SearchMemberDto("testUser3", null, "test3@test.com");
        //when
        Page<SearchMemberDto> search = memberService.search(searchMemberDto, page);
        //then
        assertThat(search.getContent().size()).isEqualTo(2);
        assertThat(search.getContent().stream().map(SearchMemberDto::getNickname))
                .containsExactly("test3", "test30");
    }

    @Test
    @DisplayName("3의 닉네임과 이메일로 검색하면 3, 30이 나온다. ")
    public void searchByNicknameAndEmail() throws Exception{
        //given
        SearchMemberDto searchMemberDto = new SearchMemberDto(null, "test3", "test3@test.com");
        //when
        Page<SearchMemberDto> search = memberService.search(searchMemberDto, page);
        //then
        assertThat(search.getContent().size()).isEqualTo(2);
        assertThat(search.getContent().stream().map(SearchMemberDto::getUsername))
                .containsExactly("testUser3", "testUser30");
    }

    @Test
    @DisplayName("3의 모든 값으로 검색하면 3, 30번이 나온다.")
    public void searchByAll() throws Exception{
        //given
        SearchMemberDto searchMemberDto = new SearchMemberDto("testUser3", "test3", "test3@test.com");
        //when
        Page<SearchMemberDto> search = memberService.search(searchMemberDto, page);
        //then
        assertThat(search.getContent().size()).isEqualTo(2);
        assertThat(search.getContent().stream().map(SearchMemberDto::getUsername))
                .containsExactly("testUser3", "testUser30");
        assertThat(search.getContent().stream().map(SearchMemberDto::getNickname))
                .containsExactly("test3", "test30");
        assertThat(search.getContent().stream().map(SearchMemberDto::getEmail))
                .containsExactly("test3@test.com", "test30@test.com");
    }
}
