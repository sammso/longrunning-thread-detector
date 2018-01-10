package com.sohlman.tools.threaddump.detector.longrunning.model;

public class CompareReport {
	public CompareReport(String line, int promille) {
		this.line = line;
		this.promille = promille;
	}
	
	public final String line;
	public final int promille;
}
