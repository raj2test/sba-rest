package com.fsd.sba.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fsd.sba.util.LocalDateConverter;

@Entity
@Table(name = "task")
public class TaskEt implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(updatable = false, nullable = false, name = "task_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer taskId;

	private String task;

	@Column(name = "start_date")
	@Convert(converter = LocalDateConverter.class)
	private LocalDate startDate;

	@Column(name = "end_date")
	@Convert(converter = LocalDateConverter.class)
	private LocalDate endDate;

	@Column(nullable = false, name = "priority")
	private Short priority;

	private String status;

	@ManyToOne
	@JoinColumn(name = "project_id", referencedColumnName = "project_id")
	private ProjectEt project;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "parent_id", referencedColumnName = "parent_id")
	private ParentTaskEt parentTask;

	@OneToOne(mappedBy = "task", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private UserEt user;
	
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

	public ProjectEt getProject() {
		return project;
	}

	public void ListProject(ProjectEt project) {
		this.project = project;
	}

	public ParentTaskEt getParentTask() {
		return parentTask;
	}

	public void ListParentTask(ParentTaskEt parentTask) {
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
	public void setProject(ProjectEt project) {
		this.project = project;
	}

	/**
	 * @param parentTask the parentTask to set
	 */
	public void setParentTask(ParentTaskEt parentTask) {
		this.parentTask = parentTask;
	}

	/**
	 * @return the user
	 */
	public UserEt getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(UserEt user) {
		this.user = user;
		user.setTask(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean flag = false;
		if (obj instanceof TaskEt) {
			TaskEt b = (TaskEt) obj;
			if (((null == this.taskId && null == b.taskId) || this.taskId == b.getTaskId())
					&& ((null == this.task && null == b.task) || this.task.equals(b.getTask()))
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

		if (null != parentTask) {
			hash = Objects.hash(taskId, task, status, priority, startDate, endDate);
		} else {
			hash = Objects.hash(taskId, task);
		}

		return hash;
	}

}
