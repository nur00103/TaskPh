package com.example.task.taskph.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
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
