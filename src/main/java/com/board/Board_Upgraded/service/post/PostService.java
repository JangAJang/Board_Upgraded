package com.board.Board_Upgraded.service.post;

import com.board.Board_Upgraded.dto.post.EditPostRequestDto;
import com.board.Board_Upgraded.dto.post.PostResponseDto;
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
    public Page<PostResponseDto> search(String text, Pageable pageable, SearchPostType searchPostType){
        return postRepository.searchPost(text, searchPostType, pageable);
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> getMembersPage(Long id, Pageable pageable){
        return postRepository.getMembersPost(id, pageable);
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> getMyPost(Member member, Pageable pageable){
        return postRepository.getMembersPost(member.getId(), pageable);
    }

    @Transactional
    public PostResponseDto edit(EditPostRequestDto editPostRequestDto, Member member, Long id){
        Post post = findPostById(id);
        validateMember(member, post);
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
        Post post = findPostById(id);
        validateMember(member, post);
        postRepository.delete(post);
    }

    private Post findPostById(Long id){
        return postRepository.findById(id).orElseThrow(PostNotFoundException::new);
    }

    private void validateMember(Member member, Post post){
        if(!post.getMember().equals(member)) throw new NotMyPostException();
    }
}