package com.fse.taskmanagement.dao;

import org.springframework.data.repository.CrudRepository;

import com.fse.taskmanagement.model.User;

public interface UserDao extends CrudRepository<User, Integer>{
	
	User findByfirstName(String firstName);
    
    User findByempId(int id);
    
    User findByuserId(int id);

}
