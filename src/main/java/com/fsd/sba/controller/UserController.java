package com.fsd.sba.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fsd.sba.constant.UserOption;
import com.fsd.sba.model.User;
import com.fsd.sba.service.UserService;

/**
 * @author Rajiniganth Jagadeesan
 * 
 *         Rest controller class servicing rest api's for user resource
 *
 */
@RestController
public class UserController {

	private final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService service;

	/*
	 * Rest Api for servicing post request to add/update the user resource
	 * 
	 * @param User
	 * 
	 * @return User
	 */
	@PostMapping(value = "/user", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public User saveUser(@Valid @RequestBody User user) {

		return service.saveUser(user);
	}

	/*
	 * Rest Api for servicing get request to retrieve all user resource
	 * 
	 * @return List<User>
	 */
	@GetMapping("/user")
	public List<User> getUsers(@RequestParam(value = "option", required = false, defaultValue = "ALL") String option) {
		
		return service.getUsers(UserOption.getOption(option));
	}

	/*
	 * Rest Api for servicing delete request to delete the user resource
	 * 
	 * @param Integer
	 */
	@DeleteMapping("/user/{userId}")
	public void deleteUser(@PathVariable Integer userId) {

		service.deleteUser(userId);

		log.info("User resource with user id({}) is deleted from the system", userId);
	}

}
