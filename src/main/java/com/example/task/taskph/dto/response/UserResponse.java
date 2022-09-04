package com.example.task.taskph.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String photoUrl;
    private String status;
}
