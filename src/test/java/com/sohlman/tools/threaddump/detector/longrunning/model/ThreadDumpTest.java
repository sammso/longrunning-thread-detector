package com.sohlman.tools.threaddump.detector.longrunning.model;

import org.junit.Assert;
import org.junit.Test;

import com.sohlman.tools.threaddump.detector.testutils.FileUtil;

public class ThreadDumpTest {

	@Test
	public void testAllDumpsExist() throws Exception {
		
		String resource = "/com/sohlman/tools/threaddump/detector/longrunning/model/threadDump1.log";
		
		ThreadDump threadDump1 = new ThreadDump(FileUtil.readFileUTF8(
				getClass().getResourceAsStream(resource)), resource.substring(resource.lastIndexOf('/') + 1), resource);

		Thread[] threads = threadDump1.getThreads();
		
		Thread first = threads[0];
		Thread last = threads[threads.length - 1];
		
		Assert.assertEquals(first.getName(), "Attach Listener");
		Assert.assertEquals(last.getName(), "VM Periodic Task Thread");
	}
}
