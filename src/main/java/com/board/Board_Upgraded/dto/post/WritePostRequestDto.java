package com.board.Board_Upgraded.dto.post;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class WritePostRequestDto {

    private String title;
    private String content;

    @Builder
    public WritePostRequestDto(String title, String content){
        this.title = title;
        this.content = content;
    }
}
