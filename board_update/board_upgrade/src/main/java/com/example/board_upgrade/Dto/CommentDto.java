package com.example.board_upgrade.Dto;

import com.example.board_upgrade.Entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    @NotNull
    private int id;
    @NotNull
    private String content;
    @NotNull
    private String writer;

    public CommentDto toDto(Comment comment){
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                comment.getWriter().getName()
        );
    }
}
