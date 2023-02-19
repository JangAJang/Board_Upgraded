package com.board.Board_Upgraded.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SearchPostRequestDto {

    private final String title;
    private final String content;
    private final String writer;
}
