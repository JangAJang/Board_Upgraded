package com.board.Board_Upgraded.dto.post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SearchPostRequestDto {

    private String text;

    public SearchPostRequestDto(String text) {
        this.text = text;
    }
}
