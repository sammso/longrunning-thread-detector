package com.sohlman.tools.threaddump.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.sohlman.tools.threaddump.detector.longrunning.LongRunningDetector;
import com.sohlman.tools.threaddump.detector.longrunning.OrderBy;

public class Main {
	
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		
		if (args.length<3) {
			System.err.println("too little arguments <file> <thread name filter> <classpath filter> <required similary default 500> <minimun stackline count default 30> <optional order name/date default name>");
			return;
		}
		
		File dir = new File(args[0]);
		String nameFilter = args[1];
		String classPathFilter = args[2];
		
		String requiredSimilarityString =  "500";
		
		if (args.length >= 4 && args[3].matches("[-+]?\\d*\\.?\\d+") ) {
			requiredSimilarityString = args[3];
		}
		
		int requiredSimilarity = Integer.valueOf(requiredSimilarityString);
		
		String minLineCountString =  "30";
		
		if (args.length >= 5 && args[4].matches("[-+]?\\d*\\.?\\d+") ) {
			minLineCountString = args[4];
		}
		
		int minLineCount = Integer.valueOf(minLineCountString);
		
		OrderBy orderBy = OrderBy.NAME;
		
		if (args.length >= 6 && args[5].equals("date") ) {
			orderBy = OrderBy.DATE;
		}
		
		if (dir.exists() && dir.isDirectory()) {
			new LongRunningDetector().detect(dir, nameFilter, classPathFilter, requiredSimilarity, minLineCount, orderBy, System.out);
		}
		else {
			System.err.println("Does not exist or is not directory");
		}
	}
}
