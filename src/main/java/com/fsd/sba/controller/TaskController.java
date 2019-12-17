/**
 * 
 */
package com.fsd.sba.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fsd.sba.model.Task;
import com.fsd.sba.service.TaskService;

/**
 * @author Rajiniganth Jagadeesan
 *
 *         Rest controller class servicing rest api's for task resource
 */
@RestController
public class TaskController {

	@Autowired
	private TaskService service;

	/*
	 * Rest Api for servicing post request to add/update the task resource
	 * 
	 * @param Task
	 * 
	 * @return Task
	 */
	@PostMapping("/task")
	public Task saveTask(@Valid @RequestBody Task task) {

		return service.saveTask(task);
	}

	/*
	 * Rest Api for servicing get request to retrieve the task resource
	 * 
	 * @param Integer
	 * 
	 * @return Task
	 */
	@GetMapping("/task/{taskId}")
	public Task getTask(@PathVariable Integer taskId) {

		return service.getTask(taskId);
	}

}
