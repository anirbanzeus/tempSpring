package com.fse.taskmanagement.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fse.taskmanagement.dao.UserDao;
import com.fse.taskmanagement.model.User;
import com.fse.taskmanagement.model.UserDto;
import com.fse.taskmanagement.service.UserService;

@Service(value = "userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;

	@Override
	public User save(UserDto user) {
		User newUser = new User();
		newUser.setEmpId(user.getEmpId());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		return userDao.save(newUser);
	}

	@Override
	public List<User> findAll() {
		List<User> list = new ArrayList<User>();
		userDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(int id) {
		userDao.deleteById(id);

	}

	@Override
	public User findById(int id) {
		@SuppressWarnings("rawtypes")
		Optional optionalUser = userDao.findById(id);
		if (optionalUser.isPresent())
			return (User) optionalUser.get();
		else
			return null;
	}

	@Override
	public UserDto update(UserDto userDto) {
		User user = findById(userDto.getUserId());
        if(user != null) {
            BeanUtils.copyProperties(userDto, user, "password");
            userDao.save(user);
        }
		return userDto;
	}

}
