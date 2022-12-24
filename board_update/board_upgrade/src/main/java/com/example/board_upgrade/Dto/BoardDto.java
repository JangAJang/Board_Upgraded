package com.example.board_upgrade.Dto;

import com.example.board_upgrade.Entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    @NotNull
    private String title;
    @NotNull
    private String content;

    public static BoardDto toDto(Board board){
        return new BoardDto(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getUser().getName());
    }
}
