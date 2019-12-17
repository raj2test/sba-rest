/**
 * 
 */
package com.fsd.sba.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.fsd.sba.AbstractTest;

/**
 * @author Rajiniganth Jagadeesan
 *
 */
public class TestSbaResponseCode extends AbstractTest{
	
	@Test
	public void testGetResponseCode() {
		
		SbaResponseCode value  = SbaResponseCode.DATA_NOT_AVAILABLE_FOR_DELETE;
		
		SbaResponseCode rtn = SbaResponseCode.getResponseCode(value.getCode());
		
		assertNotNull(rtn);
		assertEquals(value, rtn);
		
	}
	
	

}
