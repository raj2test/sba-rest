package com.fsd.sba.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsd.sba.entity.UserEt;

@Repository
public interface UserRepository extends JpaRepository<UserEt, Integer> {
	
	List<UserEt> findByProjectIsNull();
	
	List<UserEt> findByTaskIsNull();

}
