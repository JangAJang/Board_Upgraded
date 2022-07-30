package com.example.board_upgrade.Dto;

import com.example.board_upgrade.Entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private int id;
    private String content;
    private String writer;

    public CommentDto toDto(Comment comment){
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                comment.getWriter().getName()
        );
    }
}
