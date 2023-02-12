package com.board.Board_Upgraded.exception.validation;

import com.board.Board_Upgraded.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response needToWriteParameterException(MethodArgumentNotValidException e){
        return Response.failure(400, e.getFieldErrors().get(0).getDefaultMessage());
    }
}
