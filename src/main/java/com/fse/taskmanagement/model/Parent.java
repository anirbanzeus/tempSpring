package com.fse.taskmanagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "parent")
public class Parent {
	
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	private int parentId;
	
	@Column
	private String parentTaskName;

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getParentTaskName() {
		return parentTaskName;
	}

	public void setParentTakeName(String parentTaskName) {
		this.parentTaskName = parentTaskName;
	}

}
