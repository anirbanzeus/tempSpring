package com.fse.taskmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fse.taskmanagement.model.ApiResponse;
import com.fse.taskmanagement.model.User;
import com.fse.taskmanagement.model.UserDto;
import com.fse.taskmanagement.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
    private UserService userService;

    @PostMapping
    public ApiResponse<User> saveUser(@RequestBody UserDto user){
        return new ApiResponse<>(HttpStatus.OK.value(), "User saved successfully.",userService.save(user));
    }
    
    @GetMapping
    public ApiResponse<User> getAllUsers(){
        return new ApiResponse<>(HttpStatus.OK.value(), "All users retreived successfully.",userService.findAll());
    }
    
    @PutMapping("/{id}")
    public ApiResponse<Object> update(@RequestBody UserDto userDto) {
    	//userDto.setUserId(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "User updated successfully.",userService.update(userDto));
    }

}
