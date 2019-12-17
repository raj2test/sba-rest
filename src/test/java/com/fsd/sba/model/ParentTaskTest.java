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
public class ParentTaskTest extends AbstractTest{
	
	@Test
	public void testAddTask() {
		
		Task t1 = new Task();
		t1.setTaskId(1);
		t1.setTask("Test");
		t1.setParentFlag(false);
		t1.setPriority(Short.valueOf("10"));
		t1.setStartDate(LocalDate.now());
		t1.setEndDate(LocalDate.now());
		
		ParentTask pt = new ParentTask();
		pt.setParentId(1);
		pt.setParentTask("Test Parent");
		pt.addTasks(t1);
		
		assertNotNull(pt.getTasks());
		assertNotNull(pt.getTasks().get(0));
		assertEquals(t1, pt.getTasks().get(0));
	}

}
