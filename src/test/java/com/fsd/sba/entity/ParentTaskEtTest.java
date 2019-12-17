/**
 * 
 */
package com.fsd.sba.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;

import org.junit.Test;

import com.fsd.sba.AbstractTest;

/**
 * @author Rajiniganth Jagadeesan
 *
 */
public class ParentTaskEtTest extends AbstractTest{
	
	@Test
	public void testAddTask() {
		
		TaskEt t1 = new TaskEt();
		t1.setTaskId(1);
		t1.setTask("Test");
		t1.setPriority(Short.valueOf("10"));
		t1.setStartDate(LocalDate.now());
		t1.setEndDate(LocalDate.now());
		
		ParentTaskEt pt = new ParentTaskEt();
		pt.setParentId(1);
		pt.setParentTask("Test Parent");
		pt.addTasks(t1);
		
		assertNotNull(pt.getTasks());
		assertNotNull(pt.getTasks().get(0));
		assertEquals(t1, pt.getTasks().get(0));
	}

}
