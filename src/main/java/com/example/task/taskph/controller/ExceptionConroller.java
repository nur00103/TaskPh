package com.example.task.taskph.controller;

import com.example.task.taskph.dto.response.ErrorResponse;
import com.example.task.taskph.enums.ExceptionEnum;
import com.example.task.taskph.exception.MyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ExceptionConroller {

    @ExceptionHandler(MyException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse expMy(MyException myException){
        return ErrorResponse.builder().message(myException.getMessage())
                .code(myException.getCode()).build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public  ErrorResponse expReq(MethodArgumentTypeMismatchException e){
        return ErrorResponse.builder().message(ExceptionEnum.INPUT.getMessage())
                .code(ExceptionEnum.INPUT.getCode()).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse exceptionInput(MethodArgumentNotValidException e){
        return ErrorResponse.builder().code(ExceptionEnum.BAD_REQUEST.getCode())
                .message(ExceptionEnum.BAD_REQUEST.getMessage()).build();
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse exceptionAll(Exception e){
        return ErrorResponse.builder().message(e.getMessage()).code(404).build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse exceptionAllRunTime(RuntimeException e){
        return ErrorResponse.builder().message(e.getMessage()).code(404).build();
    }


}
