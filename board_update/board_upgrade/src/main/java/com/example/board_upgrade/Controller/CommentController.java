package com.example.board_upgrade.Controller;

import com.example.board_upgrade.Dto.CommentDto;
import com.example.board_upgrade.Entity.User;
import com.example.board_upgrade.Repository.UserRepository;
import com.example.board_upgrade.Response.Response;
import com.example.board_upgrade.Service.CommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;
    private final UserRepository userRepository;

    @ApiOperation(value = "댓글 작성", notes = "댓글을 작성합니다. ")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/board/{boardId}/comments/write")
    public Response writeComment(@PathVariable("boardId") Integer boardId, @RequestBody CommentDto commentDto){
        User user = userRepository.findById(1).get();
        return new Response("성공", "댓글을 등록했습니다. ", commentService.writeComment(boardId,
                commentDto, user));
    }

    @ApiOperation(value = "댓글 불러오기", notes = "게시물의 댓글들을 불러옵니다. ")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/board/{boardId}/comments")
    public Response readComment(@PathVariable("boardId") Integer boardId){
        return new Response("성공", "댓글들을 불러옵니다. ", commentService.getComments(boardId));
    }

    @ApiOperation(value = "댓글 삭제", notes = "해당 댓글을 삭제합니다. ")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/board/{boardId}/comments/delete/{commentId}")
    public Response deleteComment(@PathVariable("boardId") Integer boardId,
                                  @PathVariable("commentId") Integer commentId){
        return new Response("성공", "댓글 삭제 완료", commentService.deleteComment(commentId));
    }

}
