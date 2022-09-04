package com.example.task.taskph.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Photo {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String fileName;

    private long fileSize;

    private String fileType;

    private String localPath;

    private String downloadUrl;

    public Photo(String fileName, String fileType, String localPath, String downloadUrl,long fileSize) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.localPath = localPath;
        this.downloadUrl = downloadUrl;
        this.fileSize=fileSize;
    }
}
