package com.board.Board_Upgraded.dto.post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SearchPostRequestDto {

    @NotNull(message = "검색할 내용을 입력해주세요.")
    @NotBlank(message = "검색할 내용을 입력해주세요.")
    @NotEmpty(message = "검색할 내용을 입력해주세요.")
    private String text;

    public SearchPostRequestDto(String text) {
        this.text = text;
    }
}
