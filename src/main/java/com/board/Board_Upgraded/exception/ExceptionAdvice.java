package com.board.Board_Upgraded.exception;

import com.board.Board_Upgraded.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response needToLoginException(){
        return Response.failure(403, "인증이 되어있지 않습니다. 로그인하세요.");
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response notRightAuthentication(){
        return Response.failure(403, "인증 정보가 존재하지 않습니다. 다시 로그인해주세요");
    }


}
