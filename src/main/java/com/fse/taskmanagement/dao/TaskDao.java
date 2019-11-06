package com.fse.taskmanagement.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.fse.taskmanagement.model.Task;

@Repository
public interface TaskDao extends CrudRepository<Task, Integer> {

    Task findBytaskName(String taskName);
    
    Task findBytaskId(int id);
    
    List<Task> findByprojectName(String projectName);
    
    List<Task> findBystatus(String statusValue);
}
