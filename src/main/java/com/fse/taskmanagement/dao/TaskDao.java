package com.fse.taskmanagement.dao;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import com.fse.taskmanagement.model.Task;

@Repository
public interface TaskDao extends CrudRepository<Task, Integer> {

    Task findBytaskName(String taskName);
}
