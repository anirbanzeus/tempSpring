package com.fse.taskmanagement.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fse.taskmanagement.model.Project;

@Repository
public interface ProjectDao extends CrudRepository<Project, Integer>{

	Project findByprojectName(String projectName);
    
	Project findByprojectId(int id);


}
