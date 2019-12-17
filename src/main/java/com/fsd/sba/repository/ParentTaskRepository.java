/**
 * 
 */
package com.fsd.sba.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fsd.sba.entity.ParentTaskEt;

/**
 * @author Rajiniganth Jagadeesan
 *
 */
@Repository
public interface ParentTaskRepository extends JpaRepository<ParentTaskEt, Integer> {

	@Query(value = "select * from parent_task pt, task t where pt.parent_task = t.task and t.project_id = :projectId", nativeQuery = true)
	List<ParentTaskEt> findByProjectId(@Param("projectId") Integer projectId);
	
}
