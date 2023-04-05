package net.luis.data;

import com.google.common.collect.Lists;
import net.luis.data.common.io.FileHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.List;

/**
 *
 * @author Luis-St
 *
 */

public class Main {
	
	private static final Logger LOGGER = LogManager.getLogger(Main.class);
	
	public static void main(String[] args) throws Exception {
		String jsonFile = FileHelper.read(new File("src/main/resources/test.json"));
		StringBuilder jsonBuilder = new StringBuilder();
		boolean inQuotes = false;
		for (int i = 0; i < jsonFile.length(); i++) {
			char c = jsonFile.charAt(i);
			if (c == '"' && isNotEscaped(jsonFile, i)) {
				inQuotes = !inQuotes;
			}
			if (Character.isWhitespace(c) && !inQuotes) {
				continue;
			} else {
				jsonBuilder.append(c);
			}
		}
		
		String json = jsonBuilder.toString();
		String result = json.substring(1, json.length() - 1);
		
		List<String> jsonParts = Lists.newArrayList();
		
		StringBuilder partBuilder = new StringBuilder();
		
		char target = ',';
		
		for (int i = 0; i < result.length(); i++) {
			char c = result.charAt(i);
			if (target == ',') {
				if (c == '{' && isNotEscaped(result, i)) {
					target = '}';
				} else if (c == '[' && isNotEscaped(result, i)) {
					target = ']';
				}
			}
			if (c == target && isNotEscaped(result, i)) {
				if (c == '}' || c == ']') {
					partBuilder.append(c);
				}
				if (partBuilder.length() > 0) {
					jsonParts.add("'" + partBuilder.toString() + "'");
				}
				partBuilder = new StringBuilder();
				target = ',';
			} else {
				partBuilder.append(c);
			}
		}
		System.out.println(jsonParts);
	}
	
	private static boolean isNotEscaped(String file, int index) {
		return index - 1 < 0 || file.charAt(index - 1) != '\\';
	}
	
}
