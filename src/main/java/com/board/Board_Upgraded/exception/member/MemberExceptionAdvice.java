package com.board.Board_Upgraded.exception.member;

import com.board.Board_Upgraded.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberExceptionAdvice {

    @ExceptionHandler(UsernameAlreadyInUseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response usernameAlreadyInUseException(){
        return Response.failure(400, "이미 사용중인 아이디입니다.");
    }

    @ExceptionHandler(NicknameAlreadyInUseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response nicknameAlreadyInUseException(){
        return Response.failure(400, "이미 사용중인 닉네임입니다.");
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response emailAlreadyInUseException(){
        return Response.failure(400, "이미 사용중인 이메일입니다.");
    }

    @ExceptionHandler(EmailNotFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response emailNotFormatException(){
        return Response.failure(400, "올바르지 않은 이메일 형식입니다.");
    }

    @ExceptionHandler(PasswordNotMatchingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response passwordNotMatchingException(){
        return Response.failure(400, "비밀번호가 일치하지 않습니다.");
    }

    @ExceptionHandler(PasswordNotChangedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response passwordNotChangedException(){
        return Response.failure(400, "같은 비밀번호로 변경할 수 없습니다.");
    }

    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response memberNotFoundException(){
        return Response.failure(404, "해당 사용자를 찾을 수 없습니다.");
    }

    @ExceptionHandler(NeedToAddSearchConditionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response needToAddSearchConditionException(){
        return Response.failure(400, "검색 조건을 하나라도 입력해야 합니다.");
    }

    @ExceptionHandler(NeedToAddEditConditionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response needToAddEditConditionException(){
        return Response.failure(400,"수정 조건을 하나라도 입력해야 합니다.");
    }

    @ExceptionHandler(NeedToPutPasswordTwiceToEditException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response needToPutPasswordTwiceToEditException(){
        return Response.failure(400, "비밀번호를 수정하기 위해서는, 비밀번호를 두 번 입력해야 합니다.");
    }
}
