package net.luis.data.common.util;

import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class DataUtils {
	
	public static boolean isNotEscaped(String value, int index) {
		return index - 1 < 0 || value.charAt(index - 1) != '\\';
	}
	
	public static @NotNull String deleteWhitespace(String value, char ignoreBetween) {
		return deleteWhitespace(value, ignoreBetween, ignoreBetween);
	}
	
	public static @NotNull String deleteWhitespace(String value, char startIgnore, char endIgnore) {
		Objects.requireNonNull(value, "Value must not be null");
		StringBuilder result = new StringBuilder();
		boolean ignored = false;
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			if (isNotEscaped(value, i)) {
				if (c == startIgnore) {
					ignored = true;
				}
				if (c == endIgnore) {
					ignored = false;
				}
			}
			if (!Character.isWhitespace(c) || ignored) {
				result.append(c);
			}
			
		}
		return result.toString();
	}
	
	public static int countNoneQuoted(String value, String toCheck) {
		Objects.requireNonNull(value, "Value must not be null");
		Objects.requireNonNull(toCheck, "String to check must not be null");
		int count = 0;
		boolean inQuotes = false;
		for (int i = 0; i < value.length(); i++) {
			if (value.charAt(i) == '"' && isNotEscaped(value, i)) {
				inQuotes = !inQuotes;
			}
			if (!inQuotes && value.startsWith(toCheck, i)) {
				count++;
			}
		}
		return count;
	}
	
	public static String[] splitNoneQuoted(String value, String separator) {
		Objects.requireNonNull(value, "Value must not be null");
		Objects.requireNonNull(separator, "Separator must not be null");
		List<String> result = Lists.newArrayList();
		boolean inQuotes = false;
		for (int i = 0; i < value.length(); i++) {
			if (value.charAt(i) == '"' && isNotEscaped(value, i)) {
				inQuotes = !inQuotes;
			}
			if (!inQuotes && value.startsWith(separator, i)) {
				result.add(value.substring(0, i));
				value = value.substring(i + separator.length());
				i = 0;
			}
		}
		result.add(value);
		return result.toArray(String[]::new);
	}
}
