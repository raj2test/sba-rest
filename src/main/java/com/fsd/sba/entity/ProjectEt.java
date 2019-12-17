package com.fsd.sba.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fsd.sba.util.LocalDateConverter;

@Entity
@Table(name = "project")
public class ProjectEt implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(updatable = false, nullable = false, name = "project_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer projectId;

	private String project;

	@Column(name = "start_date")
	@Convert(converter = LocalDateConverter.class)
	private LocalDate startDate;

	@Column(name = "end_date")
	@Convert(converter = LocalDateConverter.class)
	private LocalDate endDate;

	@Column(nullable = false, name = "priority")
	private Short priority;
	
	@Column(nullable = false)
	private String status;

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	private List<TaskEt> tasks;

	@OneToOne(mappedBy = "project", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private UserEt user;

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

	public List<TaskEt> getTasks() {
		return tasks;
	}

	public void ListTasks(List<TaskEt> tasks) {
		this.tasks = tasks;
	}

	public void addTasks(TaskEt task) {
		if (null == this.tasks) {
			this.tasks = new ArrayList<TaskEt>();
		}
		this.tasks.add(task);
		task.setProject(this);
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
	public void setTasks(List<TaskEt> tasks) {
		this.tasks = tasks;
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
		user.setProject(this);
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
		if (obj instanceof ProjectEt) {
			ProjectEt b = (ProjectEt) obj;
			if (((null == this.projectId && null == b.projectId) || this.projectId == b.getProjectId())
					&& ((null == this.project && null == b.getProject()) || this.project.equals(b.getProject()))
					&& ((null == this.priority && null == b.priority) || this.priority == b.getPriority())
					&& ((null == this.status && null == b.getStatus()) || this.status.equals(b.getStatus()))
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
