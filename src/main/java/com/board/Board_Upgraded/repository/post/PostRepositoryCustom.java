package com.board.Board_Upgraded.repository.post;

import com.board.Board_Upgraded.dto.post.PostResponseDto;
import org.springframework.data.domain.Page;

public interface PostRepositoryCustom {

    Page<PostResponseDto> searchPost(SearchPostRequestDto searchPostRequestDto);
}
