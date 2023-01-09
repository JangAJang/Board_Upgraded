package com.board.Board_Upgraded.exception;

import com.board.Board_Upgraded.exception.authentication.NeedToLoginException;
import com.board.Board_Upgraded.exception.authentication.NotAuthenticationInfoException;
import com.board.Board_Upgraded.exception.authentication.NotRightAuthenticationException;
import com.board.Board_Upgraded.exception.authentication.WrongTokenException;
import com.board.Board_Upgraded.exception.member.*;
import com.board.Board_Upgraded.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(NeedToLoginException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response needToLoginException(){
        return Response.failure(403, "인증이 되어있지 않습니다. 로그인하세요.");
    }

    @ExceptionHandler(NotRightAuthenticationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response notRightAuthenticationException(){
        return Response.failure(403, "인증 정보가 존재하지 않습니다. 다시 로그인해주세요");
    }

    @ExceptionHandler(NotAuthenticationInfoException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public Response notAuthenticationInfoException(){
        return Response.failure(403, "권한 정보가 없는 토큰입니다.");
    }

    @ExceptionHandler(WrongTokenException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public Response wrongTokenException(){
        return Response.failure(401, "JWT 토큰이 잘못되었습니다.");
    }

    @ExceptionHandler(UsernameAlreadyInUseException.class)
    @ResponseStatus(HttpStatus.FOUND)
    public Response usernameAlreadyInUseException(){
        return Response.failure(404, "이미 사용중인 아이디입니다.");
    }

    @ExceptionHandler(NicknameAlreadyInUseException.class)
    @ResponseStatus(HttpStatus.FOUND)
    public Response nicknameAlreadyInUseException(){
        return Response.failure(404, "이미 사용중인 닉네임입니다.");
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    @ResponseStatus(HttpStatus.FOUND)
    public Response emailAlreadyInUseException(){
        return Response.failure(404, "이미 사용중인 이메일입니다.");
    }

    @ExceptionHandler(EmailNotFormatException.class)
    @ResponseStatus(HttpStatus.FOUND)
    public Response emailNotFormatException(){
        return Response.failure(404, "올바르지 않은 이메일 형식입니다.");
    }

    @ExceptionHandler(PasswordNotMatchingException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public Response passwordNotMatchingException(){
        return Response.failure(404, "비밀번호가 일치하지 않습니다.");
    }
}
