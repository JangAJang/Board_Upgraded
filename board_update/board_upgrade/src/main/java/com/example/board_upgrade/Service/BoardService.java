package com.example.board_upgrade.Service;

import com.example.board_upgrade.Dto.BoardDto;
import com.example.board_upgrade.Entity.Board;
import com.example.board_upgrade.Entity.User;
import com.example.board_upgrade.Repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public List<BoardDto> getBoards(){
        List<Board> boards = boardRepository.findAll();
        List<BoardDto> boardDtos = new ArrayList<>();
        boards.forEach(s -> boardDtos.add(BoardDto.toDto(s)));
        return boardDtos;
    }

    @Transactional(readOnly = true)
    public BoardDto getBoard(int id){
        Board board = boardRepository.findById(id).orElseThrow(()-> {
            return new IllegalArgumentException("Board ID를 찾을 수 없습니다. ");
        });
        BoardDto boardDto = BoardDto.toDto(board);
        return boardDto;

    }

    @Transactional
    public BoardDto write(BoardDto boardDto, User user){
        Board board = new Board();
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        board.setUser(user);
        return BoardDto.toDto(board);
    }

    @Transactional
    public BoardDto update(int id, BoardDto boardDto){
        Board board = boardRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("해당 게시물 ID를 찾을 수 없습니다. ");
        });
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        return BoardDto.toDto(board);
    }

    @Transactional
    public void delete(int id){
        Board board = boardRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("해당 게시물의 ID를 찾을 수 없습니다. ");
        });
        boardRepository.deleteById(id);
    }
}
