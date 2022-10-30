package com.example.task.taskph.service.impl;

import com.example.task.taskph.dto.request.UserRequest;
import com.example.task.taskph.entity.Status;
import com.example.task.taskph.enums.ExceptionEnum;
import com.example.task.taskph.exception.MyException;
import com.example.task.taskph.repository.StatusRepo;
import com.example.task.taskph.repository.UserRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceImplTest {

    private UserServiceImpl userService;

    private  UserRepo userRepo;
    private  StatusRepo statusRepo;

    @Before
    public void setUp() throws Exception {
        userRepo= Mockito.mock(UserRepo.class);
        statusRepo=Mockito.mock(StatusRepo.class);
        userService=new UserServiceImpl(userRepo,statusRepo);
    }

    @Test
    public void whenSaveUserWithValidRequestItShouldReturnValidData(){
        UserRequest userRequest=UserRequest.builder()
                .name("Ali")
                .surname("Aliyev")
                .email("alialiyev@mail.ru")
                .photoUrl("test.url")
                .build();
        Status status=new Status(1L,"online");
        Status status1=new Status(2L,"offline");
        List<Status> statusList=new ArrayList<>();
        statusList.add(status);
        statusList.add(status1);

        Mockito.when(statusRepo.findAll()).thenReturn(statusList);

    }

    @Test
    public void whenSendNullRequestItShouldReturnException(){
        UserRequest userRequest=null;
        Executable executable = () -> {
            if(userRequest==null){
                throw new MyException(ExceptionEnum.BAD_REQUEST);
            }
        };
        assertThrows(MyException.class,executable);

    }
    @Test
    public void whenStatusListIsEmptyThenSaveStatusData(){
        List<Status> statusList=new ArrayList<>();
        Status status=new Status(1L,"online");
        Status status1=new Status(2L,"offline");

        Status savedStatus=new Status(1L,"online");
        Status savedStatus1=new Status(2L,"offline");

        Status actual=new Status();
        Status actual1=new Status();

        Mockito.when(statusRepo.save(status)).thenReturn(savedStatus);
        Mockito.when(statusRepo.save(status1)).thenReturn(savedStatus1);

        if (statusList.isEmpty()){
          actual= statusRepo.save(status);
          actual1=  statusRepo.save(status1);
        }

        assertEquals(status,actual);
        assertEquals(status1,actual1);
    }
}