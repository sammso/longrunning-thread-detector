package com.sohlman.tools.threaddump.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Util {
	public static Date stringToDate(String str)  {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return formatter.parse(str);
		} catch (ParseException e) {
			return null;
		}	
	}
	
	public static int stringToInt( String str) {
		str = removePrependingZero(str);	
		int value = Integer.valueOf(str);
		
		if (String.valueOf(value).equals(str)) {
			return value;
		}
		else {
			throw new IllegalArgumentException(str + " is cannot be converted to integer");
		}
	}
	
	private static String removePrependingZero(String str) {
		if (str.charAt(0)=='0') {
			return removePrependingZero(str.substring(1));
		}
		else {
			return str;
		}
	}

}
