package com.board.Board_Upgraded.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostResponseDto {

    private final String title;
    private final String content;
    private final LocalDateTime lastModifiedDate;
}
