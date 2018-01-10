package com.sohlman.tools.threaddump.detector.longrunning.model;

import org.junit.Assert;
import org.junit.Test;

import com.sohlman.tools.threaddump.detector.testutils.FileUtil;

public class ThreadTest {

	@Test
	public void testSameFile() throws Exception {
		Thread thread1 = new Thread(FileUtil.readFileUTF8(
				getClass().getResourceAsStream("/com/sohlman/tools/threaddump/detector/longrunning/model/thread1.log")));
		Thread thread2 = new Thread(FileUtil.readFileUTF8(
				getClass().getResourceAsStream("/com/sohlman/tools/threaddump/detector/longrunning/model/thread1.log")));

		Assert.assertEquals(thread1.countComparePromille(thread2).promille,1000);
	}
	
	@Test
	public void testThreaDifference1() throws Exception {
		Thread thread1 = new Thread(FileUtil.readFileUTF8(
				getClass().getResourceAsStream("/com/sohlman/tools/threaddump/detector/longrunning/model/thread1.log")));
		Thread thread2 = new Thread(FileUtil.readFileUTF8(
				getClass().getResourceAsStream("/com/sohlman/tools/threaddump/detector/longrunning/model/thread2.log")));

		int promille = thread1.countComparePromille(thread2).promille;
		
		System.err.println("testThreaDifference1 " + promille);
		
		Assert.assertTrue(promille < 1000);
	}	
	
	@Test
	public void testThreadName() throws Exception {
		Thread thread1 = new Thread(FileUtil.readFileUTF8(
				getClass().getResourceAsStream("/com/sohlman/tools/threaddump/detector/longrunning/model/thread1.log")));

		Assert.assertEquals(thread1.getName(), "localhost-startStop-1");
	}	
	
}
