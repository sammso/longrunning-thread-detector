package com.sohlman.tools.threaddump.detector.longrunning.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ThreadHistory {
	
	private List<Thread> list = new LinkedList<>();
	
	public ThreadHistory(Thread first, Thread last) {
		list.add(first);
		list.add(last);
	}
	
	public void add(Thread thread) {
		list.add(thread);
	}
	
	public Thread getFirst() {
		return list.get(0);
	}
	
	public Thread getLast() {
		return list.get(list.size() - 1);
	}
	
	public Iterator<Thread> iterator() {
		return list.iterator();
	}
}
