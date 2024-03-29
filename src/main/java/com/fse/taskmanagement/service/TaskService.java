package com.fse.taskmanagement.service;

import java.util.List;

import com.fse.taskmanagement.model.Task;
import com.fse.taskmanagement.model.TaskDto;

public interface TaskService {
	
	Task save(TaskDto task);
	
    List<Task> findAll();
    
    List<Task> findAllParent();
    
    List<Task> findAllTasksForProject(String projectName);
    
    void delete(int id);

    Task findById(int id);

    TaskDto update(TaskDto userDto);

}
