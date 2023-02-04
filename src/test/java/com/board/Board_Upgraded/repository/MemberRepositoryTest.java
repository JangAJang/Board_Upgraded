package com.board.Board_Upgraded.repository;

import com.board.Board_Upgraded.dto.member.ChangeNicknameRequestDto;
import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.dto.member.SearchMemberDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void initData(){
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("testUser")
                .nickname("test")
                .email("test@test.com")
                .password("password")
                .passwordCheck("password")
                .build();
        Member member = new Member(registerRequestDto);
        memberRepository.save(member);
        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("생성과 조회를 테스트")
    public void createAndReadTest() throws Exception{
        //given

        //when
        //then
        assertThat(memberRepository.findByUsername("testUser").get().getNickname()).isEqualTo("test");
    }

    @Test
    @DisplayName("수정시 변경감지로 쿼리가 나가고 조회해오면 변경이 반영되었는지 테스트")
    public void updateTest() throws Exception{
        //given
        //when
        memberRepository.findByUsername("testUser").get().changeNickname(new ChangeNicknameRequestDto("이건줄 몰랐지"));
        //then
        assertThat(memberRepository.findByUsername("testUser").get().getNickname()).isEqualTo("이건줄 몰랐지");
    }

    @Test
    @DisplayName("")
    public void deleteTest() throws Exception{
        //given

        //when
        Member member = memberRepository.findByUsername("testUser").get();
        //then
        memberRepository.delete(member);
        assertThat(memberRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("아이디로만 검색했을 때 검색한 멤버의 닉네임이 같은 번호로 나온다. ")
    public void searchByUsernameTest() throws Exception{
        //given
        createDatas();

        SearchMemberDto searchMemberDto = new SearchMemberDto("testUser3", null, null);

        //when
        List<Member> search = memberRepository.search(searchMemberDto);
        //then
        assertThat(search.get(0).getNickname()).isEqualTo("test3");
    }

    private void createDatas() {
        for(int index = 0; index < 10; index++){
            RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                    .username("testUser" + index)
                    .nickname("test" + index)
                    .email("test" + index + "@test.com")
                    .password("password")
                    .passwordCheck("password")
                    .build();
            Member member = new Member(registerRequestDto);
            memberRepository.save(member);
            em.flush();
            em.clear();
        }
    }

    @Test
    @DisplayName("닉네임으로만 검색했을 때 검색한 멤버의 아이디가 같은 번호로 나온다. ")
    public void searchByNickname() throws Exception{
        //given
        createDatas();

        SearchMemberDto searchMemberDto = new SearchMemberDto(null, "test3", null);

        //when
        List<Member> search = memberRepository.search(searchMemberDto);
        //then
        assertThat(search.get(0).getUsername()).isEqualTo("testUser3");
    }

    @Test
    @DisplayName("이메일로만 검색했을 때 검색한 멤버의 아이디가 같은 번호로 나온다. ")
    public void searchByEmail() throws Exception{
        //given
        createDatas();

        SearchMemberDto searchMemberDto = new SearchMemberDto(null, null, "test3@test.com");

        //when
        List<Member> search = memberRepository.search(searchMemberDto);
        //then
        assertThat(search.get(0).getUsername()).isEqualTo("testUser3");
    }

    @Test
    @DisplayName("아이디와 닉네임으로 검색하면 같은 번호의 이메일이 나온다.")
    public void searchByUsernameAndNickname() throws Exception{
        //given
        createDatas();

        SearchMemberDto searchMemberDto = new SearchMemberDto("testUser3", "test3", null);

        //when
        List<Member> search = memberRepository.search(searchMemberDto);
        //then
        assertThat(search.get(0).getEmail()).isEqualTo("test3@test.com");
    }

    @Test
    @DisplayName("아이디와 이메일로 검색하면 같은 번호의 닉네임이 나온다.")
    public void searchByUsernameAndEmail() throws Exception{
        //given
        createDatas();

        SearchMemberDto searchMemberDto = new SearchMemberDto("testUser3", null, "test3@test.com");

        //when
        List<Member> search = memberRepository.search(searchMemberDto);
        //then
        assertThat(search.get(0).getNickname()).isEqualTo("test3");
    }

    @Test
    @DisplayName("닉네임과 이메일로 검색하면 같은 번호의 아이디가 나온다.")
    public void searchByNicknameAndEmail() throws Exception{
        //given
        createDatas();

        SearchMemberDto searchMemberDto = new SearchMemberDto(null, "test3", "test3@test.com");

        //when
        List<Member> search = memberRepository.search(searchMemberDto);
        //then
        assertThat(search.get(0).getUsername()).isEqualTo("testUser3");
    }
}
