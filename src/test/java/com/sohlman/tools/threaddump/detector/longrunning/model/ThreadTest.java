package com.sohlman.tools.threaddump.detector.longrunning.model;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.sohlman.tools.threaddump.detector.testutils.FileUtil;

public class ThreadTest {

	@Test
	public void testSameFile() throws Exception {
		String fileNameAndPath = "/com/sohlman/tools/threaddump/detector/longrunning/model/thread1.log";
		Thread thread1 = new Thread(fileNameAndPath, new Date(), FileUtil.readFileUTF8(
				getClass().getResourceAsStream(fileNameAndPath)));
		Thread thread2 = new Thread(fileNameAndPath, new Date(), FileUtil.readFileUTF8(
				getClass().getResourceAsStream(fileNameAndPath)));

		Assert.assertEquals(thread1.compareReport(thread2).promille,1000);
	}
	
	@Test
	public void testThreaDifference1() throws Exception {
		String fileNameAndPath1 = "/com/sohlman/tools/threaddump/detector/longrunning/model/thread1.log";
		String fileNameAndPath2 = "/com/sohlman/tools/threaddump/detector/longrunning/model/thread2.log";
		Thread thread1 = new Thread(fileNameAndPath1, new Date(),FileUtil.readFileUTF8(
				getClass().getResourceAsStream(fileNameAndPath1)));
		Thread thread2 = new Thread(fileNameAndPath2, new Date(),FileUtil.readFileUTF8(
				getClass().getResourceAsStream(fileNameAndPath2)));

		int promille = thread1.compareReport(thread2).promille;
		
		System.err.println("testThreaDifference1 " + promille);
		
		Assert.assertTrue(promille < 1000);
	}	
	
	@Test
	public void testThreadName() throws Exception {
		String fileNameAndPath = "/com/sohlman/tools/threaddump/detector/longrunning/model/thread1.log";
		Thread thread1 = new Thread(fileNameAndPath, new Date(),FileUtil.readFileUTF8(
				getClass().getResourceAsStream("/com/sohlman/tools/threaddump/detector/longrunning/model/thread1.log")));

		Assert.assertEquals(thread1.getName(), "localhost-startStop-1");
	}	
	
}
