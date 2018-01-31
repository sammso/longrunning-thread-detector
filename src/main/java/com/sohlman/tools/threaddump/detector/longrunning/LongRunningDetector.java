package com.sohlman.tools.threaddump.detector.longrunning;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sohlman.tools.threaddump.detector.longrunning.model.CompareReport;
import com.sohlman.tools.threaddump.detector.longrunning.model.Thread;
import com.sohlman.tools.threaddump.detector.longrunning.model.ThreadDump;
import com.sohlman.tools.threaddump.detector.longrunning.model.ThreadHistory;
import com.sohlman.tools.threaddump.util.Util;

public class LongRunningDetector {

	public void detect(File dir, String threadNameFilter, String classPathFilder, int requiredSimilarity, int minLineCount, OrderBy orderBy, PrintStream out) throws FileNotFoundException, IOException {
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

		Map<String, List<ThreadHistory>> historyThreadMap = new HashMap<>();
		
		for (File file : files) {
			if (file.isFile()) {
				earlierThreadDump = threadDump;
				
				threadDump = new ThreadDump(file);
				
				if (earlierThreadDump!=null) {
					Map<String, ThreadHistory> longRunningThreadPairMap = threadDump.matchReport(earlierThreadDump, threadNameFilter, classPathFilder, requiredSimilarity, minLineCount, System.out);
					
					for ( Entry<String, ThreadHistory> entry : longRunningThreadPairMap.entrySet() ) {
						
						ThreadHistory threadHistory = entry.getValue();
						String threadTid = threadHistory.getFirst().getTid();
						List<ThreadHistory> threadHistoryList = historyThreadMap.get(threadTid);
						
						if ( threadHistoryList==null ) {
							 
							threadHistoryList = new LinkedList<>();
							threadHistoryList.add(entry.getValue());							
							historyThreadMap.put(threadTid, threadHistoryList);
						}
						else {
							ThreadHistory threadHistoryExisting = threadHistoryList.get(threadHistoryList.size() - 1);
							
							if (threadHistoryExisting.getLast() == threadHistory.getFirst()) {
								threadHistoryExisting.add(threadHistory.getLast());
							}
							else {
								threadHistoryList.add(threadHistory);
							}
						}
					}
				}
			}
		}
		System.out.println("Time\tSeconds\tThreadName\tTid\tSimilarity\tLinecountt\tsiginificant line\tfiles");
		
		String plainFormat = "%s\t%d\t\"%s\"\t%s\t%d\t%d\t%s\t";
		String explainFormat = "%s\t%d s\t\"%s\"\t%s\ttotal-similarity: %d\tlines: %d\tline: %s\t";
		
		for (Entry<String, List<ThreadHistory>> entry : historyThreadMap.entrySet()) {
			String threadName = entry.getKey();
			List<ThreadHistory> threadHistoryList = entry.getValue();
			
			for (ThreadHistory threadHistory : threadHistoryList) {
				Thread first = threadHistory.getFirst();
				Thread last = threadHistory.getLast();
				
				CompareReport compareReport = first.compareReport(last);
				
				System.out.print(String.format(plainFormat, Util.dateToString(first.getThreadDumpDate()), ((last.getThreadDumpDateMillis() - first.getThreadDumpDateMillis())/1000), last.getName(), last.getTid(), compareReport.promille,last.getStackTraceLines().length, compareReport.lastSignificantLine));
				Iterator<Thread> iterator = threadHistory.iterator();
				
				String space = "";
				while(iterator.hasNext()) {
					System.out.print(space + iterator.next().getFileName());
					if (space.equals("")) space=" ";
				}
				System.out.println();
			}
					
		}
	}
}
