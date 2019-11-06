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
    
    @GetMapping("scope")
    public ApiResponse<Task> getParentTask(){
        return new ApiResponse<>(HttpStatus.OK.value(), "All parent tasks retreived successfully.",taskService.findAllParent());
    }
    
    @GetMapping("fetch/{projectName}")
    public ApiResponse<Task> getTaskByProjectName(@PathVariable String projectName){
        return new ApiResponse<>(HttpStatus.OK.value(), "All tasks retreived successfully.",taskService.findAllTasksForProject(projectName));
    }
    
    @GetMapping("/{id}")
    public ApiResponse<Task> getTask(@PathVariable int id){
        return new ApiResponse<>(HttpStatus.OK.value(), "Task retreived successfully.",taskService.findById(id));
    }
    
    @SuppressWarnings("rawtypes")
	@PutMapping("/{id}")
    public ApiResponse update(@RequestBody TaskDto userDto) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Task updated successfully.",taskService.update(userDto));
    }
    
    
    @SuppressWarnings("rawtypes")
	@PutMapping("complete/{id}")
    public ApiResponse updateToComplete(@RequestBody TaskDto userDto, @PathVariable int id) {
       userDto.setStatus("Complete");
       userDto.setTaskId(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Task completed successfully.",taskService.update(userDto));
    }

    @SuppressWarnings("rawtypes")
	@DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable int id) {
    	taskService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Task deleted successfully.", null);
    }

}
