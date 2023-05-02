package net.luis.data.properties;

import com.google.common.collect.Lists;
import net.luis.data.properties.config.PropertyConfig;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Property that represents an array of values
 *
 * @author Luis-St
 */

public final class PropertyArray implements Property {
	
	private final String key;
	private final List<String> values;
	
	/**
	 * Constructs a new property array with the given key
	 * @param key The key of the property array
	 * @throws NullPointerException If the key is null
	 */
	public PropertyArray(String key) {
		this.key = Objects.requireNonNull(key, "Property key must not be null");
		this.values = Lists.newArrayList();
	}
	
	@Override
	public @NotNull String getName() {
		return "property array";
	}
	
	@Override
	public @NotNull String getKey() {
		return this.key.toLowerCase();
	}
	
	@Override
	public @NotNull PropertyArray copy() {
		PropertyArray copy = new PropertyArray(this.key);
		copy.values.addAll(this.values);
		return copy;
	}
	
	//region Adders
	
	/**
	 * Adds the given boolean value to the property array
	 * @param value The boolean value to add
	 * @return True if the value was added
	 */
	public boolean add(boolean value) {
		return this.values.add(String.valueOf(value));
	}
	
	/**
	 * Adds the given number value to the property array
	 * @param value The number value to add
	 * @return True if the value was added
	 */
	public boolean add(Number value) {
		return this.values.add(String.valueOf(value));
	}
	
	/**
	 * Adds the given string value to the property array
	 * @param value The string value to add
	 * @return True if the value was added
	 */
	public boolean add(String value) {
		return this.values.add(value);
	}
	
	/**
	 * Adds all values of the given property array to this property array
	 * @param array The property array to add
	 */
	public void addAll(PropertyArray array) {
		this.values.addAll(Objects.requireNonNull(array, "Property array must not be null").values);
	}
	
	/**
	 * Adds all values of the given boolean array to this property array
	 * @param values The boolean array to add
	 */
	public void addAll(boolean... values) {
		Stream.of(values).map(String::valueOf).forEach(this.values::add);
	}
	
	/**
	 * Adds all values of the given number array to this property array
	 * @param values The number array to add
	 */
	public void addAll(Number... values) {
		Arrays.stream(values).map(String::valueOf).forEach(this.values::add);
	}
	
	/**
	 * Adds all values of the given string array to this property array
	 * @param values The string array to add
	 */
	public void addAll(String... values) {
		this.values.addAll(Arrays.asList(values));
	}
	//endregion
	
	//region List methods
	
	/**
	 * @return The size of the property array
	 */
	public int size() {
		return this.values.size();
	}
	
	/**
	 * @return True if the property array is empty
	 */
	public boolean isEmpty() {
		return this.values.isEmpty();
	}
	
	/**
	 * Checks if the property array contains all values of the given array
	 * @param array The array to check
	 * @return True if the property array contains all values of the given array
	 */
	public boolean containsAll(PropertyArray array) {
		return new HashSet<>(this.values).containsAll(Objects.requireNonNull(array, "Property array must not be null").values);
	}
	//endregion
	
	//region Getters
	
	/**
	 * @return The value at the given index as a boolean using {@link Boolean#parseBoolean(String)}
	 * @throws IndexOutOfBoundsException If the index is out of bounds
	 */
	public boolean getAsBoolean(int index) {
		return Boolean.parseBoolean(this.values.get(index));
	}
	
	/**
	 * @return The value at the given index as a number using {@link Double#parseDouble(String)}
	 * @throws IndexOutOfBoundsException If the index is out of bounds
	 * @throws NumberFormatException If the value at the given index is not a number
	 */
	public @NotNull Number getAsNumber(int index) {
		return Double.parseDouble(this.values.get(index));
	}
	
	/**
	 * @return The value at the given index as an integer using {@link Integer#parseInt(String)}
	 * @throws IndexOutOfBoundsException If the index is out of bounds
	 * @throws NumberFormatException If the value at the given index is not an integer
	 */
	public int getAsInt(int index) {
		return Integer.parseInt(this.values.get(index));
	}
	
	/**
	 * @return The value at the given index as a long using {@link Long#parseLong(String)}
	 * @throws IndexOutOfBoundsException If the index is out of bounds
	 * @throws NumberFormatException If the value at the given index is not a long
	 */
	public long getAsLong(int index) {
		return Long.parseLong(this.values.get(index));
	}
	
	/**
	 * @return The value at the given index as a double using {@link Double#parseDouble(String)}
	 * @throws IndexOutOfBoundsException If the index is out of bounds
	 * @throws NumberFormatException If the value at the given index is not a double
	 */
	public double getAsDouble(int index) {
		return Double.parseDouble(this.values.get(index));
	}
	
	/**
	 * @return The value at the given index as a string
	 * @throws IndexOutOfBoundsException If the index is out of bounds
	 */
	public String getAsString(int index) {
		return this.values.get(index);
	}
	
	/**
	 * @return The property array as a boolean array using {@link Boolean#parseBoolean(String)}
	 */
	public boolean[] getAsBooleanArray() {
		boolean[] array = new boolean[this.values.size()];
		for (int i = 0; i < this.values.size(); i++) {
			array[i] = Boolean.parseBoolean(this.values.get(i));
		}
		return array;
	}
	
	/**
	 * @return The property array as a number array using {@link Double#parseDouble(String)}
	 * @throws NumberFormatException If a value is not a number
	 */
	public Number[] getAsNumberArray() {
		Number[] array = new Number[this.values.size()];
		for (int i = 0; i < this.values.size(); i++) {
			array[i] = Double.parseDouble(this.values.get(i));
		}
		return array;
	}
	
	/**
	 * @return The property array as an integer array using {@link Integer#parseInt(String)}
	 * @throws NumberFormatException If a value is not an integer
	 */
	public int[] getAsIntArray() {
		int[] array = new int[this.values.size()];
		for (int i = 0; i < this.values.size(); i++) {
			array[i] = Integer.parseInt(this.values.get(i));
		}
		return array;
	}
	
	/**
	 * @return The property array as a long array using {@link Long#parseLong(String)}
	 * @throws NumberFormatException If a value is not a long
	 */
	public long[] getAsLongArray() {
		long[] array = new long[this.values.size()];
		for (int i = 0; i < this.values.size(); i++) {
			array[i] = Long.parseLong(this.values.get(i));
		}
		return array;
	}
	
	/**
	 * @return The property array as a double array using {@link Double#parseDouble(String)}
	 * @throws NumberFormatException If a value is not a double
	 */
	public double[] getAsDoubleArray() {
		double[] array = new double[this.values.size()];
		for (int i = 0; i < this.values.size(); i++) {
			array[i] = Double.parseDouble(this.values.get(i));
		}
		return array;
	}
	
	/**
	 * @return The property array as a string array
	 */
	public String[] getAsStringArray() {
		return this.values.toArray(new String[0]);
	}
	//endregion
	
	@Override
	public @NotNull String toString(PropertyConfig config) {
		Objects.requireNonNull(config, "Property config must not be null");
		return "[" + String.join(config.prettyPrint() ? ", " : ",", this.values) + "]";
	}
	
	//region Object overrides
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PropertyArray that)) return false;
		
		if (!this.key.equals(that.key)) return false;
		return this.values.equals(that.values);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.key, this.values);
	}
	
	@Override
	public String toString() {
		return this.key + "=" + this.values.toString();
	}
	//endregion
}
