package net.luis.data.common.util;

import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * Utility class for data
 *
 * @author Luis-St
 */

public class DataUtils {
	
	/**
	 * Checks if the given character is not escaped
	 * @param value The value to check
	 * @param index The index of the character to check
	 * @return True if the character is not escaped
	 */
	public static boolean isNotEscaped(String value, int index) {
		return index - 1 < 0 || value.charAt(index - 1) != '\\';
	}
	
	/**
	 * Deletes all whitespaces from the given string, except those between the given character
	 * @param value The value to delete the whitespaces from
	 * @param ignoreBetween The character to ignore whitespaces
	 * @return The value without whitespaces
	 */
	public static @NotNull String deleteWhitespace(String value, char ignoreBetween) {
		return deleteWhitespace(value, ignoreBetween, ignoreBetween);
	}
	
	/**
	 * Deletes all whitespaces from the given string, except those between the given characters
	 * @param value The value to delete the whitespaces from
	 * @param startIgnore The character to start ignoring whitespaces
	 * @param endIgnore The character to stop ignoring whitespaces
	 * @return The value without whitespaces
	 */
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
	
	/**
	 * Counts the occurrences of the given string, except those between double quotes
	 * @param value The value to count the occurrences in
	 * @param toCheck The string to check for
	 * @return The amount of occurrences
	 */
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
	
	/**
	 * Splits the given string at the given separator, except those between double quotes
	 * @param value The value to split
	 * @param separator The separator to split at
	 * @return The split string
	 */
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
