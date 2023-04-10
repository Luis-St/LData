package net.luis.data.properties;

import net.luis.data.common.exception.FormatException;
import net.luis.data.common.util.StringConverter;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author Luis-St
 *
 */

public class Property implements Comparable<Property> {
	
	private final String key;
	private final String value;
	
	//region Constructors
	public Property(String key, int value) {
		this(key, String.valueOf(value));
	}
	
	public Property(String key, long value) {
		this(key, String.valueOf(value));
	}
	
	public Property(String key, double value) {
		this(key, String.valueOf(value));
	}
	
	public Property(String key, boolean value) {
		this(key, String.valueOf(value));
	}
	
	public Property(String key, char value) {
		this(key, String.valueOf(value));
	}
	
	public <T> Property(String key, T value, @NotNull StringConverter<T> converter) {
		this(key, converter.toString(value));
	}
	//endregion
	
	//region Array constructors
	public Property(String key, int... value) {
		this(key, Arrays.toString(value));
	}
	
	public Property(String key, long... value) {
		this(key, Arrays.toString(value));
	}
	
	public Property(String key, double... value) {
		this(key, Arrays.toString(value));
	}
	
	public Property(String key, boolean... value) {
		this(key, Arrays.toString(value));
	}
	
	public Property(String key, String... value) {
		this(key, Arrays.toString(value));
	}
	
	@SafeVarargs
	public <T> Property(String key, @NotNull StringConverter<T> converter, T... value) {
		this(key, Arrays.toString(Arrays.stream(value).map(converter::toString).toArray(String[]::new)));
	}
	//endregion
	
	public Property(String key, String value) {
		//region Validation
		if (key == null) {
			throw new IllegalArgumentException("Key of property must not be null");
		}
		if (key.isEmpty()) {
			throw new IllegalArgumentException("Key of property must not be empty");
		}
		if (key.isBlank()) {
			throw new IllegalArgumentException("Key of property must not be blank");
		}
		if (key.startsWith("#")) {
			throw new IllegalArgumentException("Key of property must not start with #");
		}
		//endregion
		this.key = key.strip();
		this.value = StringUtils.stripToEmpty(value);
	}
	
	public String getKey() {
		return this.key;
	}
	
	public String get() {
		return this.value;
	}
	
	//region Getter overloads
	public int getAsInt() {
		try {
			return Integer.parseInt(this.value);
		} catch (Exception e) {
			throw new FormatException("Value of property is not an int: " + this.value);
		}
	}
	
	public long getAsLong() {
		try {
			return Long.parseLong(this.value);
		} catch (Exception e) {
			throw new FormatException("Value of property is not a long: " + this.value);
		}
	}
	
	public double getAsDouble() {
		try {
			return Double.parseDouble(this.value);
		} catch (Exception e) {
			throw new FormatException("Value of property is not a double: " + this.value);
		}
	}
	
	public boolean getAsBoolean() {
		if (this.value.equalsIgnoreCase("true") || this.value.equalsIgnoreCase("false")) {
			return Boolean.parseBoolean(this.value);
		}
		throw new FormatException("Value of property is not a boolean: " + this.value);
	}
	
	public char getAsChar() {
		if (this.value.length() == 1) {
			return this.value.charAt(0);
		}
		throw new FormatException("Value of property is not a char: " + this.value);
	}
	
	public <T> T getAs(@NotNull StringConverter<T> converter) {
		return converter.fromString(this.value);
	}
	//endregion
	
	public String[] getAsArray() {
		return this.value.replace("[", "").replace("]", "").strip().split(",");
	}
	
	//region Array getter overloads
	public int[] getAsIntArray() {
		try {
			return Arrays.stream(this.getAsArray()).mapToInt(Integer::parseInt).toArray();
		} catch (Exception e) {
			throw new FormatException("Value of property is not an int array: " + this.value);
		}
	}
	
	public long[] getAsLongArray() {
		try {
			return Arrays.stream(this.getAsArray()).mapToLong(Long::parseLong).toArray();
		} catch (Exception e) {
			throw new FormatException("Value of property is not a long array: " + this.value);
		}
	}
	
	public double[] getAsDoubleArray() {
		try {
			return Arrays.stream(this.getAsArray()).mapToDouble(Double::parseDouble).toArray();
		} catch (Exception e) {
			throw new FormatException("Value of property is not a double array: " + this.value);
		}
	}
	
	public boolean[] getAsBooleanArray() {
		String[] array = this.getAsArray();
		try {
			boolean[] result = new boolean[array.length];
			for (int i = 0; i < result.length; i++) {
				if (array[i].equalsIgnoreCase("true") || array[i].equalsIgnoreCase("false")) {
					result[i] = Boolean.parseBoolean(array[i]);
				} else {
					throw new FormatException("Value of property is not a boolean array: " + this.value);
				}
			}
			return result;
		} catch (FormatException e) {
			throw new FormatException("Value of property is not a boolean array: " + this.value, e);
		} catch (Exception e) {
			throw new FormatException("Value of property is not a boolean array: " + this.value);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T[] getAsArray(@NotNull StringConverter<T> converter) {
		String[] array = this.getAsArray();
		Object[] result = new Object[array.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = converter.fromString(array[i]);
		}
		return (T[]) result;
	}
	//endregion
	
	@Override
	public int compareTo(@NotNull Property property) {
		return this.key.compareTo(property.key);
	}
	
	//region Object overrides
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Property property)) return false;
		
		if (!this.key.equals(property.key)) return false;
		return Objects.equals(this.value, property.value);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.key, this.value);
	}
	
	@Override
	public String toString() {
		return "Property{key='" + this.key + '\'' + ", value='" + this.value + '\'' + "}";
	}
	
	public String toShortString() {
		return this.key + "=" + this.value;
	}
	//endregion
}
