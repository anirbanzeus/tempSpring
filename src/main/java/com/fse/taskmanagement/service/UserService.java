package com.fse.taskmanagement.service;

import java.util.List;

import com.fse.taskmanagement.model.User;
import com.fse.taskmanagement.model.UserDto;

public interface UserService {
	
	User save(UserDto user);
	
    List<User> findAll();
    
    void delete(int id);

    User findById(int id);

    UserDto update(UserDto userDto);

}
