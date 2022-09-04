package com.example.task.taskph.service.impl;

import com.example.task.taskph.dto.request.UserRequest;
import com.example.task.taskph.dto.response.ResponseModel;
import com.example.task.taskph.dto.response.StatusResponse;
import com.example.task.taskph.dto.response.UserResponse;
import com.example.task.taskph.entity.Status;
import com.example.task.taskph.entity.User;
import com.example.task.taskph.enums.ExceptionEnum;
import com.example.task.taskph.enums.StatusEnum;
import com.example.task.taskph.exception.MyException;
import com.example.task.taskph.repository.StatusRepo;
import com.example.task.taskph.repository.UserRepo;
import com.example.task.taskph.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final StatusRepo statusRepo;

    @Override
    public ResponseModel<List<UserResponse>> getAllUsers() {
        List<UserResponse> users= userRepo.findAll().stream().map(user -> convertToRes(user))
                .collect(Collectors.toList());
        if (users.isEmpty()){
            throw new MyException(ExceptionEnum.EMPTY);
        }
        return ResponseModel.<List<UserResponse>>builder().result(users)
                .status(ExceptionEnum.SUCCESS.getMessage()).code(ExceptionEnum.SUCCESS.getCode()).build();
    }

    @Override
    public ResponseModel<UserResponse> save(UserRequest userRequest) {

        if (userRequest==null){
           throw new MyException(ExceptionEnum.BAD_REQUEST);
        }
        User user=new User();
        BeanUtils.copyProperties(userRequest,user);
        user.setPhoto(userRequest.getPhotoUrl());
        System.out.println(user.getPhoto());
        Status status=statusRepo.findById(1L).get();
        user.setStatus(status);
        userRepo.save(user);
        UserResponse userResponse=convertToRes(user);
        return ResponseModel.<UserResponse>builder().result(userResponse)
                .status(ExceptionEnum.SUCCESS.getMessage()).code(ExceptionEnum.SUCCESS.getCode()).build();
    }

    @Override
    public ResponseModel<StatusResponse> changeStatus(Long id) {
        if (id==null || id.equals("")){
            throw new MyException(ExceptionEnum.INPUT);
        }
        StatusResponse statusResponse=null;
        User user=null;
        user=userRepo.findById(id).orElseThrow(()->new MyException(ExceptionEnum.USER_NOT_FOUND));

        if (user.getStatus().getStatus().equals("online")){
            user.setStatus(statusRepo.findById(2L).get());
            userRepo.save(user);
            statusResponse= StatusResponse.builder().oldStatus("online").newStatus("offline").build();

        }else if(user.getStatus().getStatus().equals("offline")){
            user.setStatus(statusRepo.findById(1L).get());
            userRepo.save(user);
            statusResponse= StatusResponse.builder().oldStatus("offline").newStatus("online").build();
        }else {
            throw new MyException(ExceptionEnum.UNKNOWN);
        }
        return ResponseModel.<StatusResponse>builder().result(statusResponse)
                .status(ExceptionEnum.SUCCESS.getMessage()).code(ExceptionEnum.SUCCESS.getCode()).build();
    }

    @Override
    public ResponseModel<UserResponse> getUserById(Long id) {
        if (id==null || id.equals("")){
            throw new MyException(ExceptionEnum.INPUT);
        }
        User user=userRepo.findById(id).orElseThrow(()->new MyException(ExceptionEnum.USER_NOT_FOUND));
        UserResponse userResponse=UserResponse.builder().id(user.getId())
                .name(user.getName()).surname(user.getSurname())
                .email(user.getEmail()).photoUrl(user.getPhoto()).build();
        return ResponseModel.<UserResponse>builder().result(userResponse).status(ExceptionEnum.SUCCESS.getMessage())
                .code(ExceptionEnum.SUCCESS.getCode()).build();
    }

    private UserResponse convertToRes(User user){
        return UserResponse.builder().id(user.getId()).name(user.getName()).surname(user.getSurname())
                .email(user.getEmail()).photoUrl(user.getPhoto()).status(user.getStatus().getStatus()).build();

    }
}
