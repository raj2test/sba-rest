package com.fsd.sba.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fsd.sba.model.ParentTask;
import com.fsd.sba.service.ParentTaskService;

@RestController
public class ParentTaskController {
	
	@Autowired
	private ParentTaskService service;
	
	@GetMapping("/parent-task/{projectId}")
	public List<ParentTask> getParentTaskForProject(@PathVariable Integer projectId) {

		return service.getParentTaskForProject(projectId);
	}

}
