package com.board.Board_Upgraded.exception.token;

import com.board.Board_Upgraded.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TokenExceptionAdvice {

    @ExceptionHandler(LogOutMemberException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response logOutMemberException(){
        return Response.failure(404, "로그아웃 된 사용자입니다.");
    }

    @ExceptionHandler(TokenUnmatchWithMemberException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response tokenUnmatchWithMemberException(){
        return Response.failure(404, "토큰의 유저 정보가 일치하지 않습니다.");
    }

    @ExceptionHandler(UnvalidRefreshTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response unvalidRefreshTokenException(){
        return Response.failure(404, "Refresh Token 이 유효하지 않습니다.");
    }
}
