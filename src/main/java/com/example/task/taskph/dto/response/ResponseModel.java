package com.example.task.taskph.dto.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseModel <T>{
    private T result;
    private String status;
    private int code;
    private boolean error;

}
