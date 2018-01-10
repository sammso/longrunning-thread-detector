package com.sohlman.tools.threaddump.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.sohlman.tools.threaddump.detector.longrunning.LongRunningDetector;
import com.sohlman.tools.threaddump.detector.longrunning.OrderBy;

public class Main {
	
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		
		if (args.length<3) {
			System.err.println("too little arguments <file> <thread name filter> <classpath filter> <optional order name/date default name>");
			return;
		}
		
		File dir = new File(args[0]);
		String nameFilter = args[1];
		String classPathFilter = args[2];
		
		OrderBy orderBy = OrderBy.NAME;
		
		if (args.length >= 3 && args[2].equals("date") ) {
			orderBy = OrderBy.DATE;
		}
		
		if (dir.exists() && dir.isDirectory()) {
			new LongRunningDetector().detect(dir, nameFilter, classPathFilter, orderBy, System.out);
		}
		else {
			System.err.println("Does not exist or is not directory");
		}
	}
}
