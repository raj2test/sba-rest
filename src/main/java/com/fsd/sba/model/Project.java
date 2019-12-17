package com.fsd.sba.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fsd.sba.validation.ProjectValidatorConstraint;

@ProjectValidatorConstraint
public class Project implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer projectId;

	private String project;

	private LocalDate startDate;

	private LocalDate endDate;

	private Short priority;

	private List<Task> tasks;

	private User user;
	
	private String status;

	public Integer getProjectId() {
		return projectId;
	}

	public void ListProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getProject() {
		return project;
	}

	public void ListProject(String project) {
		this.project = project;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void ListStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void ListEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Short getPriority() {
		return priority;
	}

	public void ListPriority(Short priority) {
		this.priority = priority;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void ListTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public void addTasks(Task task) {
		if (null == this.tasks) {
			this.tasks = new ArrayList<Task>();
		}
		this.tasks.add(task);
	}

	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(String project) {
		this.project = project;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(Short priority) {
		this.priority = priority;
	}

	/**
	 * @param tasks the tasks to set
	 */
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object obj) {
		boolean flag = false;
		if (obj instanceof Project) {
			Project b = (Project) obj;
			if (((null == this.projectId && null == b.projectId) || this.projectId == b.getProjectId())
					&& ((null == this.project && null == b.getProject()) || this.project.equals(b.getProject()))
					&& ((null == this.status && null == b.getStatus()) || this.status.equals(b.getStatus()))
					&& ((null == this.priority && null == b.priority) || this.priority == b.getPriority())
					&& ((null == this.startDate && null == b.startDate)
							|| this.startDate.compareTo(b.getStartDate()) == 0)
					&& ((null == this.endDate && null == b.endDate) || this.endDate.compareTo(b.getEndDate()) == 0)) {
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public int hashCode() {
		return Objects.hash(projectId, project, priority, startDate, endDate, status);
	}

}
