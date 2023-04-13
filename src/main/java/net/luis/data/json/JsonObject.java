package net.luis.data.json;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.luis.data.common.io.Writable;
import net.luis.data.json.config.JsonConfig;
import net.luis.data.json.exception.JsonException;
import net.luis.data.json.io.JsonWriter;
import net.luis.data.json.primitive.JsonBoolean;
import net.luis.data.json.primitive.JsonNumber;
import net.luis.data.json.primitive.JsonPrimitive;
import net.luis.data.json.primitive.JsonString;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

/**
 *
 * @author Luis-St
 *
 */

public class JsonObject implements Json, Iterable<Map.Entry<String, Json>>, Writable<JsonWriter> {
	
	private final Map<String, Json> elements = Maps.newTreeMap();
	
	//region Constructors
	public JsonObject() {
	
	}
	
	public JsonObject(String key, Json value) {
		this.add(key, value);
	}
	
	public JsonObject(String key, String value) {
		this.add(key, value);
	}
	
	public JsonObject(String key, Number value) {
		this.add(key, value);
	}
	
	public JsonObject(String key, boolean value) {
		this.add(key, value);
	}
	
	public JsonObject(String key, JsonObject value) {
		this.add(key, value);
	}
	//endregion
	
	//region Validation
	private static @NotNull String validateKey(String key) {
		if (key == null) {
			throw new NullPointerException("Key is null");
		}
		if (key.isEmpty()) {
			throw new IllegalArgumentException("Key is empty");
		}
		if (key.isBlank()) {
			throw new IllegalArgumentException("Key is blank");
		}
		return key;
	}
	//endregion
	
	@Override
	public @NotNull JsonObject copy() {
		JsonObject object = new JsonObject();
		for (Map.Entry<String, Json> entry : this.elements.entrySet()) {
			object.add(entry.getKey(), entry.getValue().copy());
		}
		return object;
	}
	
	//region Adders
	public void add(String key, Json value) {
		this.elements.put(validateKey(key), value == null ? JsonNull.INSTANCE : value);
	}
	
	public void add(String key, String value) {
		this.elements.put(validateKey(key), value == null ? JsonNull.INSTANCE : new JsonString(value));
	}
	
	public void add(String key, Number value) {
		this.elements.put(validateKey(key), value == null ? JsonNull.INSTANCE : new JsonNumber(value));
	}
	
	public void add(String key, boolean value) {
		this.elements.put(validateKey(key), new JsonBoolean(value));
	}
	
	public void add(String key, JsonObject value) {
		this.elements.put(validateKey(key), value == null ? JsonNull.INSTANCE : value);
	}
	//endregion
	
	public void add(String key, JsonArray value) {
		this.elements.put(validateKey(key), value == null ? JsonNull.INSTANCE : value);
	}
	
	public int size() {
		return this.elements.size();
	}
	
	public Set<String> keySet() {
		return this.elements.keySet();
	}
	
	public boolean has(String key) {
		return this.elements.containsKey(key);
	}
	
	//region Getters
	public Json get(String key) {
		if (this.has(key)) {
			return this.elements.get(key);
		}
		throw new IllegalStateException("No such key: " + key);
	}
	
	public JsonArray getAsArray(String key) {
		if (this.has(key)) {
			if (this.get(key).isArray()) {
				return this.get(key).getAsArray();
			}
			throw new JsonException("Not a JsonArray: " + this.get(key));
		}
		throw new IllegalStateException("No such key: " + key);
	}
	
	public JsonPrimitive getAsPrimitive(String key) {
		if (this.has(key)) {
			if (this.get(key).isPrimitive()) {
				return this.get(key).getAsPrimitive();
			}
			throw new JsonException("Not a JsonPrimitive: " + this.get(key));
		}
		throw new IllegalStateException("No such key: " + key);
	}
	
	public JsonObject getAsObject(String key) {
		if (this.has(key)) {
			if (this.get(key).isObject()) {
				return this.get(key).getAsObject();
			}
			throw new JsonException("Not a JsonObject: " + this.get(key));
		}
		throw new IllegalStateException("No such key: " + key);
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
		boolean simplify = JsonHelper.canBeSimplified(this.elements.values(), config.simplifyPrimitiveObjects());
		List<String> values = Lists.newArrayList();
		for (Map.Entry<String, Json> entry : this.elements.entrySet()) {
			String key = JsonHelper.quote(entry.getKey(), config);
			if (key.substring(1, key.length() - 1).isBlank() && !config.allowBlankKeys()) {
				throw new JsonException("Key of value " + entry.getValue() + " is blank which is not allowed");
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
		Objects.requireNonNull(writer, "Writer must not be null").write(this);
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
