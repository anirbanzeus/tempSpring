package com.fse.taskmanagement.service;

import java.util.List;

import com.fse.taskmanagement.model.Project;
import com.fse.taskmanagement.model.ProjectDto;

public interface ProjectService {
	
	Project save(ProjectDto projectDto);
	
    List<Project> findAll();
    
    void delete(int id);

    Project findById(int id);

    ProjectDto update(ProjectDto projectDto);
    
    //Project findByprojectName(String projectName);

}
