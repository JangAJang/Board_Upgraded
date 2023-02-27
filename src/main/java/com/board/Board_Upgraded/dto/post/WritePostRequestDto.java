package com.board.Board_Upgraded.dto.post;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class WritePostRequestDto {

    @NotNull(message = "제목을 입력하세요.")
    @NotEmpty(message = "제목을 입력하세요.")
    @NotBlank(message = "제목을 입력하세요.")
    private String title;
    @NotNull(message = "내용을 입력하세요.")
    @NotEmpty(message = "내용을 입력하세요.")
    @NotBlank(message = "내용을 입력하세요.")
    private String content;

    @Builder
    public WritePostRequestDto(String title, String content){
        this.title = title;
        this.content = content;
    }
}
