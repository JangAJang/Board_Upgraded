package com.example.board_upgrade.Controller;

import com.example.board_upgrade.Dto.BoardDto;
import com.example.board_upgrade.Entity.User;
import com.example.board_upgrade.Repository.UserRepository;
import com.example.board_upgrade.Response.Response;
import com.example.board_upgrade.Service.BoardService;
import com.example.board_upgrade.auth.PrincipalDetails;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;
    private final UserRepository userRepository;

    @ApiOperation(value = "전체 게시글 보기", notes = "전체 게시글을 조회합니다. ")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/board")
    public Response getBoards(){
        return new Response("성공", "전체 게시글 리턴", boardService.getBoards());
    }

    @ApiOperation(value = "게시글 보기", notes = "해당 게시글을 조회합니다. ")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/board/{id}")
    public Response getBoard(@PathVariable("id") Integer id){
        return new Response("성공", "전체 게시글 리턴", boardService.getBoard(id));
    }

    @ApiOperation(value = "게시글 작성", notes = "새로운 게시글을 작성합니다. ")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/board/write")
    public Response write(@RequestBody BoardDto boardDto, Authentication authentication){
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetails.getUser();
        return new Response("성공", "글 작성 완료", boardService.write(boardDto, user));
    }

    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정합니다. ")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/board/update/{id}")
    public Response update(@RequestBody BoardDto boardDto, @PathVariable("id") Integer id, Authentication authentication){
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetails.getUser();
        if(user.getName().equals(boardService.getBoard(id).getWriter())){
            return new Response("성공", "글 수정 완료", boardService.update(id, boardDto));
        }
        else return new Response("실패", "본인의 게시물만 수정할 수 있습니다. ", null);

    }

    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제합니다. ")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/board/delete/{id}")
    public Response delete(@PathVariable("id") Integer id, Authentication authentication){
        PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
        User user = principalDetails.getUser();
        if(user.getName().equals(boardService.getBoard(id).getWriter())){
            boardService.delete(id);
            return new Response("성공", "글 삭제 완료", null);
        }
        else return new Response("실패", "본인의 게시물만 삭제할 수 있습니다. ", null);

    }


}
