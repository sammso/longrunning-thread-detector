package com.sohlman.tools.threaddump.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

public class FileUtil {

	public static String[] readFile(File file) throws FileNotFoundException, IOException {
		return readFile(new FileReader(file));
	}

	public static String[] readFile(Reader reader) throws IOException {
		try (BufferedReader br = new BufferedReader(reader)) {
			List<String> lines = new LinkedList<>();
			for (String line; (line = br.readLine()) != null;) {
				lines.add(line);
			}
			return lines.toArray(new String[lines.size()]);
		}
	}
}
