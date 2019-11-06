package com.fse.taskmanagement.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fse.taskmanagement.dao.ParentDao;
import com.fse.taskmanagement.dao.TaskDao;
import com.fse.taskmanagement.model.Parent;
import com.fse.taskmanagement.model.Task;
import com.fse.taskmanagement.model.TaskDto;
import com.fse.taskmanagement.service.TaskService;
import com.fse.taskmanagement.util.ProjectUtils;

@Service(value = "taskService")
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskDao taskDao;
	
	@Autowired
	private ParentDao parentDao;
	
	@Override
    public Task save(TaskDto task) {
		ProjectUtils utils = new ProjectUtils();
		Task newTask = new Task();
		Parent parentTask;
		
		if(task.isParent()) {
			Parent parent  = new Parent();
			parent.setParentTakeName(task.getParentTask());
			parentTask = parentDao.save(parent);
			newTask.setParentId(parentTask.getParentId());
			newTask.setParent(true);
			newTask.setTaskName(task.getTaskName());
			newTask.setProjectName(task.getProjectName());
			newTask.setStatus("Running");
			newTask.setPriority(task.getPriority());
			newTask = taskDao.save(newTask);			
		}else {			
			newTask.setPriority(task.getPriority());
			newTask.setTaskName(task.getTaskName());
			newTask.setParentTask(task.getParentTask());
			newTask.setProjectName(task.getProjectName());
			newTask.setStatus("Running");
			
			if(task.getStartDate() != null) {
				newTask.setStartDate(task.getStartDate());
			}else {
				newTask.setStartDate(new Date());
			}
			if(task.getEndDate() != null) {
				newTask.setEndDate(task.getEndDate());
			}else {
				newTask.setEndDate(utils.getNextDay(newTask.getStartDate()));
			}
			
			newTask = taskDao.save(newTask);
		}
		
        return newTask;
    }

	@Override
	public List<Task> findAll() {
		List<Task> list = new ArrayList<Task>();
 		taskDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(int id) {
		//taskDao.deleteById(id);
		
	}

	@Override
	public Task findById(int id) {
		@SuppressWarnings("rawtypes")
		Optional optionalTask = taskDao.findById(id);
		if (optionalTask.isPresent())
			return (Task) optionalTask.get();
		else
			return null;
	}

	@Override
	public TaskDto update(TaskDto taskDto) {
		Task task = findById(taskDto.getTaskId());
        if(task != null) {
            BeanUtils.copyProperties(taskDto, task, "password");
            taskDao.save(task);
        }
        return taskDto;
	}

	@Override
	public List<Task> findAllParent() {
		List<Task> resultList = (List<Task>) taskDao.findAll();
		List<Task> list = new ArrayList<Task>();
		
		for(Task tempTask : resultList) {
			if(tempTask.isParent()) {
				list.add(tempTask);
			}
		}
		
		return list;
	}
	
	@Override
	public List<Task> findAllTasksForProject(String projectName) {
		//Project validProject = projectDao.findByprojectName(projectName);
		List<Task> tasks = new ArrayList<Task>();
		//if(null != validProject) {			
			tasks = taskDao.findByprojectName(projectName);			
		//}		
		
		return tasks;
	}

}
