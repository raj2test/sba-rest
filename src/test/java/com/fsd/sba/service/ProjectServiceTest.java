package com.fsd.sba.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.util.CollectionUtils;

import com.fsd.sba.AbstractTest;
import com.fsd.sba.entity.ProjectEt;
import com.fsd.sba.exception.SbaException;
import com.fsd.sba.exception.SbaResponseCode;
import com.fsd.sba.model.Project;
import com.fsd.sba.repository.ProjectRepository;
import com.fsd.sba.repository.UserRepository;
import com.fsd.sba.transformer.Transformer;

public class ProjectServiceTest extends AbstractTest {

	@Mock
	private ProjectRepository repository;
	
	@Mock
	private UserRepository userRepository;

	@Mock
	private Transformer transformer;

	@InjectMocks
	private ProjectService service;

	@Test
	public void testSaveProjectSuccess() {

		String requestString = getJsonString("classpath:request/add-project-success-request.json");
		String responseString = getJsonString("classpath:response/add-project-success-response.json");

		try {
			Project prjReq = mapFromJson(requestString, Project.class);
			Project prjRtn = mapFromJson(responseString, Project.class);
			ProjectEt prjEtReq = mapFromJson(requestString, ProjectEt.class);
			ProjectEt prjEtRes = mapFromJson(responseString, ProjectEt.class);
			when(transformer.buildProjectEntity(prjReq)).thenReturn(prjEtReq);
			when(repository.save(prjEtReq)).thenReturn(prjEtRes);
			when(transformer.buildProjectModel(prjEtRes, true)).thenReturn(prjRtn);

			Project project = service.saveProject(prjReq);

			assertNotNull(project);
			assertEquals(project.getProjectId(), prjRtn.getProjectId());
			assertEquals(project.getProject(), prjRtn.getProject());
			assertEquals(project.getStartDate(), prjRtn.getStartDate());
			assertEquals(project.getEndDate(), prjRtn.getEndDate());
			assertEquals(project.getPriority(), prjRtn.getPriority());
			assertEquals(project.getUser(), prjRtn.getUser());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testSaveProjectFailureWithoutPrjName() {

		try {
			String requestString = getJsonString("classpath:request/add-project-success-request.json");
			Project prjReq = mapFromJson(requestString, Project.class);
			ProjectEt prjEtReq = mapFromJson(requestString, ProjectEt.class);
			when(transformer.buildProjectEntity(prjReq)).thenReturn(prjEtReq);
			when(repository.save(prjEtReq))
					.thenThrow(new SQLIntegrityConstraintViolationException("Porject: project is mandatory"));
			service.saveProject(prjReq);
			fail();
		} catch (Exception e) {
			assertNotNull(e);
		}
	}

	@Test
	public void testGetProjectsSuccess() {

		try {
			List<ProjectEt> prjList = mapFromJsonList(
					getJsonString("classpath:response/get-projects-entity-response.json"), ProjectEt[].class);
			List<Project> projects = mapFromJsonList(
					getJsonString("classpath:response/get-projects-success-response.json"), Project[].class);
			when(repository.findAll()).thenReturn(prjList);
			when(transformer.buildProjectModel(prjList.get(0), true)).thenReturn(projects.get(0));

			List<Project> userList = service.getProjects();

			assertNotNull(userList);
			assertFalse(CollectionUtils.isEmpty(userList));
			assertEquals(userList.get(0), projects.get(0));
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetProjectsNoDataFound() {

		try {
			List<ProjectEt> projects = new ArrayList<ProjectEt>();
			when(repository.findAll()).thenReturn(projects);

			List<Project> projectList = service.getProjects();

			assertNotNull(projectList);
			assertTrue(CollectionUtils.isEmpty(projectList));
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetProjectSuccess() {

		try {
			Integer projectId = 1;
			ProjectEt prjEt = mapFromJson(getJsonString("classpath:response/get-project-entity-response.json"),
					ProjectEt.class);
			Optional<ProjectEt> optinal = Optional.of(prjEt);
			Project project = mapFromJson(getJsonString("classpath:response/get-project-success-response.json"),
					Project.class);
			when(repository.findById(projectId)).thenReturn(optinal);
			when(transformer.buildProjectModel(prjEt, true)).thenReturn(project);

			Project prjRes = service.getProject(projectId);

			assertNotNull(prjRes);
			assertEquals(prjRes, project);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetProjectNoDataFound() {

		try {
			Optional<ProjectEt> optinal = Optional.ofNullable(null);
			when(repository.findById(101)).thenReturn(optinal);
			when(transformer.buildProjectModel(null, false)).thenReturn(null);

			Project prjRes = service.getProject(1);

			assertEquals(null, prjRes);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testDeleteProjectSuccess() {

		try {
			Integer projectId = 1;
			ProjectEt prjEt = mapFromJson(getJsonString("classpath:response/get-project-entity-response.json"),
					ProjectEt.class);
			Optional<ProjectEt> optinal = Optional.of(prjEt);
			when(repository.findById(projectId)).thenReturn(optinal);
			when(userRepository.save(prjEt.getUser())).thenReturn(prjEt.getUser());
			
			doNothing().when(repository).deleteById(projectId);

			service.deleteProject(projectId);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testDeleteProjectFailureWhenNoDataAvailable() {

		try {
			Integer projectId = 101;
			Optional<ProjectEt> optinal = Optional.ofNullable(null);
			when(repository.findById(projectId)).thenReturn(optinal);

			service.deleteProject(projectId);
		} catch (SbaException e) {
			assertNotNull(e);
			assertEquals(e.getCode(), SbaResponseCode.DATA_NOT_AVAILABLE_FOR_DELETE);
		} catch (Exception e) {
			fail();
		}
	}


}
