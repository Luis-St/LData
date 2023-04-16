package net.luis.data.common.util;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Utils {
	
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
	
}
