/**
 * 
 */
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

import com.fsd.sba.constant.UserOption;
import com.fsd.sba.exception.SbaException;
import com.fsd.sba.exception.SbaResponseCode;
import com.fsd.sba.model.User;
import com.fsd.sba.service.UserService;

/**
 * @author Rajiniganth Jagadeesan
 *
 * Test class for test rest api's for user resource
 */
public class UserControllerTest extends SbaBaseTest {

	@MockBean
	private UserService service;

	@Test
	public void testSaveUserSuccess() {

		String requestString = getJsonString("classpath:request/add-user-success-request.json");
		String responseString = getJsonString("classpath:response/add-user-success-response.json");

		try {
			User userRtn = mapFromJson(responseString, User.class);
			when(service.saveUser(mapFromJson(requestString, User.class))).thenReturn(userRtn);

			MvcResult result = mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
					.content(requestString).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
			String responseContent = result.getResponse().getContentAsString();
			User user = mapFromJson(responseContent, User.class);

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
	public void testSaveUserFailure() {

		String requestString = getJsonString("classpath:request/add-user-failure-request.json");

		try {

			mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(requestString)
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetUsersSuccess() {

		String responseString = getJsonString("classpath:response/get-users-success-response.json");

		try {
			List<User> users = mapFromJsonList(responseString, User[].class);
			when(service.getUsers(UserOption.ALL)).thenReturn(users);

			MvcResult result = mockMvc.perform(get("/user").accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk()).andReturn();
			String responseContent = result.getResponse().getContentAsString();
			List<User> userList = mapFromJsonList(responseContent, User[].class);

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
			List<User> users = new ArrayList<User>();
			when(service.getUsers(UserOption.ALL)).thenReturn(users);

			MvcResult result = mockMvc.perform(get("/user").accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk()).andReturn();
			String responseContent = result.getResponse().getContentAsString();
			List<User> userList = mapFromJsonList(responseContent, User[].class);

			assertNotNull(userList);
			assertTrue(CollectionUtils.isEmpty(userList));
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testDeleteUserSuccess() {

		try {
			doNothing().when(service).deleteUser(1);

			mockMvc.perform(delete("/user/1")).andExpect(status().isOk()).andReturn();
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testDeleteUserFailureWhenNoDataAvailable() {

		try {
			doThrow(new SbaException(SbaResponseCode.DATA_NOT_AVAILABLE_FOR_DELETE)).when(service).deleteUser(1);

			mockMvc.perform(delete("/user/1")).andExpect(status().isNotFound()).andReturn();
		} catch (Exception e) {
			fail();
		}
	}

}
