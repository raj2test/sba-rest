package com.fsd.sba.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ParentTask implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer parentId;

	private String parentTask;

	private List<Task> tasks;

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

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public void addTasks(Task task) {
		if (null == this.tasks) {
			this.tasks = new ArrayList<Task>();
		}
		this.tasks.add(task);
		task.setParentTask(this);
	}

	@Override
	public boolean equals(Object obj) {
		boolean flag = false;
		if (obj instanceof ParentTask) {
			ParentTask b = (ParentTask) obj;
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
