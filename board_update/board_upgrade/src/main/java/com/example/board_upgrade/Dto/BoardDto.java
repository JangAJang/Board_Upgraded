package com.example.board_upgrade.Dto;

import com.example.board_upgrade.Entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private int id;
    private String title;
    private String content;
    private String writer;

    public static BoardDto toDto(Board board){
        return new BoardDto(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getUser().getName());
    }
}
