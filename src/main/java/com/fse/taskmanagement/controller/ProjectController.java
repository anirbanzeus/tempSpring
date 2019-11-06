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
import com.fse.taskmanagement.model.Project;
import com.fse.taskmanagement.model.ProjectDto;
import com.fse.taskmanagement.service.ProjectService;

@CrossOrigin
@RestController
@RequestMapping("/project")
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
    @PostMapping
    public ApiResponse<Project> saveTask(@RequestBody ProjectDto project){
        return new ApiResponse<>(HttpStatus.OK.value(), "Project saved successfully.",projectService.save(project));
    }
    
    @GetMapping
    public ApiResponse<Project> getAllProjects(){
        return new ApiResponse<>(HttpStatus.OK.value(), "All projects retreived successfully.",projectService.findAll());
    }
    
    @SuppressWarnings("rawtypes")
	@PutMapping("/{id}")
    public ApiResponse update(@RequestBody ProjectDto projectDto) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Project updated successfully.",projectService.update(projectDto));
    }

}
