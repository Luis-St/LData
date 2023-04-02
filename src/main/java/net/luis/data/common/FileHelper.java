package net.luis.data.common;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Stream;

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
			throw new RuntimeException(e);
		}
	}
	
	public static String read(File file) {
		try {
			return FileUtils.readFileToString(file, Charset.defaultCharset());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
