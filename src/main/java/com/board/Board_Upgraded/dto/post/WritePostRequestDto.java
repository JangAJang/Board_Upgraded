package com.board.Board_Upgraded.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class WritePostRequestDto {

    private final String title;
    private final String content;
}
