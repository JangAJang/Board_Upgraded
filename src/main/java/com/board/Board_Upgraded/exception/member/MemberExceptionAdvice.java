package com.board.Board_Upgraded.exception.member;

import com.board.Board_Upgraded.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberExceptionAdvice {

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

    @ExceptionHandler(PasswordNotChangedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response passwordNotChangedException(){
        return Response.failure(404, "같은 비밀번호로 변경할 수 없습니다.");
    }

    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response memberNotFoundException(){
        return Response.failure(404, "해당 사용자를 찾을 수 없습니다.");
    }
}