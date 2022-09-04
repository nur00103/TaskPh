package com.example.task.taskph.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserRequest {

    @NotBlank
    @NotNull
    private String name;
    @NotBlank
    @NotNull
    private String surname;
    private String email;
    private String photoUrl;
}
