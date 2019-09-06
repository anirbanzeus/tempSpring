package com.fse.taskmanagement.service;

import java.util.List;

import com.fse.taskmanagement.model.Task;
import com.fse.taskmanagement.model.TaskDto;

public interface TaskService {
	
	Task save(TaskDto user);
    List<Task> findAll();
    void delete(int id);

    Task findOne(String username);

    Task findById(int id);

    TaskDto update(TaskDto userDto);

}
