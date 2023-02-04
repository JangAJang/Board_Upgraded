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

        SearchMemberDto searchMemberDto = new SearchMemberDto("testUser3", null, null);

        //when
        List<Member> search = memberRepository.search(searchMemberDto);
        //then
        assertThat(search.get(0).getNickname()).isEqualTo("test3");
    }
}
