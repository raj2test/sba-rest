/**
 * 
 */
package com.fsd.sba.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Rajiniganth Jagadeesan
 *
 */
@RunWith(SpringRunner.class)
public class LocalDateConverterTest {
	
	
	@Test
	public void testConvertToDatabaseColumn() {
		
		String dateString = "2018-07-14";       
		LocalDate localDate = LocalDate.parse(dateString); 
		Date dtRtn = new LocalDateConverter().convertToDatabaseColumn(localDate);
		String dtStr = dtRtn.toString();
		assertNotNull(dtRtn);
		assertEquals(dateString, dtStr);
	}
	
	@Test
	public void testConvertToEntityAttribute() {
		
		String dateString = "2018-07-14";    
		Date dtReq = Date.valueOf(dateString);
		LocalDate localDate = new LocalDateConverter().convertToEntityAttribute(dtReq);
		String dtStr = localDate.toString();
		assertNotNull(localDate);
		assertEquals(dateString, dtStr);
	}
	
	@Test
	public void testConvertToDatabaseColumnForNull() {
		
		Date dtRtn = new LocalDateConverter().convertToDatabaseColumn(null);
		assertEquals(null, dtRtn);
	}
	
	@Test
	public void testConvertToEntityAttributeForNull() {
		
		LocalDate localDate = new LocalDateConverter().convertToEntityAttribute(null);
		assertEquals(null, localDate);
	}

}
