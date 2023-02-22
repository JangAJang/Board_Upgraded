package com.board.Board_Upgraded.service.post;

import com.board.Board_Upgraded.dto.post.EditPostRequestDto;
import com.board.Board_Upgraded.dto.post.PostResponseDto;
import com.board.Board_Upgraded.dto.post.SearchPostRequestDto;
import com.board.Board_Upgraded.dto.post.WritePostRequestDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.entity.post.Post;
import com.board.Board_Upgraded.exception.post.NotMyPostException;
import com.board.Board_Upgraded.exception.post.PostNotFoundException;
import com.board.Board_Upgraded.repository.member.SearchPostType;
import com.board.Board_Upgraded.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostResponseDto write(WritePostRequestDto writePostRequestDto , Member member){
        Post post = Post.builder()
                .title(writePostRequestDto.getTitle())
                .content(writePostRequestDto.getContent())
                .member(member).build();
        postRepository.save(post);
        return PostResponseDto.builder()
                .writer(member.getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .lastModifiedDate(post.getLastModifiedDate()).build();
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> search(SearchPostRequestDto searchPostRequestDto, Pageable pageable, SearchPostType searchPostType){
        return null;
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> getMembersPage(Long id, Pageable pageable){
        return null;
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> getMyPost(Member member){
        return null;
    }

    @Transactional
    public PostResponseDto edit(EditPostRequestDto editPostRequestDto, Member member, Long id){
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        if(!post.getMember().equals(member)) throw new NotMyPostException();
        if(StringUtils.hasText(editPostRequestDto.getTitle())) post.editTitle(editPostRequestDto.getTitle());
        if(StringUtils.hasText(editPostRequestDto.getContent())) post.editContent(editPostRequestDto.getContent());
        return PostResponseDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .writer(member.getNickname())
                .lastModifiedDate(post.getLastModifiedDate()).build();
    }

    @Transactional
    public void delete(Member member, Long id){

    }
}
