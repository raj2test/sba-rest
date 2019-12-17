/**
 * 
 */
package com.fsd.sba.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fsd.sba.AbstractTest;

/**
 * @author Rajiniganth Jagadeesan
 *
 */
public class UserEtTest extends AbstractTest{
	
	@Test
	public void testEquals() {
		
		UserEt u1 = new UserEt();
		u1.setUserId(1);
		u1.setFirstName("test");
		u1.setLastName("test");
		u1.setEmployeeId("1");
		
		UserEt u2 = new UserEt();
		u2.setUserId(1);
		u2.setFirstName("test");
		u2.setLastName("test");
		u2.setEmployeeId("1");
		
		assertEquals(u1, u2);
	}
	
	@Test
	public void testEqualsEmpty() {
		
		UserEt u1 = new UserEt();
		
		UserEt u2 = new UserEt();
		
		assertEquals(u1, u2);
	}
	
	@Test
	public void testHashCode() {
		
		UserEt u1 = new UserEt();
		u1.setUserId(1);
		u1.setFirstName("test");
		u1.setLastName("test");
		u1.setEmployeeId("1");
		
		UserEt u2 = new UserEt();
		u2.setUserId(1);
		u2.setFirstName("test");
		u2.setLastName("test");
		u2.setEmployeeId("1");
		
		assertEquals(u1.hashCode(), u2.hashCode());
	}

}
