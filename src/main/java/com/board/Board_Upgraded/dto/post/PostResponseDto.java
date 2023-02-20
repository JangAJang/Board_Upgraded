package com.board.Board_Upgraded.dto.post;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponseDto {

    private String writer;
    private String title;
    private String content;
    private LocalDateTime lastModifiedDate;

    @Builder
    @QueryProjection
    public PostResponseDto(String writer, String title, String content, LocalDateTime lastModifiedDate) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.lastModifiedDate = lastModifiedDate;
    }
}
