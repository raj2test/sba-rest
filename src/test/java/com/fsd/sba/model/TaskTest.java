/**
 * 
 */
package com.fsd.sba.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import com.fsd.sba.AbstractTest;

/**
 * @author Rajiniganth Jagadeesan
 *
 */
public class TaskTest extends AbstractTest {
	
	
	@Test
	public void testHashCode() {
		
		Task t1 = new Task();
		t1.setTaskId(1);
		t1.setTask("Test");
		t1.setParentFlag(true);
		
		Task t2 = new Task();
		t2.setTaskId(1);
		t2.setTask("Test");
		t2.setParentFlag(true);

		assertEquals(t1.hashCode(), t2.hashCode());
	}
	
	@Test
	public void testHashCodeWithParent() {
		
		Task t1 = new Task();
		t1.setTaskId(1);
		t1.setTask("Test");
		t1.setPriority(Short.valueOf("10"));
		t1.setStartDate(LocalDate.now());
		t1.setEndDate(LocalDate.now());
		
		ParentTask pt = new ParentTask();
		pt.setParentId(1);
		pt.setParentTask("Test Parent");
		t1.setParentTask(pt);
		
		Task t2 = new Task();
		t2.setTaskId(1);
		t2.setTask("Test");
		t2.setPriority(Short.valueOf("10"));
		t2.setStartDate(LocalDate.now());
		t2.setEndDate(LocalDate.now());
		
		ParentTask pt1 = new ParentTask();
		pt1.setParentId(1);
		pt1.setParentTask("Test Parent");
		t2.setParentTask(pt1);

		assertEquals(t1.hashCode(), t2.hashCode());
	}
	

}
