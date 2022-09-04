package com.example.task.taskph.service;

import com.example.task.taskph.dto.response.PhotoResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PhotoService {
    PhotoResponse savePhoto(MultipartFile file) throws IOException;

    ResponseEntity<Resource> getPhoto(String photoId);

    String  sendFileToFolder(MultipartFile multipartFile);
}
