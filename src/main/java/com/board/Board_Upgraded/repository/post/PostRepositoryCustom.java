package com.board.Board_Upgraded.repository.post;

import com.board.Board_Upgraded.dto.post.PostResponseDto;
import com.board.Board_Upgraded.dto.post.SearchPostRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

    Page<PostResponseDto> searchPost(SearchPostRequestDto searchPostRequestDto, Pageable pageable);
}
