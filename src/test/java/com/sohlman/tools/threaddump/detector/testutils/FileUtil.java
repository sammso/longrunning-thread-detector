package com.sohlman.tools.threaddump.detector.testutils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtil extends com.sohlman.tools.threaddump.util.FileUtil{	

	public static String[] readFileUTF8(InputStream inputStream) throws IOException {
		return readFile(inputStream, "UTF-8");
	}

	public static String[] readFile(InputStream inputStream, String charSet) throws IOException {
		return readFile(new InputStreamReader(inputStream, charSet));
	}
}
