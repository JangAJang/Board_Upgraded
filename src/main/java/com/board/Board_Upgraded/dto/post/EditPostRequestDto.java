package com.board.Board_Upgraded.dto.post;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class EditPostRequestDto {

    private String title;
    private String content;

    @Builder
    public EditPostRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
