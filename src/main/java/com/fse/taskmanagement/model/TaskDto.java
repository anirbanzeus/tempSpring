package com.fse.taskmanagement.model;

import java.util.Date;

public class TaskDto {
	
private int taskId;
	
	private int parentTaskID;
	private String taskName;
	private String priority;
	private Date startDate;
	private Date endDate;
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public int getParentTaskID() {
		return parentTaskID;
	}
	public void setParentTaskID(int parentTaskID) {
		this.parentTaskID = parentTaskID;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


}
