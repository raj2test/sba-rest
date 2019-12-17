package com.fsd.sba.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.util.CollectionUtils;

import com.fsd.sba.AbstractTest;
import com.fsd.sba.entity.ParentTaskEt;
import com.fsd.sba.model.ParentTask;
import com.fsd.sba.repository.ParentTaskRepository;
import com.fsd.sba.transformer.Transformer;

public class ParentTaskServiceTest extends AbstractTest {

	@Mock
	private ParentTaskRepository repository;

	@Mock
	private Transformer transformer;

	@InjectMocks
	private ParentTaskService service;

	@Test
	public void testGetParentTaskForProjectSuccess() {

		try {
			Integer projectId = 1;
			List<ParentTaskEt> ptsList = mapFromJsonList(
					getJsonString("classpath:response/get-parent-task-by-prj-success.json"), ParentTaskEt[].class);
			List<ParentTask> pts = mapFromJsonList(
					getJsonString("classpath:response/get-parent-task-by-prj-success.json"), ParentTask[].class);
			when(repository.findByProjectId(projectId)).thenReturn(ptsList);
			when(transformer.buildParentTaskModel(ptsList.get(0))).thenReturn(pts.get(0));
			when(transformer.buildParentTaskModel(ptsList.get(1))).thenReturn(pts.get(1));

			List<ParentTask> parentTasks = service.getParentTaskForProject(projectId);

			assertNotNull(parentTasks);
			assertFalse(CollectionUtils.isEmpty(parentTasks));
			assertEquals(pts.get(0), parentTasks.get(0));
			assertEquals(pts.get(1), parentTasks.get(1));
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetParentTaskForProjectSuccessNoRecords() {

		try {
			Integer projectId = 1;
			List<ParentTaskEt> ptsList = null;
			when(repository.findByProjectId(projectId)).thenReturn(ptsList);

			List<ParentTask> parentTasks = service.getParentTaskForProject(projectId);

			assertNotNull(parentTasks);
			assertTrue(CollectionUtils.isEmpty(parentTasks));
		} catch (Exception e) {
			fail();
		}
	}

}
