package com.fsd.sba.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.fsd.sba.validation.TaskValidatorConstraint;

@TaskValidatorConstraint
public class Task implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer taskId;

	private String task;

	private LocalDate startDate;

	private LocalDate endDate;

	private Short priority;

	private String status;

	private Project project;

	private ParentTask parentTask;

	private User user;

	private boolean parentFlag;

	public Integer getTaskId() {
		return taskId;
	}

	public void ListTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getTask() {
		return task;
	}

	public void ListTask(String task) {
		this.task = task;
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

	public String getStatus() {
		return status;
	}

	public void ListStatus(String status) {
		this.status = status;
	}

	public Project getProject() {
		return project;
	}

	public void ListProject(Project project) {
		this.project = project;
	}

	public ParentTask getParentTask() {
		return parentTask;
	}

	public void ListParentTask(ParentTask parentTask) {
		this.parentTask = parentTask;
	}

	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	/**
	 * @param task the task to set
	 */
	public void setTask(String task) {
		this.task = task;
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
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}

	/**
	 * @param parentTask the parentTask to set
	 */
	public void setParentTask(ParentTask parentTask) {
		this.parentTask = parentTask;
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
	 * @return the parentFlag
	 */
	public boolean isParentFlag() {
		return parentFlag;
	}

	/**
	 * @param parentFlag the parentFlag to set
	 */
	public void setParentFlag(boolean parentFlag) {
		this.parentFlag = parentFlag;
	}

	@Override
	public boolean equals(Object obj) {
		boolean flag = false;
		if (obj instanceof Task) {
			Task b = (Task) obj;
			if (((null == this.taskId && null == b.taskId) || this.taskId == b.getTaskId())
					&& ((null == this.task && null == b.task) || this.task.equals(b.getTask()))
					&& (this.parentFlag == b.parentFlag)
					&& ((null == this.startDate && null == b.startDate)
							|| this.startDate.compareTo(b.getStartDate()) == 0)
					&& ((null == this.endDate && null == b.endDate) || this.endDate.compareTo(b.getEndDate()) == 0)
					&& ((null == this.status && null == b.status) || this.status.equals(b.getStatus()))) {
				flag = true;
			}
		}

		return flag;
	}

	@Override
	public int hashCode() {

		int hash;

		if (!parentFlag) {
			hash = Objects.hash(taskId, task, status, priority, startDate, endDate);
		} else {
			hash = Objects.hash(taskId, task);
		}

		return hash;
	}

}
