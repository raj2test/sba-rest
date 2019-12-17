/**
 * 
 */
package com.fsd.sba.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fsd.sba.AbstractTest;

/**
 * @author Rajiniganth Jagadeesan
 *
 */
public class UserTest extends AbstractTest{
	
	@Test
	public void testEquals() {
		
		User u1 = new User();
		u1.setUserId(1);
		u1.setFirstName("test");
		u1.setLastName("test");
		u1.setEmployeeId("1");
		
		User u2 = new User();
		u2.setUserId(1);
		u2.setFirstName("test");
		u2.setLastName("test");
		u2.setEmployeeId("1");
		
		assertEquals(u1, u2);
	}
	
	@Test
	public void testEqualsEmpty() {
		
		User u1 = new User();
		
		User u2 = new User();
		
		assertEquals(u1, u2);
	}
	
	@Test
	public void testHashCode() {
		
		User u1 = new User();
		u1.setUserId(1);
		u1.setFirstName("test");
		u1.setLastName("test");
		u1.setEmployeeId("1");
		
		User u2 = new User();
		u2.setUserId(1);
		u2.setFirstName("test");
		u2.setLastName("test");
		u2.setEmployeeId("1");
		
		assertEquals(u1.hashCode(), u2.hashCode());
	}

}
