/**
 * 
 */
package com.fsd.sba.transformer;

import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fsd.sba.entity.ParentTaskEt;
import com.fsd.sba.entity.ProjectEt;
import com.fsd.sba.entity.TaskEt;
import com.fsd.sba.entity.UserEt;
import com.fsd.sba.exception.SbaException;
import com.fsd.sba.exception.SbaResponseCode;
import com.fsd.sba.model.ParentTask;
import com.fsd.sba.model.Project;
import com.fsd.sba.model.Task;
import com.fsd.sba.model.User;
import com.fsd.sba.repository.ParentTaskRepository;
import com.fsd.sba.repository.ProjectRepository;
import com.fsd.sba.repository.TaskRepository;
import com.fsd.sba.repository.UserRepository;

/**
 * @author Rajiniganth Jagadeesan
 *
 */
@Component
public class Transformer {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private ParentTaskRepository prTskRepository;

	public UserEt buildUserEntity(User user) {

		UserEt userEt = null;
		if (null != user) {
			userEt = new UserEt();

			userEt.setEmployeeId(user.getEmployeeId());
			userEt.setFirstName(user.getFirstName());
			userEt.setLastName(user.getLastName());
			userEt.setUserId(user.getUserId());
		}

		return userEt;
	}

	public User buildUserModel(UserEt userEt) {

		User user = null;

		if (null != userEt) {
			user = new User();
			user.setEmployeeId(userEt.getEmployeeId());
			user.setFirstName(userEt.getFirstName());
			user.setLastName(userEt.getLastName());
			user.setUserId(userEt.getUserId());
		}

		return user;
	}

	@Transactional
	public ProjectEt buildProjectEntity(Project project) {

		ProjectEt prjEt = null;

		if (null != project) {

			Integer projectId = project.getProjectId();
			if (null != projectId) {
				prjEt = projectRepository.findById(projectId).orElse(null);
				if (null == prjEt) {
					throw new SbaException(SbaResponseCode.PROJECT_NOT_FOUND);
				}
			} else {
				prjEt = new ProjectEt();
			}
			User user = project.getUser();
			if (null != user && null != user.getUserId()) {
				UserEt usrEt = userRepository.findById(user.getUserId()).orElse(null);
				if (null != usrEt) {
					if (null == usrEt.getProject() || usrEt.getProject().equals(prjEt)) {
						if (null != prjEt.getUser() && !prjEt.getUser().equals(usrEt)) {
							// Handle orphan
							UserEt orphan = prjEt.getUser();
							orphan.setProject(null);
						}
						prjEt.setUser(usrEt);
					} else {
						throw new SbaException(SbaResponseCode.USER_OCCUPIED);
					}
				} else {
					throw new SbaException(SbaResponseCode.USER_NOT_AVAILALE);
				}
			} else {
				throw new SbaException(SbaResponseCode.INVALID_REQUEST);
			}

			prjEt.setProject(project.getProject());
			prjEt.setStartDate(project.getStartDate());
			prjEt.setEndDate(project.getEndDate());
			prjEt.setPriority(project.getPriority());
			prjEt.setStatus(project.getStatus());

		}

		return prjEt;
	}

	public TaskEt buildTaskEntity(Task tsk) {

		TaskEt tskEt = null;

		if (null != tsk) {
			if (null != tsk.getTaskId()) {
				tskEt = taskRepository.findById(tsk.getTaskId()).orElse(null);
				if (null == tskEt) {
					throw new SbaException(SbaResponseCode.TASK_NOT_FOUND);
				} 
			} else {
				tskEt = new TaskEt();
				tskEt.setTask(tsk.getTask());
			}
			if (tsk.isParentFlag()) {
				if (null == tsk.getTaskId()) {
					ParentTaskEt pEt = new ParentTaskEt();
					pEt.setParentTask(tsk.getTask());
					prTskRepository.save(pEt);
				}
			} else {
				if (null != tsk.getParentTask() && null != tsk.getParentTask().getParentId()) {
					ParentTaskEt pEt = prTskRepository.findById(tsk.getParentTask().getParentId()).orElse(null);
					if (null == pEt) {
						throw new SbaException(SbaResponseCode.PARENT_NOT_FOUND);
					}
					tskEt.setParentTask(pEt);
				} else {
					throw new SbaException(SbaResponseCode.INVALID_REQUEST);
				}
				tskEt.setPriority(tsk.getPriority());
				tskEt.setStatus(tsk.getStatus());
				tskEt.setStartDate(tsk.getStartDate());
				tskEt.setEndDate(tsk.getEndDate());
			}

			// User Mapping
			User user = tsk.getUser();
			if (null != user && null != user.getUserId()) {
				UserEt usrEt = userRepository.findById(user.getUserId()).orElse(null);
				if (null != usrEt) {
					if (null == usrEt.getTask() || usrEt.getTask().equals(tskEt)) {
						if (null != tskEt.getUser() && !tskEt.getUser().equals(usrEt)) {
							// Handle orphan
							UserEt orphan = tskEt.getUser();
							orphan.setTask(null);
						}
						tskEt.setUser(usrEt);
					} else {
						throw new SbaException(SbaResponseCode.USER_OCCUPIED);
					}
				} else {
					throw new SbaException(SbaResponseCode.USER_NOT_AVAILALE);
				}
			} else {
				throw new SbaException(SbaResponseCode.INVALID_REQUEST);
			}

			// Project Mapping
			if (null != tsk.getProject() && null != tsk.getProject().getProjectId()) {
				ProjectEt prjEt = projectRepository.findById(tsk.getProject().getProjectId()).orElse(null);
				if (null == prjEt) {
					throw new SbaException(SbaResponseCode.PROJECT_NOT_FOUND);
				}
				tskEt.setProject(prjEt);
			} else {
				throw new SbaException(SbaResponseCode.INVALID_REQUEST);
			}
		}
		return tskEt;
	}


	public Project buildProjectModel(ProjectEt projectEt, boolean primary) {

		Project project = null;

		if (null != projectEt) {
			project = new Project();
			project.setUser(buildUserModel(projectEt.getUser()));
			project.setProjectId(projectEt.getProjectId());
			project.setProject(projectEt.getProject());
			project.setStartDate(projectEt.getStartDate());
			project.setEndDate(projectEt.getEndDate());
			project.setPriority(projectEt.getPriority());
			project.setStatus(projectEt.getStatus());
			if (primary && CollectionUtils.isNotEmpty(projectEt.getTasks())) {
				for (TaskEt t: projectEt.getTasks()) {
					project.addTasks(buildTaskModel(t, false));
				}
			}

		}
		return project;
	}

	public Task buildTaskModel(TaskEt taskEt, boolean primary) {
		
		Task task = null;
		
		if (null != taskEt) {
			task = new Task();
			task.setTaskId(taskEt.getTaskId());
			task.setTask(taskEt.getTask());
			if (null != taskEt.getParentTask()) {
				task.setParentTask(buildParentTaskModel(taskEt.getParentTask()));
				task.setParentFlag(false);
				task.setPriority(taskEt.getPriority());
				task.setStartDate(taskEt.getStartDate());
				task.setEndDate(taskEt.getEndDate());
				task.setStatus(taskEt.getStatus());
			} else {
				task.setParentFlag(true);
			}
			task.setUser(buildUserModel(taskEt.getUser()));
			if (primary && null != taskEt.getProject()) {
				task.setProject(buildProjectModel(taskEt.getProject(), false));
			}
			
		}
		
		return task;
	}

	public ParentTask buildParentTaskModel(ParentTaskEt parentTask) {

		ParentTask pt = null;
		
		if (null != parentTask) {
			pt = new ParentTask();
			pt.setParentId(parentTask.getParentId());
			pt.setParentTask(parentTask.getParentTask());
		}
		return pt;
	}

}
