package com.example.task.taskph.controller;

import com.example.task.taskph.dto.response.ErrorResponse;
import com.example.task.taskph.dto.response.ResponseModel;
import com.example.task.taskph.dto.response.UserResponse;
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
    public ResponseModel<UserResponse> expMy(MyException myException){
        return ResponseModel.<UserResponse>builder().status(myException.getMessage())
                .code(myException.getCode()).result(null).error(true).build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public  ResponseModel<UserResponse> expReq(MethodArgumentTypeMismatchException e){
        return ResponseModel.<UserResponse>builder().status(ExceptionEnum.INPUT.getMessage())
                .code(ExceptionEnum.INPUT.getCode()).result(null).error(true).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseModel<UserResponse> exceptionInput(MethodArgumentNotValidException e){
        return ResponseModel.<UserResponse>builder().status(ExceptionEnum.BAD_REQUEST.getMessage())
                .code(ExceptionEnum.BAD_REQUEST.getCode()).result(null).error(true).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseModel<UserResponse> exceptionAll(Exception e){
        return ResponseModel.<UserResponse>builder().status(e.getMessage())
                .code(404).result(null).error(true).build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseModel<UserResponse> exceptionAllRunTime(RuntimeException e){
        return ResponseModel.<UserResponse>builder().status(e.getMessage())
                .code(404).result(null).error(true).build();
    }


}
