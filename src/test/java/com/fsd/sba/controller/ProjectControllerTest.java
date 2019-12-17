package com.fsd.sba.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.CollectionUtils;

import com.fsd.sba.exception.SbaException;
import com.fsd.sba.exception.SbaResponseCode;
import com.fsd.sba.model.Project;
import com.fsd.sba.service.ProjectService;

/**
 * @author Rajiniganth Jagadeesan
 * 
 *         Test class for testing rest api's for project resource
 *
 */
public class ProjectControllerTest extends SbaBaseTest {

	@MockBean
	private ProjectService service;

	@Test
	public void testSaveProjectSuccess() {

		String requestString = getJsonString("classpath:request/add-project-success-request.json");
		String responseString = getJsonString("classpath:response/add-project-success-response.json");

		try {
			Project prjRtn = mapFromJson(responseString, Project.class);
			when(service.saveProject(mapFromJson(requestString, Project.class))).thenReturn(prjRtn);

			MvcResult result = mockMvc.perform(post("/project").contentType(MediaType.APPLICATION_JSON)
					.content(requestString).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
			String responseContent = result.getResponse().getContentAsString();
			Project project = mapFromJson(responseContent, Project.class);

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
	public void testSaveProjectFailureDueToInvalidProjectDts() {

		String requestString = getJsonString("classpath:request/add-project-failure-request.json");

		try {

			mockMvc.perform(post("/project").contentType(MediaType.APPLICATION_JSON).content(requestString)
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testSaveProjectFailureDuePriorityRange() {

		String requestString = getJsonString("classpath:request/add-project-failure-priorit-range-request.json");

		try {

			mockMvc.perform(post("/project").contentType(MediaType.APPLICATION_JSON).content(requestString)
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetProjectsSuccess() {

		String responseString = getJsonString("classpath:response/get-projects-success-response.json");

		try {
			List<Project> projects = mapFromJsonList(responseString, Project[].class);
			when(service.getProjects()).thenReturn(projects);

			MvcResult result = mockMvc.perform(get("/project").accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk()).andReturn();
			String responseContent = result.getResponse().getContentAsString();
			List<Project> projectList = mapFromJsonList(responseContent, Project[].class);

			assertNotNull(projectList);
			assertFalse(CollectionUtils.isEmpty(projectList));
			assertEquals(projectList.get(0), projects.get(0));
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetProjectsSuccessWithNoRecords() {

		try {
			List<Project> projects = new ArrayList<Project>();
			when(service.getProjects()).thenReturn(projects);

			MvcResult result = mockMvc.perform(get("/project").accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk()).andReturn();
			String responseContent = result.getResponse().getContentAsString();
			List<Project> projectList = mapFromJsonList(responseContent, Project[].class);

			assertNotNull(projectList);
			assertTrue(CollectionUtils.isEmpty(projectList));
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetProjectSuccess() {

		String responseString = getJsonString("classpath:response/get-project-success-response.json");

		try {
			Project project = mapFromJson(responseString, Project.class);
			when(service.getProject(1)).thenReturn(project);

			MvcResult result = mockMvc.perform(get("/project/1").accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk()).andReturn();
			String responseContent = result.getResponse().getContentAsString();
			Project prjRes = mapFromJson(responseContent, Project.class);

			assertNotNull(prjRes);
			assertEquals(prjRes.getProjectId(), project.getProjectId());
			assertEquals(prjRes.getProject(), project.getProject());
			assertEquals(prjRes.getPriority(), project.getPriority());
			assertEquals(prjRes.getStartDate(), project.getStartDate());
			assertEquals(prjRes.getEndDate(), project.getEndDate());
			assertEquals(prjRes.getTasks(), project.getTasks());
			assertEquals(prjRes.getUser(), project.getUser());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetProjectSuccessNoRecordFound() {

		try {
			when(service.getProject(20)).thenReturn(null);

			MvcResult result = mockMvc.perform(get("/project/20").accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk()).andReturn();
			String responseContent = result.getResponse().getContentAsString();

			assertTrue(responseContent.isEmpty());

		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testDeleteProjectSuccess() {

		try {
			doNothing().when(service).deleteProject(1);

			mockMvc.perform(delete("/project/1")).andExpect(status().isOk()).andReturn();
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testDeleteProjectFailureWhenNoDataAvailable() {

		try {
			doThrow(new SbaException(SbaResponseCode.DATA_NOT_AVAILABLE_FOR_DELETE)).when(service).deleteProject(101);

			mockMvc.perform(delete("/project/101")).andExpect(status().isNotFound()).andReturn();
		} catch (Exception e) {
			fail();
		}
	}

}
