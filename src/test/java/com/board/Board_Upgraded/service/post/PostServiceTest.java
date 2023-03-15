package com.board.Board_Upgraded.service.post;

import com.board.Board_Upgraded.domain.member.Username;
import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.dto.post.EditPostRequestDto;
import com.board.Board_Upgraded.dto.post.PostResponseDto;
import com.board.Board_Upgraded.dto.post.WritePostRequestDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.entity.post.Post;
import com.board.Board_Upgraded.exception.member.MemberNotFoundException;
import com.board.Board_Upgraded.exception.post.NotMyPostException;
import com.board.Board_Upgraded.exception.post.PostNotFoundException;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import com.board.Board_Upgraded.repository.post.PostRepository;
import com.board.Board_Upgraded.service.auth.AuthService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void createWriter(){
        authService.registerNewMember(RegisterRequestDto.builder()
                .username("test")
                .nickname("test")
                .email("test@test.com")
                .password("test")
                .passwordCheck("test")
                .build());
    }

    @Test
    @DisplayName("게시물의 제목과 내용이 있으면 글을 작성해준다. ")
    public void writeSuccess() throws Exception{
        //given
        Member member = memberRepository.findByUsername(new Username("test")).orElseThrow(MemberNotFoundException::new);
        WritePostRequestDto writePostRequestDto = WritePostRequestDto.builder()
                .title("취업")
                .content("가즈아").build();
        //when
        PostResponseDto postResponseDto = postService.write(writePostRequestDto, member);
        //then
        Assertions.assertThat(postRepository.count()).isEqualTo(1L);
        Assertions.assertThat(postResponseDto.getTitle()).isEqualTo("취업");
        Assertions.assertThat(postResponseDto.getContent()).isEqualTo("가즈아");
        Assertions.assertThat(postResponseDto.getWriter()).isEqualTo("test");
    }

    @Test
    @DisplayName("게시물의 제목과 내용을 입력해 수정하면 제목과 내용이 바뀌고, 최종 수정일이 초기화된다. ")
    public void editSuccess() throws Exception{
        //given
        Member member = memberRepository.findByUsername(new Username("test")).orElseThrow(MemberNotFoundException::new);
        WritePostRequestDto writePostRequestDto = WritePostRequestDto.builder()
                .title("취업")
                .content("가즈아").build();
        PostResponseDto createResult = postService.write(writePostRequestDto, member);
        EditPostRequestDto editPostRequestDto = EditPostRequestDto.builder()
                .title("진짜 취업")
                .content("하고싶다.")
                .build();
        Post post = postRepository.findByTitle("취업").orElseThrow(PostNotFoundException::new);
        //when
        PostResponseDto postResponseDto = postService.edit(editPostRequestDto, member, post.getId());
        //then
        Assertions.assertThat(postResponseDto.getTitle()).isEqualTo("진짜 취업");
        Assertions.assertThat(postResponseDto.getContent()).isEqualTo("하고싶다.");
        Assertions.assertThat(postResponseDto.getWriter()).isEqualTo("test");
        Assertions.assertThat(postResponseDto.getLastModifiedDate()).isNotEqualTo(createResult.getLastModifiedDate());
    }

    @Test
    @DisplayName("게시물의 내용을 입력해 수정하면 내용이 바뀌고, 최종 수정일이 초기화된다. ")
    public void editSuccess_WithTitleNull() throws Exception{
        //given
        Member member = memberRepository.findByUsername(new Username("test")).orElseThrow(MemberNotFoundException::new);
        WritePostRequestDto writePostRequestDto = WritePostRequestDto.builder()
                .title("취업")
                .content("가즈아").build();
        PostResponseDto createResult = postService.write(writePostRequestDto, member);
        EditPostRequestDto editPostRequestDto = EditPostRequestDto.builder()
                .content("하고싶다.")
                .build();
        Post post = postRepository.findByTitle("취업").orElseThrow(PostNotFoundException::new);
        //when
        PostResponseDto postResponseDto = postService.edit(editPostRequestDto, member, post.getId());
        //then
        Assertions.assertThat(postResponseDto.getTitle()).isEqualTo("취업");
        Assertions.assertThat(postResponseDto.getContent()).isEqualTo("하고싶다.");
        Assertions.assertThat(postResponseDto.getWriter()).isEqualTo("test");
        Assertions.assertThat(postResponseDto.getLastModifiedDate()).isNotEqualTo(createResult.getLastModifiedDate());
    }

    @Test
    @DisplayName("제목을 입력해 수정하면 제목이 바뀌고, 최종 수정일이 초기화된다. ")
    public void editSuccess_WithContentNull() throws Exception{
        //given
        Member member = memberRepository.findByUsername(new Username("test")).orElseThrow(MemberNotFoundException::new);
        WritePostRequestDto writePostRequestDto = WritePostRequestDto.builder()
                .title("취업")
                .content("가즈아").build();
        PostResponseDto createResult = postService.write(writePostRequestDto, member);
        EditPostRequestDto editPostRequestDto = EditPostRequestDto.builder()
                .title("인턴")
                .build();
        Post post = postRepository.findByTitle("취업").orElseThrow(PostNotFoundException::new);
        //when
        PostResponseDto postResponseDto = postService.edit(editPostRequestDto, member, post.getId());
        //then
        Assertions.assertThat(postResponseDto.getTitle()).isEqualTo("인턴");
        Assertions.assertThat(postResponseDto.getContent()).isEqualTo("가즈아");
        Assertions.assertThat(postResponseDto.getWriter()).isEqualTo("test");
        Assertions.assertThat(postResponseDto.getLastModifiedDate()).isNotEqualTo(createResult.getLastModifiedDate());
    }

    @Test
    @DisplayName("다른 사람이 작성한 글을 수정하려고 하면 예외처리한다. ")
    public void editFail_NotMyPost() throws Exception{
        //given
        Member member = memberRepository.findByUsername(new Username("test")).orElseThrow(MemberNotFoundException::new);
        WritePostRequestDto writePostRequestDto = WritePostRequestDto.builder()
                .title("취업")
                .content("가즈아").build();
        authService.registerNewMember(
                RegisterRequestDto.builder()
                        .username("user")
                        .nickname("nick")
                        .email("em@em.com")
                        .password("pass")
                        .passwordCheck("pass").build());
        Member other = memberRepository.findByUsername(new Username("test")).orElseThrow(MemberNotFoundException::new);
        postService.write(writePostRequestDto, member);
        EditPostRequestDto editPostRequestDto = EditPostRequestDto.builder()
                .title("인턴")
                .build();
        Post post = postRepository.findByTitle("취업").orElseThrow(PostNotFoundException::new);
        //when

        //then
        Assertions.assertThatThrownBy(()-> postService.edit(editPostRequestDto, other, post.getId()))
                .isInstanceOf(NotMyPostException.class);
    }

    @Test
    @DisplayName("게시물이 존재하고 회원이 작성자 일 때 게시물을 삭제한다.")
    public void deleteSuccess() throws Exception{
        //given
        Member member = memberRepository.findByUsername(new Username("test")).orElseThrow(MemberNotFoundException::new);
        WritePostRequestDto writePostRequestDto = WritePostRequestDto.builder()
                .title("취업")
                .content("가즈아").build();
        PostResponseDto createResult = postService.write(writePostRequestDto, member);
        Post post = postRepository.findByTitle("취업").orElseThrow(PostNotFoundException::new);
        //when
        postService.delete(member, post.getId());
        //then
        Assertions.assertThat(postRepository.count()).isEqualTo(0L);
    }

    @Test
    @DisplayName("게시물이 존재하지 않으면 예외처리시킨다.")
    public void deleteFail_PostNotFound() throws Exception{
        //given
        Member member = memberRepository.findByUsername(new Username("test")).orElseThrow(MemberNotFoundException::new);
        WritePostRequestDto writePostRequestDto = WritePostRequestDto.builder()
                .title("취업")
                .content("가즈아").build();
        PostResponseDto createResult = postService.write(writePostRequestDto, member);
        Post post = postRepository.findByTitle("취업").orElseThrow(PostNotFoundException::new);
        //when

        //then
        Assertions.assertThatThrownBy(()-> postService.delete(member, post.getId()+1))
                .isInstanceOf(PostNotFoundException.class);
    }

    @Test
    @DisplayName("게시물의 작성자가 아니면 예외처리시킨다.")
    public void deleteFail_NotMyPost() throws Exception{
        //given
        Member member = memberRepository.findByUsername(new Username("test")).orElseThrow(MemberNotFoundException::new);
        WritePostRequestDto writePostRequestDto = WritePostRequestDto.builder()
                .title("취업")
                .content("가즈아").build();
        PostResponseDto createResult = postService.write(writePostRequestDto, member);
        Post post = postRepository.findByTitle("취업").orElseThrow(PostNotFoundException::new);
        authService.registerNewMember(
                RegisterRequestDto.builder()
                        .username("user")
                        .nickname("nick")
                        .email("em@em.com")
                        .password("pass")
                        .passwordCheck("pass").build());
        Member other = memberRepository.findByUsername(new Username("test")).orElseThrow(MemberNotFoundException::new);
        //when

        //then
        Assertions.assertThatThrownBy(()-> postService.delete(other, post.getId()))
                .isInstanceOf(NotMyPostException.class);
    }
}
