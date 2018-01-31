package com.sohlman.tools.threaddump.detector.longrunning.model;

import java.util.Date;

public class Thread {
	public Thread(String fileName, Date date, String[] lines) { 
		if ( !lines[0].startsWith("\"")) {
			throw new IllegalArgumentException("First line starts with \" value");
		}
		if ( lines[0].indexOf("\"",1)==-1 ) {
			throw new IllegalArgumentException("No termination \" found .. No noname : '" + lines[0] + "'");
		}
		this.fileName = fileName;
		this.lines = lines;
		this.date = date;
	}
	
	public String getName() {
		return getName(lines[0]);
	}
	
	public String getTid() {
		int startOfTid = lines[0].indexOf(_TID_STRING) + _TID_STRING.length();
		int endOfTid = lines[0].indexOf(' ', startOfTid);
		return lines[0].substring(startOfTid, endOfTid);
	}
	
	public Date getThreadDumpDate() {
		return this.date;
	}
	
	/**
	 * Returns millisSecond value of the threaddump time or Long.MIN_VALUE if failed to parsed.
	 * 
	 * @return
	 */
	public long getThreadDumpDateMillis() {
		if (this.date==null) {
			return Long.MIN_VALUE;
		}
		else {
			return this.date.getTime();
		}
	}
	
	public String getFileName() {
		return this.fileName;
	}
	
	public boolean isSameThread(Thread thread) {
		return getName().equals(thread.getName());
	}
	
	public boolean contains(String str) {
		for( String line : getStackTraceLines()) {
			if ( line.indexOf(str) >= 0 ) {
				return true;
			}
		}
		return false;
	}
	
	public CompareReport compareReport(Thread thread) {
		String[] thisLines = getStackTraceLines();
		String[] threadLines = thread.getStackTraceLines();
		
		int maxLineCount = Math.max(thisLines.length, threadLines.length);
		int minLineCount = Math.min(thisLines.length, threadLines.length);
		
		long secondsBetweenDumps = ( this.getThreadDumpDateMillis() - thread.getThreadDumpDateMillis() ) / 1000;
		
		int equalNumber=0;
		
		String lastEqualLine = null;
		String lastSignificantLine = null;
		
		for (int thisCounter = thisLines.length - 1, threadCounter = threadLines.length - 1 ;  
				thisCounter >=0 && threadCounter >=0 ; 
				thisCounter-- , threadCounter--) {
			
			String thisLine = thisLines[thisCounter];
			String threadLine = threadLines[threadCounter];
			
			if (thisLine.equals(threadLine)) {
				lastEqualLine = thisLine;
				
				if (!(lastEqualLine.indexOf("at java.")>=0 || lastEqualLine.indexOf("at sun.")>=0)) {
					lastSignificantLine = lastEqualLine;
				}
				
				equalNumber++;
			}
			else {
				break;
			}
		}
		
		// Fetch last 
		
		
		
		return new CompareReport(lastEqualLine, lastSignificantLine,  (equalNumber * 1000) / maxLineCount, minLineCount, secondsBetweenDumps);
	}
	
	public String[] getStackTraceLines() {
		if (this.stackTraceRows==null) {		
			synchronized (this.lines) {
				if (this.stackTraceRows==null) {	
					String[] stackTraceRows = new String[this.lines.length];
					
					int count=0;
					for (String line : this.lines) {
						line = line.trim();
						if (line.startsWith("at")) {
							stackTraceRows[count++]=line;
						}
					}
					
					this.stackTraceRows = new String[count];
					
					System.arraycopy(stackTraceRows, 0, this.stackTraceRows, 0, count);
				}
			}
		}
		return this.stackTraceRows; 
	}
	
	protected String getName(String name) {
		return name.substring(name.indexOf('"') + 1, name.indexOf("\"",1));
	}	
	
	private Date date;
	private String fileName;
	private String[] lines;
	private String[] stackTraceRows;
	
	private static final String _TID_STRING = "tid=";
}
