package net.luis.data.common.util;

import org.jetbrains.annotations.NotNull;

public class Utils {
	
	public static boolean isNotEscaped(String value, int index) {
		return index - 1 < 0 || value.charAt(index - 1) != '\\';
	}
	
	public static @NotNull String deleteWhitespace(@NotNull String value) {
		StringBuilder result = new StringBuilder();
		boolean inQuotes = false;
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			if (c == '"' && isNotEscaped(value, i)) {
				inQuotes = !inQuotes;
			}
			if (!Character.isWhitespace(c) || inQuotes) {
				result.append(c);
			}
		}
		return result.toString();
	}
	
}
