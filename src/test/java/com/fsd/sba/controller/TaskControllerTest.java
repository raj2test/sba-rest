package com.fsd.sba.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.fsd.sba.model.Task;
import com.fsd.sba.service.TaskService;

public class TaskControllerTest extends SbaBaseTest {
	
	@MockBean
	private TaskService service;
	
	@Test
	public void testSaveParentTaskSuccess() {

		String requestString = getJsonString("classpath:request/add-task-parent-success-request.json");
		String responseString = getJsonString("classpath:response/add-task-parent-success-response.json");

		try {
			Task tskRtn = mapFromJson(responseString, Task.class);
			when(service.saveTask(mapFromJson(requestString, Task.class))).thenReturn(tskRtn);

			MvcResult result = mockMvc.perform(post("/task").contentType(MediaType.APPLICATION_JSON)
					.content(requestString).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
			String responseContent = result.getResponse().getContentAsString();
			Task task = mapFromJson(responseContent, Task.class);

			assertNotNull(task);
			assertEquals(task.getTaskId(), tskRtn.getTaskId());
			assertEquals(task.getTask(), tskRtn.getTask());
			assertEquals(null, task.getParentTask());
			assertEquals(task.getProject(), task.getProject());
			assertEquals(task.getUser(), tskRtn.getUser());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testSaveTaskParentFailure() {

		String requestString = getJsonString("classpath:request/add-task-parent-failure-request.json");

		try {
			mockMvc.perform(post("/task").contentType(MediaType.APPLICATION_JSON)
					.content(requestString).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testSaveTaskSuccess() {

		String requestString = getJsonString("classpath:request/add-task-success-request.json");
		String responseString = getJsonString("classpath:response/add-task-success-response.json");

		try {
			Task tskRtn = mapFromJson(responseString, Task.class);
			when(service.saveTask(mapFromJson(requestString, Task.class))).thenReturn(tskRtn);

			MvcResult result = mockMvc.perform(post("/task").contentType(MediaType.APPLICATION_JSON)
					.content(requestString).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
			String responseContent = result.getResponse().getContentAsString();
			Task task = mapFromJson(responseContent, Task.class);

			assertNotNull(task);
			assertEquals(task.getTaskId(), tskRtn.getTaskId());
			assertEquals(task.getTask(), tskRtn.getTask());
			assertEquals(task.getStartDate(), tskRtn.getStartDate());
			assertEquals(task.getEndDate(), tskRtn.getEndDate());
			assertEquals(task.getStatus(), tskRtn.getStatus());
			assertEquals(task.getPriority(), tskRtn.getPriority());
			assertEquals(task.getParentTask(), tskRtn.getParentTask());
			assertEquals(task.getProject(), task.getProject());
			assertEquals(task.getUser(), tskRtn.getUser());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testSaveTaskFailureDueToTaskDts() {

		String requestString = getJsonString("classpath:request/add-task-failure-request.json");

		try {
			mockMvc.perform(post("/task").contentType(MediaType.APPLICATION_JSON)
					.content(requestString).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testSaveTaskFailureDueToPriorityRange() {

		String requestString = getJsonString("classpath:request/add-task-failure-priority-range-request.json");

		try {
			mockMvc.perform(post("/task").contentType(MediaType.APPLICATION_JSON)
					.content(requestString).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testGetTaskSuccess() {

		String responseString = getJsonString("classpath:response/get-task-success-response.json");

		try {
			Task tskRtn = mapFromJson(responseString, Task.class);
			when(service.getTask(2)).thenReturn(tskRtn);

			MvcResult result = mockMvc.perform(get("/task/2").accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk()).andReturn();
			String responseContent = result.getResponse().getContentAsString();
			Task task = mapFromJson(responseContent, Task.class);

			assertNotNull(task);
			assertEquals(task.getTaskId(), tskRtn.getTaskId());
			assertEquals(task.getTask(), tskRtn.getTask());
			assertEquals(task.getStartDate(), tskRtn.getStartDate());
			assertEquals(task.getEndDate(), tskRtn.getEndDate());
			assertEquals(task.getStatus(), tskRtn.getStatus());
			assertEquals(task.getPriority(), tskRtn.getPriority());
			assertEquals(task.getParentTask(), tskRtn.getParentTask());
			assertEquals(task.getProject(), task.getProject());
			assertEquals(task.getUser(), tskRtn.getUser());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testGetTaskSuccessNoRecordFound() {

		try {
			when(service.getTask(20)).thenReturn(null);

			MvcResult result = mockMvc.perform(get("/task/20").accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk()).andReturn();
			String responseContent = result.getResponse().getContentAsString();

			assertTrue(responseContent.isEmpty());

		} catch (Exception e) {
			fail();
		}
	}

}
