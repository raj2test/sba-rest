package com.fsd.sba.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fsd.sba.model.Project;
import com.fsd.sba.service.ProjectService;

/**
 * @author Rajiniganth Jagadeesan
 * 
 * Rest controller class servicing rest api's for project resource
 *
 */
@RestController
public class ProjectController {
	
	private final Logger log = LoggerFactory.getLogger(ProjectController.class);
	
	@Autowired
	private ProjectService service;

	/*
	 * Rest Api for servicing post request to add/update the project resource
	 * 
	 * @param Project
	 * @return Project
	 */
	@PostMapping("/project")
	public Project saveProject(@Valid @RequestBody Project project) {

		return service.saveProject(project);
	}

	/*
	 * Rest Api for servicing get request to retrieve all project resource
	 * 
	 * @return List<Project>
	 */
	@GetMapping("/project")
	public List<Project> getProjects() {

		return service.getProjects();
	}

	/*
	 * Rest Api for servicing get request to retrieve the project resource
	 * 
	 * @param Integer
	 * @return Project
	 */
	@GetMapping("/project/{projectId}")
	public Project getProject(@PathVariable Integer projectId) {

		return service.getProject(projectId);
	}

	/*
	 * Rest Api for servicing delete request to delete the project resource
	 * 
	 * @param Integer
	 */
	@DeleteMapping("/project/{projectId}")
	public void deleteProject(@PathVariable Integer projectId) {

		service.deleteProject(projectId);
		
		log.info("Project resource with project id({}) is deleted from the system", projectId);
	}

}