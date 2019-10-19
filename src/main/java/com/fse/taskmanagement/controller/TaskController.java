package com.fse.taskmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fse.taskmanagement.model.ApiResponse;
import com.fse.taskmanagement.model.Task;
import com.fse.taskmanagement.model.TaskDto;
import com.fse.taskmanagement.service.TaskService;

@CrossOrigin
@RestController
@RequestMapping("/tasks")
public class TaskController {
	
	@Autowired
    private TaskService taskService;

    @PostMapping
    public ApiResponse<Task> saveTask(@RequestBody TaskDto task){
        return new ApiResponse<>(HttpStatus.OK.value(), "Task saved successfully.",taskService.save(task));
    }
    
    @GetMapping
    public ApiResponse<Task> getAllTask(){
        return new ApiResponse<>(HttpStatus.OK.value(), "All tasks retreived successfully.",taskService.findAll());
    }
    
    @GetMapping("/{id}")
    public ApiResponse<Task> getTask(@PathVariable int id){
        return new ApiResponse<>(HttpStatus.OK.value(), "Task retreived successfully.",taskService.findById(id));
    }
    
    @PutMapping("/{id}")
    public ApiResponse update(@RequestBody TaskDto userDto) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Task updated successfully.",taskService.update(userDto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable int id) {
    	taskService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Task fetched successfully.", null);
    }

}
