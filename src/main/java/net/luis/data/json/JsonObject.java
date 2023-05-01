package net.luis.data.json;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.luis.data.common.io.Writable;
import net.luis.data.json.config.JsonConfig;
import net.luis.data.json.exception.JsonException;
import net.luis.data.json.io.JsonWriter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

/**
 * Json element that represents a json object
 *
 * @author Luis-St
 */

public final class JsonObject implements Json, Iterable<Map.Entry<String, Json>>, Writable<JsonWriter> {
	
	private final Map<String, Json> elements = Maps.newTreeMap();
	
	//region Constructors
	/**
	 * Constructs a new empty json object
	 */
	public JsonObject() {
		super();
	}
	
	/**
	 * Constructs a new json object with the given json element
	 * @param key The key of the element
	 * @param value The value of the element
	 */
	public JsonObject(String key, Json value) {
		this.add(key, value);
	}
	
	/**
	 * Constructs a new json object with the given string value
	 * @param key The key of the element
	 * @param value The string value
	 */
	public JsonObject(String key, String value) {
		this.add(key, value);
	}
	
	/**
	 * Constructs a new json object with the given number value
	 * @param key The key of the element
	 * @param value The number value
	 */
	public JsonObject(String key, Number value) {
		this.add(key, value);
	}
	
	/**
	 * Constructs a new json object with the given boolean value
	 * @param key The key of the element
	 * @param value The boolean value
	 */
	public JsonObject(String key, boolean value) {
		this.add(key, value);
	}
	
	/**
	 * Constructs a new json object with the given json object value
	 * @param key The key of the element
	 * @param value The json object value
	 */
	public JsonObject(String key, JsonObject value) {
		this.add(key, value);
	}
	//endregion
	
	//region Validation
	private static @NotNull String validateKey(String key) {
		if (key == null) {
			throw new NullPointerException("Json key is null");
		}
		if (key.isEmpty()) {
			throw new IllegalArgumentException("Json key is empty");
		}
		return key;
	}
	//endregion
	
	@Override
	public @NotNull String getName() {
		return "json object";
	}
	
	@Override
	public @NotNull JsonObject copy() {
		JsonObject object = new JsonObject();
		for (Map.Entry<String, Json> entry : this.elements.entrySet()) {
			object.add(entry.getKey(), entry.getValue().copy());
		}
		return object;
	}
	
	//region Adders
	/**
	 * Adds the given json element to this json object
	 * @param key The key of the element
	 * @param value The json element
	 * @throws NullPointerException If the key is null
	 * @throws IllegalArgumentException If the key is empty
	 */
	public void add(String key, Json value) {
		this.elements.put(validateKey(key), value == null ? JsonNull.INSTANCE : value);
	}
	
	/**
	 * Adds the given string value to this json object as a {@link JsonString json string}
	 * @param key The key of the element
	 * @param value The string value
	 * @throws NullPointerException If the key is null
	 * @throws IllegalArgumentException If the key is empty
	 */
	public void add(String key, String value) {
		this.elements.put(validateKey(key), value == null ? JsonNull.INSTANCE : new JsonString(value));
	}
	
	/**
	 * Adds the given number value to this json object as a {@link JsonNumber json number}
	 * @param key The key of the element
	 * @param value The number value
	 * @throws NullPointerException If the key is null
	 * @throws IllegalArgumentException If the key is empty
	 */
	public void add(String key, Number value) {
		this.elements.put(validateKey(key), value == null ? JsonNull.INSTANCE : new JsonNumber(value));
	}
	
	/**
	 * Adds the given boolean value to this json object as a {@link JsonBoolean json boolean}
	 * @param key The key of the element
	 * @param value The boolean value
	 * @throws NullPointerException If the key is null
	 * @throws IllegalArgumentException If the key is empty
	 */
	public void add(String key, boolean value) {
		this.elements.put(validateKey(key), new JsonBoolean(value));
	}
	
	/**
	 * Adds the given json array to this json object
	 * @param key The key of the element
	 * @param value The json array
	 * @throws NullPointerException If the key is null
	 * @throws IllegalArgumentException If the key is empty
	 */
	public void add(String key, JsonArray value) {
		this.elements.put(validateKey(key), value == null ? JsonNull.INSTANCE : value);
	}
	
	/**
	 * Adds the given json object to this json object
	 * @param key The key of the element
	 * @param value The json object
	 * @throws NullPointerException If the key is null
	 * @throws IllegalArgumentException If the key is empty
	 */
	public void add(String key, JsonObject value) {
		this.elements.put(validateKey(key), value == null ? JsonNull.INSTANCE : value);
	}
	//endregion
	
	/**
	 * @return The number of elements in this json object
	 */
	public int size() {
		return this.elements.size();
	}
	
	/**
	 * @return The keys of the elements in this json object as an unmodifiable set
	 */
	public @NotNull Set<String> keySet() {
		return this.elements.keySet();
	}
	
	/**
	 * Checks if this json object contains an element with the given key
	 * @param key The key to check
	 * @return True if this json object contains an element with the given key, false otherwise
	 */
	public boolean has(String key) {
		return this.elements.containsKey(key);
	}
	
	//region Getters
	/**
	 * Gets the json element with the given key
	 * @param key The key of the element
	 * @return The json element
	 * @throws JsonException If this json object does not contain an element with the given key
	 */
	public Json get(String key) {
		if (this.has(key)) {
			return this.elements.get(key);
		}
		throw new JsonException("No such json key: " + key);
	}
	
	/**
	 * Gets the json element with the given key as a {@link JsonArray json array}
	 * @param key The key of the element
	 * @return The json array
	 * @throws JsonException If this json object does not contain an element with the given key
	 */
	public JsonArray getAsArray(String key) {
		if (this.has(key)) {
			return this.get(key).getAsArray();
		}
		throw new JsonException("No such json key: " + key);
	}
	
	/**
	 * Gets the json element with the given key as a {@link JsonPrimitive json primitive}
	 * @param key The key of the element
	 * @return The json primitive
	 * @throws JsonException If this json object does not contain an element with the given key
	 */
	public JsonPrimitive getAsPrimitive(String key) {
		if (this.has(key)) {
			return this.get(key).getAsPrimitive();
		}
		throw new JsonException("No such json key: " + key);
	}
	
	/**
	 * Gets the json element with the given key as a {@link JsonObject json object}
	 * @param key The key of the element
	 * @return The json object
	 * @throws JsonException If this json object does not contain an element with the given key
	 */
	public JsonObject getAsObject(String key) {
		if (this.has(key)) {
			return this.get(key).getAsObject();
		}
		throw new JsonException("No such json key: " + key);
	}
	//endregion
	
	@Override
	public @NotNull Iterator<Map.Entry<String, Json>> iterator() {
		return this.elements.entrySet().iterator();
	}
	
	@Override
	public @NotNull String toString(JsonConfig config) {
		if (this.elements.isEmpty()) {
			return "{}";
		}
		Objects.requireNonNull(config, "Json config must not be null");
		boolean simplify = JsonHelper.canBeSimplified(this.elements.values(), config.simplifyPrimitiveObjects());
		List<String> values = Lists.newArrayList();
		for (Map.Entry<String, Json> entry : this.elements.entrySet()) {
			String key = JsonHelper.quote(entry.getKey(), config);
			if (key.substring(1, key.length() - 1).isBlank() && !config.allowBlankKeys()) {
				throw new JsonException("Key of value " + entry.getValue() + " is blank which is not allowed in this configuation");
			}
			if (config.prettyPrint()) {
				if (simplify) {
					values.add(key + ": " + entry.getValue().toString(config));
				} else {
					values.add(JsonHelper.correctIndents(entry.getValue(), config, key + ": "));
				}
			} else {
				values.add(key + ":" + entry.getValue().toString(config));
			}
		}
		if (!config.prettyPrint()) {
			return "{" + String.join(",", values) + "}";
		}
		if (simplify) {
			return "{" + String.join(", ", values) + "}";
		}
		return "{" + System.lineSeparator() + String.join("," + System.lineSeparator(), values) + System.lineSeparator() + config.indent() + "}";
	}
	
	@Override
	public void write(File file) {
		JsonWriter writer = new JsonWriter(Objects.requireNonNull(file, "File must not be null"));
		writer.write(this);
		writer.flushAndClose();
	}
	
	@Override
	public void write(JsonWriter writer) {
		Objects.requireNonNull(writer, "Json writer must not be null").write(this);
	}
	
	//region Object overrides
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof JsonObject that)) return false;
		
		return this.elements.equals(that.elements);
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
