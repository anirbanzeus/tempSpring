package com.fse.taskmanagement.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fse.taskmanagement.dao.ParentDao;
import com.fse.taskmanagement.dao.TaskDao;
import com.fse.taskmanagement.model.Parent;
import com.fse.taskmanagement.model.Task;
import com.fse.taskmanagement.model.TaskDto;
import com.fse.taskmanagement.service.TaskService;

@Service(value = "taskService")
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskDao taskDao;
	
	@Autowired
	private ParentDao parentDao;
	
	@Override
    public Task save(TaskDto task) {
		
		Task newTask = new Task();
		newTask.setEndDate(task.getEndDate());
		newTask.setStartDate(task.getStartDate());
		newTask.setPriority(task.getPriority());
		newTask.setTaskName(task.getTaskName());
		Parent existingParent = parentDao.findByparentTaskName(task.getParentTask());
		if(existingParent != null) {
			//newTask.getParent().setParentTakeName(existingParent.getParentTaskName());
			newTask.setParent(existingParent);
		}else {
			Parent parent  = new Parent();
			parent.setParentTakeName(task.getParentTask());
			newTask.setParent(parent);
		}
		
        return taskDao.save(newTask);
    }

	@Override
	public List<Task> findAll() {
		List<Task> list = new ArrayList<Task>();
 		taskDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Task findOne(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TaskDto update(TaskDto userDto) {
		// TODO Auto-generated method stub
		return null;
	}

}
