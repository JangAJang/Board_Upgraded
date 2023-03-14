package com.board.Board_Upgraded.dto.post;

import com.board.Board_Upgraded.entity.post.Post;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponseDto {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime lastModifiedDate;

    @Builder
    @QueryProjection
    public PostResponseDto(Long id, String writer, String title, String content, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.lastModifiedDate = lastModifiedDate;
    }

    public static PostResponseDto toDto(Post post){
        return PostResponseDto.builder()
                .id(post.getId())
                .writer(post.getWritersName())
                .title(post.getTitle())
                .content(post.getContent()).build();
    }
}
