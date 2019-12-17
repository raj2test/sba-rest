/**
 * 
 */
package com.fsd.sba.entity;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import com.fsd.sba.AbstractTest;

/**
 * @author Rajiniganth Jagadeesan
 *
 */
public class TaskEtTest extends AbstractTest {
	
	
	@Test
	public void testHashCode() {
		
		TaskEt t1 = new TaskEt();
		t1.setTaskId(1);
		t1.setTask("Test");
		
		TaskEt t2 = new TaskEt();
		t2.setTaskId(1);
		t2.setTask("Test");

		assertEquals(t1.hashCode(), t2.hashCode());
	}
	
	@Test
	public void testHashCodeWithParent() {
		
		TaskEt t1 = new TaskEt();
		t1.setTaskId(1);
		t1.setTask("Test");
		t1.setPriority(Short.valueOf("10"));
		t1.setStartDate(LocalDate.now());
		t1.setEndDate(LocalDate.now());
		
		ParentTaskEt pt = new ParentTaskEt();
		pt.setParentId(1);
		pt.setParentTask("Test Parent");
		t1.setParentTask(pt);
		
		TaskEt t2 = new TaskEt();
		t2.setTaskId(1);
		t2.setTask("Test");
		t2.setPriority(Short.valueOf("10"));
		t2.setStartDate(LocalDate.now());
		t2.setEndDate(LocalDate.now());
		
		ParentTaskEt pt1 = new ParentTaskEt();
		pt1.setParentId(1);
		pt1.setParentTask("Test Parent");
		t2.setParentTask(pt1);

		assertEquals(t1.hashCode(), t2.hashCode());
	}
	

}
