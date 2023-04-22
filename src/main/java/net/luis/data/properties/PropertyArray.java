package net.luis.data.properties;

import com.google.common.collect.Lists;
import net.luis.data.properties.config.PropertyConfig;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public final class PropertyArray implements Property {
	
	private final String key;
	private final List<String> values;
	
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
		return this.key;
	}
	
	@Override
	public @NotNull PropertyArray copy() {
		PropertyArray copy = new PropertyArray(this.key);
		copy.values.addAll(this.values);
		return copy;
	}
	
	//region Adders
	public boolean add(boolean value) {
		return this.values.add(String.valueOf(value));
	}
	
	public boolean add(Number value) {
		return this.values.add(String.valueOf(value));
	}
	
	public boolean add(String value) {
		return this.values.add(value);
	}
	
	public void addAll(PropertyArray array) {
		this.values.addAll(Objects.requireNonNull(array, "Property array must not be null").values);
	}
	
	public void addAll(boolean... values) {
		Stream.of(values).map(String::valueOf).forEach(this.values::add);
	}
	
	public void addAll(Number... values) {
		Arrays.stream(values).map(String::valueOf).forEach(this.values::add);
	}
	
	public void addAll(String... values) {
		this.values.addAll(Arrays.asList(values));
	}
	//endregion
	
	//region List methods
	public int size() {
		return this.values.size();
	}
	
	public boolean isEmpty() {
		return this.values.isEmpty();
	}
	
	public boolean contains(Object value) {
		return this.values.contains(value);
	}
	
	public boolean containsAll(PropertyArray array) {
		return new HashSet<>(this.values).containsAll(Objects.requireNonNull(array, "Property array must not be null").values);
	}
	//endregion
	
	//region Getters
	public boolean getAsBoolean(int index) {
		return Boolean.parseBoolean(this.values.get(index));
	}
	
	public Number getAsNumber(int index) {
		return Double.parseDouble(this.values.get(index));
	}
	
	public int getAsInt(int index) {
		return Integer.parseInt(this.values.get(index));
	}
	
	public long getAsLong(int index) {
		return Long.parseLong(this.values.get(index));
	}
	
	public double getAsDouble(int index) {
		return Double.parseDouble(this.values.get(index));
	}
	
	public String getAsString(int index) {
		return this.values.get(index);
	}
	
	public boolean[] getAsBooleanArray() {
		boolean[] array = new boolean[this.values.size()];
		for (int i = 0; i < this.values.size(); i++) {
			array[i] = Boolean.parseBoolean(this.values.get(i));
		}
		return array;
	}
	
	public Number[] getAsNumberArray() {
		Number[] array = new Number[this.values.size()];
		for (int i = 0; i < this.values.size(); i++) {
			array[i] = Double.parseDouble(this.values.get(i));
		}
		return array;
	}
	
	public int[] getAsIntArray() {
		int[] array = new int[this.values.size()];
		for (int i = 0; i < this.values.size(); i++) {
			array[i] = Integer.parseInt(this.values.get(i));
		}
		return array;
	}
	
	public long[] getAsLongArray() {
		long[] array = new long[this.values.size()];
		for (int i = 0; i < this.values.size(); i++) {
			array[i] = Long.parseLong(this.values.get(i));
		}
		return array;
	}
	
	public double[] getAsDoubleArray() {
		double[] array = new double[this.values.size()];
		for (int i = 0; i < this.values.size(); i++) {
			array[i] = Double.parseDouble(this.values.get(i));
		}
		return array;
	}
	
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
