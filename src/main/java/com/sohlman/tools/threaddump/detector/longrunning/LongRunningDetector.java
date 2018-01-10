package com.sohlman.tools.threaddump.detector.longrunning;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.sohlman.tools.threaddump.detector.longrunning.model.ThreadDump;
import com.sohlman.tools.threaddump.util.FileUtil;

public class LongRunningDetector {

	public void detect(File dir, String threadNameFilter, String classPathFilder, OrderBy orderBy, PrintStream out) throws FileNotFoundException, IOException {
		if (!dir.exists()) {
			throw new IllegalArgumentException(dir.getAbsolutePath() + " not exist");
		}

		if (!dir.isDirectory()) {
			throw new IllegalArgumentException(dir.getAbsolutePath() + " is not directory");
		}
		
		List<File> files = Arrays.asList(dir.listFiles());
		
		if (orderBy==OrderBy.DATE) {
			Collections.sort(files, (a, b) -> Math.toIntExact(a.lastModified()-b.lastModified()));
		}
		else {
			Collections.sort(files, (a, b) -> a.getName().compareTo(b.getName()));
		}
			
			
		ThreadDump earlierThreadDump = null;
		ThreadDump threadDump = null;
		
		for (File file : files) {
			if (file.isFile()) {
				earlierThreadDump = threadDump;
				
				threadDump = new ThreadDump(file);
				
				if (earlierThreadDump!=null) {
					threadDump.printMatchReport(earlierThreadDump, threadNameFilter, classPathFilder, System.out);
				}
			}
		}
	}
}
