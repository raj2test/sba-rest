/**
 * 
 */
package com.fsd.sba.transformer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.fsd.sba.AbstractTest;
import com.fsd.sba.entity.ParentTaskEt;
import com.fsd.sba.entity.ProjectEt;
import com.fsd.sba.entity.TaskEt;
import com.fsd.sba.entity.UserEt;
import com.fsd.sba.exception.SbaException;
import com.fsd.sba.exception.SbaResponseCode;
import com.fsd.sba.model.Project;
import com.fsd.sba.model.Task;
import com.fsd.sba.model.User;
import com.fsd.sba.repository.ParentTaskRepository;
import com.fsd.sba.repository.ProjectRepository;
import com.fsd.sba.repository.TaskRepository;
import com.fsd.sba.repository.UserRepository;

/**
 * @author Rajiniganth Jagadeesan
 *
 */
public class TransformerTest extends AbstractTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private ProjectRepository projectRepository;

	@Mock
	private TaskRepository taskRepository;

	@Mock
	private ParentTaskRepository prTskRepository;

	@InjectMocks
	private Transformer transformer;

	@Test
	public void testBuildUserEntitySucess() {

		User user;
		try {
			user = mapFromJson(getJsonString("classpath:request/add-user-success-request.json"), User.class);
			UserEt userEt = transformer.buildUserEntity(user);
			assertNotNull(userEt);
			assertEquals(userEt.getUserId(), user.getUserId());
			assertEquals(userEt.getFirstName(), user.getFirstName());
			assertEquals(userEt.getLastName(), user.getLastName());
			assertEquals(userEt.getEmployeeId(), user.getEmployeeId());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testBuildUserEntityNull() {

		UserEt userEt = transformer.buildUserEntity(null);
		assertEquals(userEt, null);

	}

	@Test
	public void testBuildUserModelSucess() {

		try {
			UserEt userEt = mapFromJson(getJsonString("classpath:request/add-user-success-request.json"), UserEt.class);
			User user = transformer.buildUserModel(userEt);
			assertNotNull(userEt);
			assertEquals(userEt.getUserId(), user.getUserId());
			assertEquals(userEt.getFirstName(), user.getFirstName());
			assertEquals(userEt.getLastName(), user.getLastName());
			assertEquals(userEt.getEmployeeId(), user.getEmployeeId());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testBuildUserModelNull() {

		User user = transformer.buildUserModel(null);
		assertEquals(user, null);
	}

	@Test
	public void testBuildProjectModelSuccess() {

		try {
			ProjectEt prjEt = mapFromJson(getJsonString("classpath:response/add-project-success-response.json"),
					ProjectEt.class);
			Project project = transformer.buildProjectModel(prjEt, true);

			assertNotNull(project);
			assertEquals(project.getProjectId(), prjEt.getProjectId());
			assertEquals(project.getProject(), prjEt.getProject());
			assertEquals(project.getStartDate(), prjEt.getStartDate());
			assertEquals(project.getEndDate(), prjEt.getEndDate());
			assertEquals(project.getPriority(), prjEt.getPriority());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testBuildProjectModelForNull() {

		try {
			Project project = transformer.buildProjectModel(null, true);

			assertEquals(project, null);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testBuildProjectEntitySuccessForUpdateScenario() {

		try {
			Project prjReq = mapFromJson(getJsonString("classpath:response/add-project-success-response.json"),
					Project.class);
			ProjectEt prjEtReq = mapFromJson(getJsonString("classpath:response/add-project-success-response.json"),
					ProjectEt.class);
			Optional<ProjectEt> optPrjEt = Optional.of(prjEtReq);
			when(projectRepository.findById(prjReq.getProjectId())).thenReturn(optPrjEt);
			Optional<UserEt> optUsrEt = Optional.of(prjEtReq.getUser());
			when(userRepository.findById(prjReq.getUser().getUserId())).thenReturn(optUsrEt);

			ProjectEt prjRtn = transformer.buildProjectEntity(prjReq);

			assertNotNull(prjRtn);
			assertEquals(prjEtReq.getProjectId(), prjRtn.getProjectId());
			assertEquals(prjEtReq.getProject(), prjRtn.getProject());
			assertEquals(prjEtReq.getStartDate(), prjRtn.getStartDate());
			assertEquals(prjEtReq.getEndDate(), prjRtn.getEndDate());
			assertEquals(prjEtReq.getPriority(), prjRtn.getPriority());
			assertEquals(prjEtReq.getUser(), prjRtn.getUser());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testBuildProjectEntitySuccessForUpdateOrphanScenario() {

		try {
			Project prjReq = mapFromJson(getJsonString("classpath:response/add-project-success-response.json"),
					Project.class);

			ProjectEt prjEtReq = mapFromJson(
					getJsonString("classpath:response/update-prj-orphan-success-response.json"), ProjectEt.class);
			UserEt userEt = mapFromJson(getJsonString("classpath:response/add-user-success-response.json"),
					UserEt.class);

			Optional<ProjectEt> optPrjEt = Optional.of(prjEtReq);
			when(projectRepository.findById(prjReq.getProjectId())).thenReturn(optPrjEt);
			Optional<UserEt> optUsrEt = Optional.of(userEt);
			when(userRepository.findById(prjReq.getUser().getUserId())).thenReturn(optUsrEt);

			ProjectEt prjRtn = transformer.buildProjectEntity(prjReq);

			assertNotNull(prjRtn);
			assertEquals(prjEtReq.getProjectId(), prjRtn.getProjectId());
			assertEquals(prjEtReq.getProject(), prjRtn.getProject());
			assertEquals(prjEtReq.getStartDate(), prjRtn.getStartDate());
			assertEquals(prjEtReq.getEndDate(), prjRtn.getEndDate());
			assertEquals(prjEtReq.getPriority(), prjRtn.getPriority());
			assertEquals(prjEtReq.getUser(), prjRtn.getUser());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testBuildProjectEntitySuccessForSaveScenario() {

		try {
			Project prjReq = mapFromJson(getJsonString("classpath:request/add-project-success-request.json"),
					Project.class);
			UserEt userEt = mapFromJson(getJsonString("classpath:response/add-user-success-response.json"),
					UserEt.class);
			Optional<ProjectEt> optPrjEt = Optional.ofNullable(null);
			when(projectRepository.findById(prjReq.getProjectId())).thenReturn(optPrjEt);
			Optional<UserEt> optUsrEt = Optional.of(userEt);
			when(userRepository.findById(prjReq.getUser().getUserId())).thenReturn(optUsrEt);

			ProjectEt prjRtn = transformer.buildProjectEntity(prjReq);

			assertNotNull(prjRtn);
			assertEquals(prjReq.getProject(), prjRtn.getProject());
			assertEquals(prjReq.getStartDate(), prjRtn.getStartDate());
			assertEquals(prjReq.getEndDate(), prjRtn.getEndDate());
			assertEquals(prjReq.getPriority(), prjRtn.getPriority());
			assertEquals(prjReq.getUser().getUserId(), prjRtn.getUser().getUserId());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testBuildProjectEntityFailureManagerUnavailable() {

		try {
			Project prjReq = mapFromJson(getJsonString("classpath:request/add-project-success-request.json"),
					Project.class);
			UserEt userEt = new UserEt();
			userEt.setUserId(1);
			ProjectEt prjEt = new ProjectEt();
			prjEt.setProjectId(2);
			userEt.setProject(prjEt);
			Optional<ProjectEt> optPrjEt = Optional.ofNullable(null);
			when(projectRepository.findById(prjReq.getProjectId())).thenReturn(optPrjEt);
			Optional<UserEt> optUsrEt = Optional.of(userEt);
			when(userRepository.findById(prjReq.getUser().getUserId())).thenReturn(optUsrEt);

			transformer.buildProjectEntity(prjReq);

			fail();
		} catch (SbaException e) {
			assertNotNull(e);
			assertEquals(e.getCode(), SbaResponseCode.USER_OCCUPIED);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testBuildProjectEntityForNull() {

		try {
			ProjectEt prjET = transformer.buildProjectEntity(null);

			assertEquals(prjET, null);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testBuildTaskEntityForParentSuccess() {

		try {
			Task taskReq = mapFromJson(getJsonString("classpath:request/add-task-parent-success-request.json"),
					Task.class);
			TaskEt taskEtRtn = mapFromJson(
					getJsonString("classpath:response/add-task-parent-entity-success-response.json"), TaskEt.class);
			ParentTaskEt ptEt = mapFromJson(getJsonString("classpath:request/add-parent-task-req-success.json"),
					ParentTaskEt.class);
			ParentTaskEt ptEtRes = mapFromJson(
					getJsonString("classpath:response/add-parent-task-response-success.json"), ParentTaskEt.class);

			when(prTskRepository.save(ptEt)).thenReturn(ptEtRes);
			Optional<UserEt> optUserEt = Optional.of(
					mapFromJson(getJsonString("classpath:response/get-user-et-success-response.json"), UserEt.class));
			when(userRepository.findById(taskReq.getUser().getUserId())).thenReturn(optUserEt);
			Optional<ProjectEt> optPrjEt = Optional.of(taskEtRtn.getProject());
			when(projectRepository.findById(taskReq.getProject().getProjectId())).thenReturn(optPrjEt);

			TaskEt tskRtn = transformer.buildTaskEntity(taskReq);

			assertNotNull(tskRtn);
			assertEquals(taskEtRtn.getTask(), tskRtn.getTask());
			assertEquals(null, tskRtn.getParentTask());
			assertEquals(taskEtRtn.getProject(), tskRtn.getProject());
			assertEquals(taskEtRtn.getUser(), tskRtn.getUser());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testBuildTaskEntityUserNotAvailableFailure() {

		try {
			Task taskReq = mapFromJson(getJsonString("classpath:request/add-task-parent-success-request.json"),
					Task.class);
			ParentTaskEt ptEt = mapFromJson(getJsonString("classpath:request/add-parent-task-req-success.json"),
					ParentTaskEt.class);
			ParentTaskEt ptEtRes = mapFromJson(
					getJsonString("classpath:response/add-parent-task-response-success.json"), ParentTaskEt.class);

			when(prTskRepository.save(ptEt)).thenReturn(ptEtRes);
			Optional<UserEt> optUserEt = Optional.ofNullable(null);
			when(userRepository.findById(taskReq.getUser().getUserId())).thenReturn(optUserEt);

			transformer.buildTaskEntity(taskReq);
			fail();

		} catch (SbaException e) {
			assertNotNull(e);
			assertEquals(e.getCode(), SbaResponseCode.USER_NOT_AVAILALE);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testBuildTaskEntityUserOccupiedFailure() {

		try {
			Task taskReq = mapFromJson(getJsonString("classpath:request/add-task-parent-success-request.json"),
					Task.class);
			ParentTaskEt ptEt = mapFromJson(getJsonString("classpath:request/add-parent-task-req-success.json"),
					ParentTaskEt.class);
			ParentTaskEt ptEtRes = mapFromJson(
					getJsonString("classpath:response/add-parent-task-response-success.json"), ParentTaskEt.class);
			
			when(prTskRepository.save(ptEt)).thenReturn(ptEtRes);
			Optional<UserEt> optUserEt = Optional.of(
					mapFromJson(getJsonString("classpath:response/get-user-et-occupied-response.json"), UserEt.class));;
			when(userRepository.findById(taskReq.getUser().getUserId())).thenReturn(optUserEt);

			transformer.buildTaskEntity(taskReq);
			fail();

		} catch (SbaException e) {
			assertNotNull(e);
			assertEquals(e.getCode(), SbaResponseCode.USER_OCCUPIED);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testBuildTaskEntityProjectNotAvailableFailure() {

		try {
			Task taskReq = mapFromJson(getJsonString("classpath:request/add-task-parent-success-request.json"),
					Task.class);
			ParentTaskEt ptEt = mapFromJson(getJsonString("classpath:request/add-parent-task-req-success.json"),
					ParentTaskEt.class);
			ParentTaskEt ptEtRes = mapFromJson(
					getJsonString("classpath:response/add-parent-task-response-success.json"), ParentTaskEt.class);

			when(prTskRepository.save(ptEt)).thenReturn(ptEtRes);
			Optional<UserEt> optUserEt = Optional.of(
					mapFromJson(getJsonString("classpath:response/get-user-et-success-response.json"), UserEt.class));
			when(userRepository.findById(taskReq.getUser().getUserId())).thenReturn(optUserEt);
			Optional<ProjectEt> optPrjEt = Optional.ofNullable(null);
			when(projectRepository.findById(taskReq.getProject().getProjectId())).thenReturn(optPrjEt);

			transformer.buildTaskEntity(taskReq);
			fail();
		} catch (SbaException e) {
			assertNotNull(e);
			assertEquals(e.getCode(), SbaResponseCode.PROJECT_NOT_FOUND);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testUpdateTaskSuccess() {
		
		try {
			Task taskReq = mapFromJson(getJsonString("classpath:request/update-task-request-success.json"),
					Task.class);;
			TaskEt taskEtRtn = mapFromJson(
					getJsonString("classpath:response/add-task-success-et-response.json"), TaskEt.class);
			Optional<TaskEt> optTaskEt = Optional.of(taskEtRtn);
			when(taskRepository.findById(taskReq.getTaskId())).thenReturn(optTaskEt);
			Optional<ParentTaskEt> optPtEt = Optional.of(taskEtRtn.getParentTask());
			when(prTskRepository.findById(taskReq.getParentTask().getParentId())).thenReturn(optPtEt);
			Optional<UserEt> optUserEt = Optional.of(
					mapFromJson(getJsonString("classpath:response/get-user-et-success-response.json"), UserEt.class));
			when(userRepository.findById(taskReq.getUser().getUserId())).thenReturn(optUserEt);
			Optional<ProjectEt> optPrjEt = Optional.of(taskEtRtn.getProject());
			when(projectRepository.findById(taskReq.getProject().getProjectId())).thenReturn(optPrjEt);
			
			TaskEt tskRtn = transformer.buildTaskEntity(taskReq);

			assertNotNull(tskRtn);
			assertEquals(taskEtRtn.getTaskId(), tskRtn.getTaskId());
			assertEquals(taskEtRtn.getTask(), tskRtn.getTask());
			assertEquals(taskEtRtn.getParentTask(), tskRtn.getParentTask());
			assertEquals(taskEtRtn.getProject(), tskRtn.getProject());
			assertEquals(taskEtRtn.getUser(), tskRtn.getUser());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testUpdateTaskInvalidTaskIdFailure() {
		
		try {
			Task taskReq = mapFromJson(getJsonString("classpath:request/update-task-invalid-taskId-request.json"),
					Task.class);
			Optional<TaskEt> optTaskEt = Optional.ofNullable(null);
			when(taskRepository.findById(taskReq.getTaskId())).thenReturn(optTaskEt);

			transformer.buildTaskEntity(taskReq);
			fail();
		} catch (SbaException e) {
			assertNotNull(e);
			assertEquals(e.getCode(), SbaResponseCode.TASK_NOT_FOUND);
		} catch (Exception e) {
			fail();
		}
		
	}
	
	@Test
	public void testBuildTaskModelSuccess() {
		
		try {
			TaskEt taskEtReq = mapFromJson(getJsonString("classpath:response/add-task-success-et-response.json"),
					TaskEt.class);
			Task task = mapFromJson(getJsonString("classpath:response/add-task-success-response.json"),
					Task.class);

			Task taskRtn = transformer.buildTaskModel(taskEtReq, true);
			
			assertNotNull(taskRtn);
			assertEquals(task.getTaskId(), taskRtn.getTaskId());
			assertEquals(task.getTask(), taskRtn.getTask());
			assertEquals(task.getParentTask(), taskRtn.getParentTask());
			assertEquals(task.getProject(), taskRtn.getProject());
			assertEquals(task.getUser(), taskRtn.getUser());
		} catch (Exception e) {
			fail();
		}
	}

}
