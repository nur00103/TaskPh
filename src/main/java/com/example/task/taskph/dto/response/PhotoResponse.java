package com.example.task.taskph.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class PhotoResponse {

    private String fileName;
    private String fileType;
    private long fileSize;
    private String downloadUrl;

    public PhotoResponse(String fileName, String fileType, long fileSize, String downloadUrl) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.downloadUrl = downloadUrl;
    }
}
