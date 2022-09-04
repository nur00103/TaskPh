package com.example.task.taskph.controller;

import com.example.task.taskph.dto.response.PhotoResponse;
import com.example.task.taskph.entity.Photo;
import com.example.task.taskph.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/photo")
public class PhotoController  {

    private final PhotoService photoService;

    @PostMapping("upload")
    public PhotoResponse uploadPhoto(@RequestParam("file")MultipartFile file) throws IOException {
       return photoService.savePhoto(file);
    }

    @GetMapping("/download/{photoId}")
    public ResponseEntity<Resource> downloadPhoto(@PathVariable String photoId) throws IOException {
       return photoService.getPhoto(photoId);
    }
}
