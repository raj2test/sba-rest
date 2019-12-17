/**
 * 
 */
package com.fsd.sba.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fsd.sba.entity.TaskEt;
import com.fsd.sba.model.Task;
import com.fsd.sba.repository.TaskRepository;
import com.fsd.sba.transformer.Transformer;

/**
 * @author Rajiniganth Jagadeesan
 * 
 *         Service class for handling functionalities related to task resource
 *
 */
@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private Transformer transformer;

	/**
	 * @param task
	 * @return
	 */
	@Transactional
	public Task saveTask(Task task) {

		TaskEt taskEt = transformer.buildTaskEntity(task);

		taskEt = taskRepository.save(taskEt);

		return transformer.buildTaskModel(taskEt, true);
	}

	/**
	 * @param task
	 * @return
	 */
	public Task getTask(Integer taskId) {
		
		TaskEt taskEt = taskRepository.findById(taskId).orElse(null);
		
		return transformer.buildTaskModel(taskEt, true);
	}

}
