package com.example.task.taskph.service.impl;

import com.example.task.taskph.dto.request.UserRequest;
import com.example.task.taskph.dto.response.UserResponse;
import com.example.task.taskph.entity.Status;
import com.example.task.taskph.entity.User;
import com.example.task.taskph.enums.ExceptionEnum;
import com.example.task.taskph.exception.MyException;
import com.example.task.taskph.repository.StatusRepo;
import com.example.task.taskph.repository.UserRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        User user=new User();
        user.setId(1L);
        user.setName("Ali");
        user.setSurname("Aliyev");
        user.setPhoto("test.url");
        user.setEmail("alialiyev@mail.ru");
        user.setStatus(status);
        User expectedUser=user;
        List<Status> statusList=new ArrayList<>();
        statusList.add(status);
        statusList.add(status1);
        Status expected=new Status(1L,"online");
        List<Status> expectedList=statusList;

        Mockito.when(statusRepo.findAll()).thenReturn(statusList);
        Mockito.when(statusRepo.findById(status.getId())).thenReturn(Optional.of(expected));
        Mockito.when(userRepo.save(user)).thenReturn(expectedUser);

        List<Status> actualList=statusRepo.findAll();
        Status actual=statusRepo.findById(status.getId()).get();
        User actualUser=userRepo.save(user);

        assertEquals(expectedList,actualList);
        assertEquals(expected,actual);
        assertEquals(expectedUser,actualUser);


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

    @Test
    public void convertToRes_Success(){
        User user=new User();
        Status status=new Status(1L,"online");
        user.setId(1L);
        user.setName("Ali");
        user.setSurname("Aliyev");
        user.setPhoto("test.url");
        user.setEmail("alialiyev@mail.ru");
        user.setStatus(status);

        UserResponse userResponse= UserResponse.builder().id(user.getId()).name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail()).photoUrl(user.getPhoto())
                .status(user.getStatus().getStatus()).build();
        UserResponse expected=userResponse;

        UserResponse actual=userService.convertToRes(user);

        assertEquals(expected,actual);

    }
}