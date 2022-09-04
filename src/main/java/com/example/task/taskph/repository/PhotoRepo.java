package com.example.task.taskph.repository;

import com.example.task.taskph.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepo extends JpaRepository<Photo,String> {
}
