package com.fsd.sba.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "parent_task")
public class ParentTaskEt implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(updatable = false, nullable = false, name = "parent_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer parentId;

	@Column(nullable = false, name = "parent_task")
	private String parentTask;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parentTask")
	private List<TaskEt> tasks;
	
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getParentTask() {
		return parentTask;
	}

	public void setParentTask(String parentTask) {
		this.parentTask = parentTask;
	}

	public List<TaskEt> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskEt> tasks) {
		this.tasks = tasks;
	}

	public void addTasks(TaskEt task) {
		if (null == this.tasks) {
			this.tasks = new ArrayList<TaskEt>();
		}
		this.tasks.add(task);
		task.setParentTask(this);
	}

	@Override
	public boolean equals(Object obj) {
		boolean flag = false;
		if (obj instanceof ParentTaskEt) {
			ParentTaskEt b = (ParentTaskEt) obj;
			if (((null == this.parentId && null == b.parentId) || this.parentId == b.getParentId())
					&& ((null == this.getParentTask() && null == b.getParentTask())
							|| this.parentTask.equals(b.getParentTask()))) {
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public int hashCode() {
		return Objects.hash(parentId, parentTask);
	}

}
