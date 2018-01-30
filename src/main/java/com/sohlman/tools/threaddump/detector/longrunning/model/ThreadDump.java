package com.sohlman.tools.threaddump.detector.longrunning.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sohlman.tools.threaddump.util.FileUtil;
import com.sohlman.tools.threaddump.util.Util;

public class ThreadDump {
	public ThreadDump(File file) throws FileNotFoundException, IOException {
		this(FileUtil.readFile(file), file.getName(),file.getAbsolutePath());
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public ThreadDump(String[] lines, String name, String fullName) {
		this.name = name;
		this.fullName = fullName;
		int threadStart = -1;
		int threadEnd = -1;
		List<Thread> threads = new ArrayList();
		boolean isEnd=false;
		
		for (int i=0; i<lines.length ; i++ ) {
			String line = lines[i];
			
			if (line.indexOf("JNI global references")==0) {
				isEnd=true;
			}
			else if ( line.indexOf("Full thread dump Java HotSpot(TM)")==0) {
				if (i>0) {
					this.date = Util.stringToDate(lines[i-i]);
				}
			}
			
			if (line.startsWith("\"") || isEnd) {
				int oldThreadStart = threadStart;
				if (threadStart!=-1) {
					threadEnd = i - 1;
				}
				if (!isEnd) {
					threadStart = i;
				}
				if (oldThreadStart!=-1 && threadEnd!=-1) {
					int size = threadEnd - oldThreadStart + 1;
					String[] threadLines = new String[size];
					System.arraycopy(lines, oldThreadStart, threadLines, 0, size);
					Thread thread = new Thread(name, this.date, threadLines);
					threads.add(thread);
					threadMap.put(thread.getName(), thread);
				}
			}
		}
		
		this.threads = threads.toArray(new Thread[threads.size()]);
		
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getFullName() {
		return this.fullName;
	}
	
	public Thread getByName(String name) {
		return this.threadMap.get(name);
	}
	
	public Thread[] getThreads() {
		return threads;
	}
	
	public Map<String, ThreadHistory> matchReport(ThreadDump earlierThread, String nameFilter, String filter, int requiredSimilarity, int minLineCount, PrintStream out) {
		
		Map<String, ThreadHistory> map = new HashMap<>();
		for (Thread thread : threads )  {
			if (thread.contains(filter)) {
				
				if (thread.getName().contains(nameFilter)) {
					Thread otherThread = earlierThread.getByName(thread.getName());
					if (otherThread!=null) {
						CompareReport compareReport = thread.compareReport(otherThread);
						if (compareReport.promille>=requiredSimilarity && compareReport.minLineCount >= minLineCount) {
							map.put(thread.getName(), new ThreadHistory(otherThread, thread));
							//out.println(String.format("%s - %s : \"%s\" similarity: %d length: %d line: %s", getFullName(), earlierThread.getName(), thread.getName(), compareReport.promille, thread.getStackTraceLines().length, compareReport.line));
						}
					}
				}
			}
		}
		return map;
	}
	
	private Thread[] threads;
	private Map<String, Thread> threadMap = new HashMap<>();
	private String name = null;
	private String fullName = null;
	private Date date = null;
}
