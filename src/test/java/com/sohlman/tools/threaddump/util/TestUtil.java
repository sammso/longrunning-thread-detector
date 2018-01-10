package com.sohlman.tools.threaddump.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;


public class TestUtil {
	
	@Test
	public void testStringToInt () {
		Assert.assertEquals(1,Util.stringToInt("1"));
		Assert.assertEquals(1,Util.stringToInt("01"));
		Assert.assertEquals(1,Util.stringToInt("001"));
		Assert.assertEquals(2,Util.stringToInt("02"));
		
		try {
			Util.stringToInt("2x");
			Assert.fail();
		}
		catch(IllegalArgumentException e) {
			Assert.assertTrue(true);
		}
	}
	
	@Test
	public void testStringToDate () throws ParseException {
		
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date date = formatter.parse("2017-12-04 16:27:21");		
		
		Assert.assertEquals(date,Util.stringToDate("2017-12-04 16:27:21"));
		
		
		Assert.assertEquals(null,Util.stringToDate("2017-12-04x16:27:21"));

	}
}
