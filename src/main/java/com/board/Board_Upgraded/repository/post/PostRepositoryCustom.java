package com.board.Board_Upgraded.repository.post;

import com.board.Board_Upgraded.dto.post.PostResponseDto;
import com.board.Board_Upgraded.dto.post.SearchPostRequestDto;
import com.board.Board_Upgraded.repository.member.SearchPostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

    Page<PostResponseDto> searchPost(SearchPostRequestDto searchPostRequestDto, SearchPostType searchPostType, Pageable pageable);

    Page<PostResponseDto> getMembersPost(Long id, Pageable pageable);
}
