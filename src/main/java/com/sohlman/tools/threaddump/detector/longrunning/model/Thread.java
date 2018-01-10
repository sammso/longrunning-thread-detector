package com.sohlman.tools.threaddump.detector.longrunning.model;

public class Thread {
	public Thread(String[] lines) { 
		if ( !lines[0].startsWith("\"")) {
			throw new IllegalArgumentException("First line starts with \" value");
		}
		if ( lines[0].indexOf("\"",1)==-1 ) {
			throw new IllegalArgumentException("No termination \" found .. No noname : '" + lines[0] + "'");
		}
		
		this.lines = lines;
	}
	
	public String getName() {
		return getName(lines[0]);
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
	
	public CompareReport countComparePromille(Thread thread) {
		String[] thisLines = getStackTraceLines();
		String[] threadLines = thread.getStackTraceLines();
		
		int maxRowCount = Math.max(thisLines.length, threadLines.length);
		int minRowCount = Math.min(thisLines.length, threadLines.length);
		
		int equalNumber=0;
		
		String lastEqualLine = null;
		
		for (int thisCounter = thisLines.length - 1, threadCounter = threadLines.length - 1 ;  
				thisCounter >=0 && threadCounter >=0 ; 
				thisCounter-- , threadCounter--) {
			
			String thisLine = thisLines[thisCounter];
			String threadLine = threadLines[threadCounter];
			
			if (thisLine.equals(threadLine)) {
				lastEqualLine = thisLine;
				equalNumber++;
			}
			else {
				break;
			}
		}
		
		return new CompareReport(lastEqualLine,  (equalNumber * 1000) / maxRowCount);
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

	private String[] lines;
	private String[] stackTraceRows;
}
