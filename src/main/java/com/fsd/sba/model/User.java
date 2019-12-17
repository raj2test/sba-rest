package com.fsd.sba.model;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotEmpty;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer userId;

	@NotEmpty(message = "{user.firstname.required}")
	private String firstName;

	@NotEmpty(message = "{user.lastname.required}")
	private String lastName;

	@NotEmpty(message = "{user.employee.id.required}")
	private String employeeId;

	private Project project;

	private Task task;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	@Override
	public boolean equals(Object obj) {
		boolean flag = false;
		if (obj instanceof User) {
			User b = (User) obj;
			if (((null == this.userId && null == b.userId) || this.userId == b.getUserId())
					&& ((null == this.firstName && null == b.firstName) || this.getFirstName().equals(b.getFirstName()))
					&& ((null == this.lastName && null == b.lastName) || this.getLastName().equals(b.getLastName()))
					&& ((null == this.employeeId && null == b.employeeId)
							|| this.getEmployeeId().equals(b.getEmployeeId()))) {
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, firstName, lastName, employeeId);
	}

}
