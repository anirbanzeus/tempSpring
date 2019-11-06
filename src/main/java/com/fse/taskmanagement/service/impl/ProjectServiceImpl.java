package com.fse.taskmanagement.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fse.taskmanagement.dao.ProjectDao;
import com.fse.taskmanagement.dao.TaskDao;
import com.fse.taskmanagement.dao.UserDao;
import com.fse.taskmanagement.model.Project;
import com.fse.taskmanagement.model.ProjectDto;
import com.fse.taskmanagement.model.User;
import com.fse.taskmanagement.service.ProjectService;
import com.fse.taskmanagement.util.ProjectUtils;

@Service(value = "projectService")
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private TaskDao taskDao;

	@Override
	public Project save(ProjectDto projectDto) {
		
		ProjectUtils utils = new ProjectUtils();

		Project newProject = new Project();
		newProject.setProjectName(projectDto.getProjectName());
		newProject.setProjectPriority(projectDto.getProjectPriority());
		newProject.setProjectStatus(projectDto.getProjectStatus());
		if(projectDto.getStartDate() != null) {
			newProject.setStartDate(projectDto.getStartDate());
		}else {
			newProject.setStartDate(new Date());
		}
		if(projectDto.getEndDate() != null) {
			newProject.setEndDate(projectDto.getEndDate());
		}else {
			newProject.setEndDate(utils.getNextDay(newProject.getStartDate()));
		}
		
		newProject = projectDao.save(newProject);
		
		if(projectDto.getProjectManager() != null) {
			User existingUser = userDao.findByfirstName(projectDto.getProjectManager());
			if(existingUser != null) {
				existingUser.setProjectId(String.valueOf(newProject.getProjectId()));
				userDao.save(existingUser);
			}
		}
		return newProject;
	}

	@Override
	public List<Project> findAll() {
		List<Project> list = new ArrayList<Project>();
		projectDao.findAll().iterator().forEachRemaining(list::add);
		for(Project temp: list) {
			temp.setTaskCount(taskDao.findByprojectName(temp.getProjectName()).size());
			temp.setCompletedTaskCount(taskDao.findBystatus("COMPLETED").size());
		}
		return list;
	}

	@Override
	public void delete(int id) {
		projectDao.deleteById(id);

	}

	@Override
	public Project findById(int id) {
		@SuppressWarnings("rawtypes")
		Optional optionalProject = projectDao.findById(id);
		if (optionalProject.isPresent())
			return (Project) optionalProject.get();
		else
			return null;
	}

	@Override
	public ProjectDto update(ProjectDto projectDto) {
		Project project = findById(projectDto.getProjectId());
        if(project != null) {
            BeanUtils.copyProperties(projectDto, project, "password");
            projectDao.save(project);
        }
		return projectDto;
	}

	/*@Override
	public Project findByprojectName(String projectName) {
		Project optionalProject = projectDao.findByprojectName(projectName);
		if (null != optionalProject)
			return optionalProject;
		else
			return null;
	}*/

}
