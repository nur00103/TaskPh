package com.example.task.taskph.service.impl;

import com.example.task.taskph.dto.response.PhotoResponse;
import com.example.task.taskph.entity.Photo;
import com.example.task.taskph.enums.ExceptionEnum;
import com.example.task.taskph.exception.MyException;
import com.example.task.taskph.repository.PhotoRepo;
import com.example.task.taskph.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepo photoRepo;

    @Value("${local.file.upload.path}")
    private String localPath;

    @Override
    @Transactional
    public PhotoResponse savePhoto(MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        String fileLocalPath = sendFileToFolder(multipartFile);
        String contentType = multipartFile.getContentType();
        long fileSize=multipartFile.getSize();
        Photo photo = new Photo(fileName, contentType, fileLocalPath,null,fileSize);
        Photo savedPhoto = photoRepo.save(photo);
        String dowmloadUrl= ServletUriComponentsBuilder.fromCurrentContextPath().toUriString()
                +"/photo/download/" +savedPhoto.getId();
        savedPhoto.setDownloadUrl(dowmloadUrl);
       return PhotoResponse.builder()
               .fileName(savedPhoto.getFileName()).fileType(savedPhoto.getFileType())
               .fileSize(savedPhoto.getFileSize()).downloadUrl(savedPhoto.getDownloadUrl())
               .build();


    }

    @Override
    public ResponseEntity<Resource> getPhoto(String photoId) {
        Photo photo = photoRepo.findById(photoId).orElseThrow(()->new MyException(ExceptionEnum.PHOTO));
        try {
            byte[] bytes = FileUtils.readFileToByteArray(new File(photo.getLocalPath()));
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(photo.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + photo.getFileName()
                            + "\"").body(new ByteArrayResource(bytes));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String sendFileToFolder(MultipartFile multipartFile) {
        if(multipartFile.isEmpty()) {
            throw new MyException(ExceptionEnum.PHOTO);
        }
        String fileName = multipartFile.getOriginalFilename() ;
        String fileLocalPath = localPath + UUID.randomUUID() + fileName;
        File file = new File(fileLocalPath);
        try {
            file.createNewFile();
            InputStream inputStream = multipartFile.getInputStream();
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            inputStream.close();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
            fileOutputStream.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return fileLocalPath;

    }
}
