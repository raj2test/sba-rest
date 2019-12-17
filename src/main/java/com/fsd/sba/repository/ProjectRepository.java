package com.fsd.sba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsd.sba.entity.ProjectEt;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEt, Integer> {

}
