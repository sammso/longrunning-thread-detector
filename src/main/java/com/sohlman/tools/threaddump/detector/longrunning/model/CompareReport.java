package com.sohlman.tools.threaddump.detector.longrunning.model;

public class CompareReport {
	public CompareReport(String line, String lastSignificantLine, int promille, int minLineCount, long secondsBetweenDumps) {
		this.line = line;
		this.promille = promille;
		this.lastSignificantLine = lastSignificantLine;
		this.minLineCount = minLineCount;
		this.secondsBetweenDumps = secondsBetweenDumps;
	}
	
	public final String line;
	public final int promille;
	public final String lastSignificantLine;
	public final int minLineCount;
	public final long secondsBetweenDumps;
}
