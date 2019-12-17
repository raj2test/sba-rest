/**
 * 
 */
package com.fsd.sba.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;

import org.junit.Test;

import com.fsd.sba.AbstractTest;

/**
 * @author Rajiniganth Jagadeesan
 *
 */
public class ProjectTest extends AbstractTest{
	
	@Test
	public void testAddTask() {
		
		Task t1 = new Task();
		t1.setTaskId(1);
		t1.setTask("Test");
		t1.setPriority(Short.valueOf("10"));
		t1.setStartDate(LocalDate.now());
		t1.setEndDate(LocalDate.now());
		
		Project prj = new Project();
		prj.setProjectId(1);
		prj.setProject("Test Project");
		prj.addTasks(t1);
		
		assertNotNull(prj.getTasks());
		assertNotNull(prj.getTasks().get(0));
		assertEquals(t1, prj.getTasks().get(0));
	}
	
	@Test
	public void testHashCode() {
		
		Project prj = new Project();
		prj.setProjectId(1);
		prj.setProject("Test Project");
		prj.setPriority(Short.valueOf("10"));
		prj.setStartDate(LocalDate.now());
		prj.setEndDate(LocalDate.now());
		
		Project prj1 = new Project();
		prj1.setProjectId(1);
		prj1.setProject("Test Project");
		prj1.setPriority(Short.valueOf("10"));
		prj1.setStartDate(LocalDate.now());
		prj1.setEndDate(LocalDate.now());
		
		assertEquals(prj.hashCode(), prj1.hashCode());
	}
}
