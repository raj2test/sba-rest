package com.fsd.sba.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fsd.sba.entity.ProjectEt;
import com.fsd.sba.entity.UserEt;
import com.fsd.sba.exception.SbaException;
import com.fsd.sba.exception.SbaResponseCode;
import com.fsd.sba.model.Project;
import com.fsd.sba.repository.ProjectRepository;
import com.fsd.sba.repository.UserRepository;
import com.fsd.sba.transformer.Transformer;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Transformer transformer;

	@Transactional
	public Project saveProject(Project project) {

		ProjectEt projectEt = transformer.buildProjectEntity(project);

		projectEt = projectRepository.save(projectEt);

		return transformer.buildProjectModel(projectEt, true);
	}

	/**
	 * Service method to get the project resource
	 * 
	 * @param projectId
	 * @return Project
	 */
	public Project getProject(Integer projectId) {

		ProjectEt projEt = projectRepository.findById(projectId).orElse(null);

		return transformer.buildProjectModel(projEt, true);
	}

	/**
	 * @return List<Project>
	 */
	public List<Project> getProjects() {

		List<ProjectEt> projectList = projectRepository.findAll();
		List<Project> projects = new ArrayList<Project>();
		for (ProjectEt prj : projectList) {
			projects.add(transformer.buildProjectModel(prj, true));
		}

		return projects;
	}

	/**
	 * @param projectId
	 */
	public void deleteProject(Integer projectId) {

		ProjectEt prjEt = projectRepository.findById(projectId).orElse(null);

		if (null != prjEt) {
			UserEt user = prjEt.getUser();
			if (null != user) {
				user.setProject(null);
				userRepository.save(user);
			}
			projectRepository.delete(prjEt);
		} else {
			throw new SbaException(SbaResponseCode.DATA_NOT_AVAILABLE_FOR_DELETE);
		}
	}

}
