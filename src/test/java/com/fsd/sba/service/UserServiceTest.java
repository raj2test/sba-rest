/**
 * 
 */
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

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.util.CollectionUtils;

import com.fsd.sba.AbstractTest;
import com.fsd.sba.constant.UserOption;
import com.fsd.sba.entity.UserEt;
import com.fsd.sba.exception.SbaException;
import com.fsd.sba.exception.SbaResponseCode;
import com.fsd.sba.model.User;
import com.fsd.sba.repository.UserRepository;
import com.fsd.sba.transformer.Transformer;

/**
 * @author Rajiniganth Jagadeesan
 *
 */
public class UserServiceTest extends AbstractTest {

	@Mock
	private UserRepository repository;

	@Mock
	private Transformer transformer;

	@InjectMocks
	private UserService service;

	@Test
	public void testSaveUserSuccess() {

		try {
			User userRequest = mapFromJson(getJsonString("classpath:request/add-user-success-request.json"),
					User.class);
			UserEt userReqEt = mapFromJson(getJsonString("classpath:request/add-user-success-request.json"),
					UserEt.class);
			UserEt userEtRtn = mapFromJson(getJsonString("classpath:response/add-user-success-response.json"),
					UserEt.class);
			User userRtn = mapFromJson(getJsonString("classpath:response/add-user-success-response.json"), User.class);
			when(repository.save(userReqEt)).thenReturn(userEtRtn);
			when(transformer.buildUserEntity(userRequest)).thenReturn(userReqEt);
			when(transformer.buildUserModel(userEtRtn)).thenReturn(userRtn);

			User user = service.saveUser(userRequest);
			assertNotNull(user);
			assertEquals(user.getUserId(), userRtn.getUserId());
			assertEquals(user.getFirstName(), userRtn.getFirstName());
			assertEquals(user.getLastName(), userRtn.getLastName());
			assertEquals(user.getEmployeeId(), userRtn.getEmployeeId());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testSaveUserFailureWithoutFirstName() {

		try {
			User userRequest = mapFromJson(getJsonString("classpath:request/add-user-success-request.json"),
					User.class);
			UserEt userReqEt = mapFromJson(getJsonString("classpath:request/add-user-success-request.json"),
					UserEt.class);
			when(repository.save(userReqEt))
					.thenThrow(new SQLIntegrityConstraintViolationException("User: FirstName is mandatory"));
			when(transformer.buildUserEntity(userRequest)).thenReturn(userReqEt);
			service.saveUser(userRequest);
			fail();
		} catch (Exception e) {
			assertNotNull(e);
		}
	}

	@Test
	public void testGetUsersSuccess() {

		try {
			List<UserEt> userEts = mapFromJsonList(getJsonString("classpath:response/get-users-success-response.json"),
					UserEt[].class);
			List<User> users = mapFromJsonList(getJsonString("classpath:response/get-users-success-response.json"),
					User[].class);
			when(repository.findAll()).thenReturn(userEts);
			when(transformer.buildUserModel(userEts.get(0))).thenReturn(users.get(0));
			when(transformer.buildUserModel(userEts.get(1))).thenReturn(users.get(1));

			List<User> userList = service.getUsers(UserOption.ALL);

			assertNotNull(userList);
			assertFalse(CollectionUtils.isEmpty(userList));
			assertEquals(userList.get(0), users.get(0));
			assertEquals(userList.get(1), users.get(1));
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetUsersNoDataFound() {

		try {
			List<UserEt> users = new ArrayList<UserEt>();
			when(repository.findAll()).thenReturn(users);

			List<User> userList = service.getUsers(UserOption.ALL);

			assertNotNull(userList);
			assertTrue(CollectionUtils.isEmpty(userList));
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testDeleteUserSuccess() {

		try {
			Integer userId = 1;
			when(repository.existsById(userId)).thenReturn(true);
			doNothing().when(repository).deleteById(userId);

			service.deleteUser(userId);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testDeleteUserFailureWhenNoDataAvailable() {

		try {
			Integer userId = 1;
			when(repository.existsById(1)).thenReturn(false);

			service.deleteUser(userId);
		} catch (SbaException e) {
			assertNotNull(e);
			assertEquals(e.getCode(), SbaResponseCode.DATA_NOT_AVAILABLE_FOR_DELETE);
		} catch (Exception e) {
			fail();
		}
	}

}
