package net.luis.data;

import com.google.common.collect.Lists;
import net.luis.data.common.io.FileHelper;
import net.luis.data.json.exception.JsonSyntaxException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

/**
 *
 * @author Luis-St
 *
 */

public class Main {
	
	private static final Logger LOGGER = LogManager.getLogger(Main.class);
	
	public static void main(String[] args) {
		String jsonFile = FileHelper.read(new File("src/main/resources/test.json"));
		StringBuilder jsonBuilder = new StringBuilder();
		boolean inQuotes = false;
		for (int i = 0; i < jsonFile.length(); i++) {
			char c = jsonFile.charAt(i);
			if (c == '"' && isNotEscaped(jsonFile, i)) {
				inQuotes = !inQuotes;
			}
			if (!Character.isWhitespace(c) || inQuotes) {
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
					jsonParts.add(partBuilder.toString());
				}
				partBuilder = new StringBuilder();
				target = ',';
			} else {
				partBuilder.append(c);
			}
		}
		System.out.println(jsonParts + "\n");
		encodeJsonParts(jsonParts);
	}
	
	private static void encodeJsonParts(@NotNull List<String> parts) {
		for (String part : parts) {
			int index = validIndexOf(part, ':');
			if (index > 0) {
				encodeJsonPart(part.substring(0, index), part.substring(index + 1));
			}
		}
	}
	
	private static void encodeJsonPart(@NotNull String key, String value) {
		if (!key.startsWith("\"") || !key.endsWith("\"")) {
			throw new JsonSyntaxException("Invalid key: " + key);
		}
		if (value.startsWith("\"") && value.endsWith("\"")) {
			System.out.println("JsonString: " + value);
		} else if (value.startsWith("{") && value.endsWith("}")) {
			System.out.println("JsonObject: " + value);
		} else if (value.startsWith("[") && value.endsWith("]")) {
			System.out.println("JsonArray: " + value);
		} else if (value.equals("true") || value.equals("false")) {
			System.out.println("JsonBoolean: " + value);
		} else if (value.equals("null")) {
			System.out.println("JsonNull: " + value);
		} else {
			System.out.println("JsonNumber: " + value);
		}
	}
	
	//region Helper methods
	private static int validIndexOf(@NotNull String value, char key) {
	    int index = -1;
		boolean inQuotes = false;
		for (int i = 0; i < value.length(); i++) {
			if (value.charAt(i) == '"' && isNotEscaped(value, i)) {
				inQuotes = !inQuotes;
			}
			if (!inQuotes && value.charAt(i) == key && isNotEscaped(value, i)) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	private static boolean isNotEscaped(String file, int index) {
		return index - 1 < 0 || file.charAt(index - 1) != '\\';
	}
	//endregion
	
}
