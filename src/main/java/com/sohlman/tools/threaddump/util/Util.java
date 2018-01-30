package com.sohlman.tools.threaddump.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	public static Date stringToDate(String str)  {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return formatter.parse(str);
		} catch (ParseException e) {
			return null;
		}	
	}
	
	public static String dateToString(Date date, String format) {
		return (new SimpleDateFormat(format)).format(date);
	}
	
	public static String dateToString(Date date) {
		return dateToString(date, "yyyy-MM-dd HH:mm:ss");
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
