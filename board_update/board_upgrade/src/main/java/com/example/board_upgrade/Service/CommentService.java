package com.example.board_upgrade.Service;


import com.example.board_upgrade.Dto.CommentDto;
import com.example.board_upgrade.Entity.Board;
import com.example.board_upgrade.Entity.Comment;
import com.example.board_upgrade.Entity.User;
import com.example.board_upgrade.Repository.BoardRepository;
import com.example.board_upgrade.Repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public CommentDto writeComment(int boardId, CommentDto commentDto, User user){
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        Board board = boardRepository.findById(boardId).orElseThrow(()->{
            return new IllegalArgumentException("게시글을 찾을 수 없습니다. ");
        });
        comment.setWriter(user);
        comment.setBoard(board);
        commentRepository.save(comment);
        return commentDto.toDto(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getComments(int boardId){
        List<Comment> comments = commentRepository.findAllByBoardId(boardId);
        List<CommentDto> commentDtos = new ArrayList<>();
        comments.forEach(s-> commentDtos.add(new CommentDto().toDto(s)));
        return commentDtos;
    }

    @Transactional(readOnly = true)
    public Comment getComment(int commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->{
            return new IllegalArgumentException("댓글을 찾을 수 없습니다. ");
        });
        return comment;
    }

    @Transactional
    public String deleteComment(int commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->{
            return new IllegalArgumentException("댓글을 찾을 수 없습니다. ");
        });
        commentRepository.deleteById(commentId);
        return "삭제완료";
    }
}
