package com.example.task.taskph.service;

import com.example.task.taskph.dto.request.UserRequest;
import com.example.task.taskph.dto.response.ResponseModel;
import com.example.task.taskph.dto.response.StatusResponse;
import com.example.task.taskph.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    ResponseModel<List<UserResponse>> getAllUsers();

    ResponseModel<UserResponse> save(UserRequest userRequest);

    ResponseModel<StatusResponse> changeStatus(Long id);

    ResponseModel<UserResponse> getUserById(Long id);
}
