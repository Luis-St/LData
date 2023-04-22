package net.luis.data.common.io;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

/**
 *
 * @author Luis-St
 *
 */

public class FileHelper {
	
	public static List<String> readLines(File file) {
		try {
			return FileUtils.readLines(file, Charset.defaultCharset());
		} catch (Exception e) {
			throw new RuntimeException("Could not read lines from file '" + file.getAbsolutePath() + "'", e);
		}
	}
	
	public static String read(File file) {
		try {
			return FileUtils.readFileToString(file, Charset.defaultCharset());
		} catch (Exception e) {
			throw new RuntimeException("Could not read file '" + file.getAbsolutePath() + "'", e);
		}
	}
	
}
