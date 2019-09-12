package com.fse.taskmanagement.dao;

import org.springframework.data.repository.CrudRepository;

import com.fse.taskmanagement.model.Parent;

public interface ParentDao extends CrudRepository<Parent, Integer>{
	
	Parent findByparentTaskName(String parentName);

}
