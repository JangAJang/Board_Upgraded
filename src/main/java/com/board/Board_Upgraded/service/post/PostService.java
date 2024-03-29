package com.board.Board_Upgraded.service.post;

import com.board.Board_Upgraded.domain.post.Content;
import com.board.Board_Upgraded.domain.post.Title;
import com.board.Board_Upgraded.dto.post.EditPostRequestDto;
import com.board.Board_Upgraded.dto.post.PostResponseDto;
import com.board.Board_Upgraded.dto.post.SearchPostRequestDto;
import com.board.Board_Upgraded.dto.post.WritePostRequestDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.entity.post.Post;
import com.board.Board_Upgraded.exception.post.MembersPostNotFoundException;
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
                .title(new Title(writePostRequestDto.getTitle()))
                .content(new Content(writePostRequestDto.getContent()))
                .member(member).build();
        postRepository.save(post);
        return PostResponseDto.toDto(post);
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> search(SearchPostRequestDto searchPostRequestDto, Pageable pageable, SearchPostType searchPostType){
        return postRepository.searchPost(searchPostRequestDto, searchPostType, pageable);
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> getMembersPage(Long id, Pageable pageable){
        Page<PostResponseDto> result = postRepository.getMembersPost(id, pageable);
        if(result.isEmpty()) throw new MembersPostNotFoundException();
        return result;
    }

    @Transactional
    public PostResponseDto edit(EditPostRequestDto editPostRequestDto, Member member, Long id){
        Post post = findPostById(id);
        validateMember(member, post);
        if(StringUtils.hasText(editPostRequestDto.getTitle())) post.editTitle(new Title(editPostRequestDto.getTitle()));
        if(StringUtils.hasText(editPostRequestDto.getContent())) post.editContent(new Content(editPostRequestDto.getContent()));
        return PostResponseDto.toDto(post);
    }

    @Transactional
    public void delete(Member member, Long id){
        Post post = findPostById(id);
        validateMember(member, post);
        member.deletePost(post);
        postRepository.delete(post);
    }

    private Post findPostById(Long id){
        return postRepository.findById(id).orElseThrow(PostNotFoundException::new);
    }

    private void validateMember(Member member, Post post){
        if(!post.isWriter(member)) throw new NotMyPostException();
    }
}
