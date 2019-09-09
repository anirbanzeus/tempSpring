package com.fse.taskmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fse.taskmanagement.model.ApiResponse;
import com.fse.taskmanagement.model.Task;
import com.fse.taskmanagement.model.TaskDto;
import com.fse.taskmanagement.service.TaskService;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8088"})
//@CrossOrigin
@RestController
@RequestMapping("/tasks")
public class TaskController {
	
	@Autowired
    private TaskService taskService;

	@CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping
    public ApiResponse<Task> saveTask(@RequestBody TaskDto task){
        return new ApiResponse<>(HttpStatus.OK.value(), "Task saved successfully.",taskService.save(task));
    }

}
