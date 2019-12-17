/**
 * 
 */
package com.fsd.sba.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.fsd.sba.AbstractTest;
import com.fsd.sba.entity.TaskEt;
import com.fsd.sba.exception.SbaException;
import com.fsd.sba.exception.SbaResponseCode;
import com.fsd.sba.model.Task;
import com.fsd.sba.repository.TaskRepository;
import com.fsd.sba.transformer.Transformer;

/**
 * @author Rajiniganth Jagadeesan
 *
 */
public class TaskServiceTest extends AbstractTest {
	
	@Mock
	private TaskRepository taskRepository;

	@Mock
	private Transformer transformer;
	
	@InjectMocks
	private TaskService service;
	
	@Test
	public void testSaveParentTaskSuccess() {

		try {
			Task taskReq = mapFromJson(getJsonString("classpath:request/add-task-parent-success-request.json"), Task.class);
			Task task = mapFromJson(getJsonString("classpath:response/add-task-parent-success-response.json"), Task.class);
			TaskEt taskEt = mapFromJson(getJsonString("classpath:request/add-task-parent-entity-success-request.json"), TaskEt.class);
			TaskEt taskEtRtn = mapFromJson(getJsonString("classpath:response/add-task-parent-entity-success-response.json"), TaskEt.class);
			when(transformer.buildTaskEntity(taskReq)).thenReturn(taskEt);
			when(taskRepository.save(taskEt)).thenReturn(taskEtRtn);
			when(transformer.buildTaskModel(taskEtRtn, true)).thenReturn(task);

			Task tskRtn = service.saveTask(taskReq);

			assertNotNull(tskRtn);
			assertEquals(task.getTaskId(), tskRtn.getTaskId());
			assertEquals(task.getTask(), tskRtn.getTask());
			assertEquals(null, tskRtn.getParentTask());
			assertEquals(task.getProject(), tskRtn.getProject());
			assertEquals(task.getUser(), tskRtn.getUser());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testSaveTaskParentFailure() {

		try {
			Task taskReq = mapFromJson(getJsonString("classpath:request/add-task-parent-usr-not-found-failure-request.json"), Task.class);
			when(transformer.buildTaskEntity(taskReq)).thenThrow(new SbaException(SbaResponseCode.USER_NOT_AVAILALE));
			service.saveTask(taskReq);
			fail();
		} catch (SbaException e) {
			assertNotNull(e);
			assertEquals(e.getCode(), SbaResponseCode.USER_NOT_AVAILALE);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testSaveTaskSuccess() {

		try {
			Task taskReq = mapFromJson(getJsonString("classpath:request/add-task-success-request.json"), Task.class);
			Task task = mapFromJson(getJsonString("classpath:response/add-task-success-response.json"), Task.class);
			TaskEt taskEt = mapFromJson(getJsonString("classpath:request/add-task-success-et-request.json"), TaskEt.class);
			TaskEt taskEtRtn = mapFromJson(getJsonString("classpath:response/add-task-success-et-response.json"), TaskEt.class);
			when(transformer.buildTaskEntity(taskReq)).thenReturn(taskEt);
			when(taskRepository.save(taskEt)).thenReturn(taskEtRtn);
			when(transformer.buildTaskModel(taskEtRtn, true)).thenReturn(task);

			Task tskRtn = service.saveTask(taskReq);

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
	public void testGetTaskNoData() {

		try {
			Integer taskId = 101;
			Optional<TaskEt> optinal = Optional.ofNullable(null);
			when(taskRepository.findById(101)).thenReturn(optinal);
			when(transformer.buildTaskModel(null, true)).thenReturn(null);
			
			Task task = service.getTask(taskId);

			assertEquals(null, task);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testGetTaskSuccess() {

		try {
			Integer taskId = 2;
			TaskEt tskEtRtn = mapFromJson(getJsonString("classpath:response/get-task-success-et-response.json"), TaskEt.class);
			Task tskRtn = mapFromJson(getJsonString("classpath:response/get-task-success-response.json"), Task.class);
			Optional<TaskEt> optinal = Optional.of(tskEtRtn);
			when(taskRepository.findById(taskId)).thenReturn(optinal);
			when(transformer.buildTaskModel(tskEtRtn, true)).thenReturn(tskRtn);
			
			Task task = service.getTask(taskId);

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
	
}
