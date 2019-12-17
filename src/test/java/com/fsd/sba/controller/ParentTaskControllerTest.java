package com.fsd.sba.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.fsd.sba.model.ParentTask;
import com.fsd.sba.service.ParentTaskService;

public class ParentTaskControllerTest extends SbaBaseTest{
	
	@MockBean
	private ParentTaskService service;
	
	@Test
	public void testGetParenTaskForProjectSuccess() {

		String responseString = getJsonString("classpath:response/get-parent-task-by-prj-success.json");
		Integer projectId = 1;
		try {
			List<ParentTask> pts = mapFromJsonList(responseString, ParentTask[].class);
			when(service.getParentTaskForProject(projectId)).thenReturn(pts);

			MvcResult result = mockMvc.perform(get("/parent-task/" + projectId).accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk()).andReturn();
			String responseContent = result.getResponse().getContentAsString();
			List<ParentTask> ptList = mapFromJsonList(responseContent, ParentTask[].class);

			assertNotNull(ptList);
			assertFalse(CollectionUtils.isEmpty(ptList));
			assertEquals(ptList.get(0), pts.get(0));
			assertEquals(ptList.get(1), pts.get(1));
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testGetParenTaskForProjectSuccessNoRecords() {

		Integer projectId = 100;
		try {
			List<ParentTask> pts = new ArrayList<ParentTask>();
			when(service.getParentTaskForProject(projectId)).thenReturn(pts);

			MvcResult result = mockMvc.perform(get("/parent-task/" + projectId).accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk()).andReturn();
			String responseContent = result.getResponse().getContentAsString();
			List<ParentTask> ptList = mapFromJsonList(responseContent, ParentTask[].class);

			assertNotNull(ptList);
			assertTrue(CollectionUtils.isEmpty(ptList));
		} catch (Exception e) {
			fail();
		}
	}

}
