package com.board.Board_Upgraded.exception.post;

import com.board.Board_Upgraded.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PostExceptionAdvice {

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response postNotFoundException(){
        return Response.failure(404, "해당 게시물을 찾을 수 없습니다.");
    }

    @ExceptionHandler(NotMyPostException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response notMyPostException(){
        return Response.failure(401, "권한이 없는 게시물 입니다.");
    }

    @ExceptionHandler(MembersPostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response membersPostNotFoundException(){
        return Response.failure(404, "존재하지 않는 사용자이거나, 게시물이 존재하지 않습니다.");
    }
}
