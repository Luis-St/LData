package net.luis.data.internal.io;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Helper class for reading files
 *
 * @author Luis-St
 */

public class FileHelper {
	
	/**
	 * Reads all lines from a file and returns them as a list of strings
	 * @param file The file to read from
	 * @return A list of strings containing all lines from the file
	 */
	public static List<String> readLines(File file) {
		try {
			return FileUtils.readLines(file, Charset.defaultCharset());
		} catch (Exception e) {
			throw new RuntimeException("Could not read lines from file '" + file.getAbsolutePath() + "'", e);
		}
	}
	
	/**
	 * Reads all lines from a file and returns them as a single string
	 * @param file The file to read from
	 * @return A string containing all lines from the file
	 */
	public static String read(File file) {
		try {
			return FileUtils.readFileToString(file, Charset.defaultCharset());
		} catch (Exception e) {
			throw new RuntimeException("Could not read file '" + file.getAbsolutePath() + "'", e);
		}
	}
}
