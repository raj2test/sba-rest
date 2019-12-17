package com.fsd.sba.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class UserEt implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(updatable = false, nullable = false, name="user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	
	@Column(nullable = false, name="first_name")
	private String firstName;
	
	@Column(nullable = false, name="last_name")
	private String lastName;
	
	@Column(updatable = false, nullable = false, name="employee_id")
	private String employeeId;	
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="project_id", referencedColumnName = "project_id", unique = true)
	private ProjectEt project;
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="task_id", referencedColumnName = "task_id", unique = true)
	private TaskEt task;

	
	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the employeeId
	 */
	public String getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * @return the project
	 */
	public ProjectEt getProject() {
		return project;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(ProjectEt project) {
		this.project = project;
	}

	/**
	 * @return the task
	 */
	public TaskEt getTask() {
		return task;
	}

	/**
	 * @param task the task to set
	 */
	public void setTask(TaskEt task) {
		this.task = task;
	}

	@Override
	public boolean equals(Object obj) {
		boolean flag = false;
		if (obj instanceof UserEt) {
			UserEt b = (UserEt) obj;
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
