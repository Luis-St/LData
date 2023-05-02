package net.luis.data.json;

import com.google.common.collect.Lists;
import net.luis.data.json.config.JsonConfig;
import net.luis.utils.util.Utils;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Stream;

/**
 * Json element that represents a json array
 *
 * @author Luis-St
 */

public final class JsonArray implements Json, Iterable<Json> {
	
	private final List<Json> elements = Lists.newArrayList();
	
	/**
	 * Constructs a new empty {@link JsonArray json array}
	 */
	public JsonArray() {
		super();
	}
	
	/**
	 * Constructs a new {@link JsonArray} with the given elements
	 * @param elements The elements of the json array
	 * @throws NullPointerException If the elements are null
	 */
	public JsonArray(Json... elements) {
		this.elements.addAll(Arrays.asList(elements));
	}
	
	@Override
	public @NotNull String getName() {
		return "json array";
	}
	
	@Override
	public @NotNull JsonArray copy() {
		JsonArray array = new JsonArray();
		for (Json element : this.elements) {
			array.add(element.copy());
		}
		return array;
	}
	
	//region Adders
	
	/**
	 * Adds the given element to the json array
	 * @param element The element to add
	 * @return True if the element was added
	 */
	public boolean add(Json element) {
		return this.elements.add(element == null ? JsonNull.INSTANCE : element);
	}
	
	/**
	 * Adds the given boolean as a {@link JsonBoolean} to the json array
	 * @param value The boolean to add
	 * @return True if the boolean was added
	 */
	public boolean add(boolean value) {
		return this.add(new JsonBoolean(value));
	}
	
	/**
	 * Adds the given number as a {@link JsonNumber} to the json array
	 * @param value The number to add
	 * @return True if the number was added
	 */
	public boolean add(Number value) {
		return this.add(new JsonNumber(value));
	}
	
	/**
	 * Adds the given string as a {@link JsonString} to the json array
	 * @param value The string to add
	 * @return True if the string was added
	 */
	public boolean add(String value) {
		return this.add(new JsonString(value));
	}
	
	/**
	 * Adds all elements of the given {@link JsonArray} to this json array
	 * @param array The json array to add
	 * @return True if all elements were added
	 * @throws NullPointerException If the json array is null
	 */
	public boolean addAll(JsonArray array) {
		return this.elements.addAll(Objects.requireNonNull(array, "Json array must not be null").elements);
	}
	
	/**
	 * Adds all values of the given boolean array as {@link JsonBoolean} to the json array
	 * @param values The boolean array to add
	 */
	public void addAll(boolean... values) {
		Stream.of(values).map(String::valueOf).forEach(this::add);
	}
	
	/**
	 * Adds all values of the given number array as {@link JsonNumber} to the json array
	 * @param values The number array to add
	 */
	public void addAll(Number... values) {
		Arrays.stream(values).map(String::valueOf).forEach(this::add);
	}
	
	/**
	 * Adds all values of the given string array as {@link JsonString} to the json array
	 * @param values The string array to add
	 */
	public void addAll(String... values) {
		Arrays.asList(values).forEach(this::add);
	}
	//endregion
	
	//region List methods
	
	/**
	 * Replace the element at the given index with the given element
	 * @param index The index of the element to replace
	 * @param element The element to replace the old element with
	 * @return The old element
	 */
	public Json set(int index, Json element) {
		return this.elements.set(index, element == null ? JsonNull.INSTANCE : element);
	}
	
	/**
	 * Removes the element at the given index
	 * @param index The index of the element to remove
	 * @return The removed element
	 */
	public Json remove(int index) {
		return this.elements.remove(index);
	}
	
	/**
	 * Removes the given element
	 * @param element The element to remove
	 * @return True if the element was removed
	 */
	public boolean remove(Json element) {
		return this.elements.remove(element);
	}
	
	/**
	 * @return The size of the json array
	 */
	public int size() {
		return this.elements.size();
	}
	
	/**
	 * @return True if the json array is empty
	 */
	public boolean isEmpty() {
		return this.elements.isEmpty();
	}
	
	/**
	 * Checks if the json array contains the given element
	 * @param element The element to check
	 * @return True if the json array contains the element
	 */
	public boolean contains(Json element) {
		return this.elements.contains(element);
	}
	
	/**
	 * Checks if the json array contains all elements of the given json array
	 * @param array The json array to check
	 * @return True if the json array contains all elements of the given json array
	 * @throws NullPointerException If the json array is null
	 */
	public boolean containsAll(JsonArray array) {
		return new HashSet<>(this.elements).containsAll(Objects.requireNonNull(array, "Json array must not be null").elements);
	}
	
	@Override
	public @NotNull Iterator<Json> iterator() {
		return this.elements.iterator();
	}
	//endregion
	
	//region Getters
	
	/**
	 * Gets the element at the given index
	 * @param index The index of the element
	 * @return The element at the given index
	 */
	public Json get(int index) {
		return this.elements.get(index);
	}
	
	/**
	 * Gets the element at the given index as a {@link JsonObject}
	 * @param index The index of the element
	 * @return The element at the given index as a json object
	 */
	public JsonObject getAsObject(int index) {
		return this.elements.get(index).getAsObject();
	}
	
	/**
	 * Gets the element at the given index as a {@link JsonArray}
	 * @param index The index of the element
	 * @return The element at the given index as a json array
	 */
	public JsonArray getAsArray(int index) {
		return this.elements.get(index).getAsArray();
	}
	
	/**
	 * Gets the element at the given index as a {@link Boolean}
	 * @param index The index of the element
	 * @return The element at the given index as a boolean
	 */
	public boolean getAsBoolean(int index) {
		return this.elements.get(index).getAsBoolean();
	}
	
	/**
	 * Gets the element at the given index as a {@link Number}
	 * @param index The index of the element
	 * @return The element at the given index as a number
	 */
	public Number getAsNumber(int index) {
		return this.elements.get(index).getAsNumber();
	}
	
	/**
	 * Gets the element at the given index as a {@link Integer}
	 * @param index The index of the element
	 * @return The element at the given index as a integer
	 */
	public int getAsInt(int index) {
		return this.elements.get(index).getAsInt();
	}
	
	/**
	 * Gets the element at the given index as a {@link Long}
	 * @param index The index of the element
	 * @return The element at the given index as a long
	 */
	public long getAsLong(int index) {
		return this.elements.get(index).getAsLong();
	}
	
	/**
	 * Gets the element at the given index as a {@link Double}
	 * @param index The index of the element
	 * @return The element at the given index as a double
	 */
	public double getAsDouble(int index) {
		return this.elements.get(index).getAsDouble();
	}
	
	/**
	 * Gets the element at the given index as a {@link String}
	 * @param index The index of the element
	 * @return The element at the given index as a string
	 */
	public String getAsString(int index) {
		return this.elements.get(index).getAsString();
	}
	//endregion
	
	@Override
	public @NotNull String toString(JsonConfig config) {
		if (this.elements.isEmpty()) {
			return "[]";
		}
		Objects.requireNonNull(config, "Json config must not be null");
		boolean simplify = JsonHelper.canBeSimplified(this.elements, config.simplifyPrimitiveArrays());
		List<String> values = Utils.mapList(this.elements, element -> {
			if (config.prettyPrint() && !simplify) {
				return JsonHelper.correctIndents(element, config, "");
			}
			return element.toString(config);
		});
		if (!config.prettyPrint()) {
			return "[" + String.join(",", values) + "]";
		}
		if (simplify) {
			return "[" + String.join(", ", values) + "]";
		}
		return "[" + System.lineSeparator() + String.join("," + System.lineSeparator(), values) + System.lineSeparator() + config.indent() + "]";
	}
	
	//region Object overrides
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof JsonArray jsonArray)) return false;
		
		return this.elements.equals(jsonArray.elements);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.elements);
	}
	
	@Override
	public String toString() {
		return this.elements.toString();
	}
	//endregion
}
