package com.fsd.sba.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fsd.sba.constant.UserOption;
import com.fsd.sba.entity.UserEt;
import com.fsd.sba.exception.SbaException;
import com.fsd.sba.exception.SbaResponseCode;
import com.fsd.sba.model.User;
import com.fsd.sba.repository.UserRepository;
import com.fsd.sba.transformer.Transformer;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private Transformer transformer;

	/**
	 * @param user
	 * @return User
	 */
	public User saveUser(User user) {
		
		UserEt userEt = repository.save(transformer.buildUserEntity(user));
		
		return transformer.buildUserModel(userEt);
	}

	public List<User> getUsers(UserOption option) {
		
		List<UserEt> userList = null;
		switch(option) {
		case PROJECT:
			userList = repository.findByProjectIsNull();
			break;
		case TASK:
			userList = repository.findByTaskIsNull();
			break;
		case ALL:
			default:
				userList = repository.findAll();
		}
		
		List<User> users = new ArrayList<User>();
		for (UserEt userEt: userList) {
			User user = transformer.buildUserModel(userEt);
			users.add(user);
		}
		
		return users;
	}

	public void deleteUser(Integer userId) {
		
		if (repository.existsById(userId)) {
			repository.deleteById(userId);
		} else {
			throw new SbaException(SbaResponseCode.DATA_NOT_AVAILABLE_FOR_DELETE);
		}
	}

}
