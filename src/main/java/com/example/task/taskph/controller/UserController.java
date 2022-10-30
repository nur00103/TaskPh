package com.example.task.taskph.controller;

import com.example.task.taskph.dto.request.FilterUserReq;
import com.example.task.taskph.dto.request.UserRequest;
import com.example.task.taskph.dto.response.ResponseModel;
import com.example.task.taskph.dto.response.StatusResponse;
import com.example.task.taskph.dto.response.UserResponse;
import com.example.task.taskph.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("users")
    public ResponseModel<List<UserResponse>> getUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/save")
    public ResponseModel<UserResponse> saveUser(@RequestBody @Valid UserRequest userRequest) throws ParseException {
        return userService.save(userRequest);
    }

    @GetMapping("change/{id}")
    public ResponseModel<StatusResponse> changeStatus(@PathVariable("id") @Valid Long id){
        return userService.changeStatus(id);
    }

    @GetMapping("/{id}")
    public ResponseModel<UserResponse> getUserById(@PathVariable("id") @Valid Long id){
        return userService.getUserById(id);
    }

    @GetMapping("/filter")
    public ResponseModel<List<UserResponse>> getUserByFilter(@RequestBody FilterUserReq filterUserReq) throws ParseException {
        return userService.getUserByFilter(filterUserReq);
    }

}
