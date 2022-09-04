package com.example.task.taskph.entity;

import lombok.Data;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.*;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)

    private String name;

    @Column(length = 20)
    private String surname;

    @Column(length = 30)
    private String email;

    private String photo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Status status;


}
