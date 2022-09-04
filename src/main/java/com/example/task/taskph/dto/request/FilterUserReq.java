package com.example.task.taskph.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilterUserReq {

    private String status;
    private String timeStamp;
}
