/**
 * 
 */
package com.fsd.sba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsd.sba.entity.TaskEt;

/**
 * @author Rajiniganth Jagadeesan
 *
 */
@Repository
public interface TaskRepository extends JpaRepository<TaskEt, Integer> {

}
