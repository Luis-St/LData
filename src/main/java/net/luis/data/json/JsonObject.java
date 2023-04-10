package net.luis.data.json;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.luis.data.json.exception.JsonException;
import net.luis.data.json.primitive.JsonBoolean;
import net.luis.data.json.primitive.JsonNumber;
import net.luis.data.json.primitive.JsonPrimitive;
import net.luis.data.json.primitive.JsonString;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 *
 * @author Luis-St
 *
 */

public class JsonObject implements JsonElement, Iterable<Map.Entry<String, JsonElement>> {
	
	private final Map<String, JsonElement> map = Maps.newTreeMap();
	
	public JsonObject() {
	
	}
	
	@Override
	public JsonElement copy() {
		JsonObject object = new JsonObject();
		for (Map.Entry<String, JsonElement> entry : this.map.entrySet()) {
			object.add(entry.getKey(), entry.getValue().copy());
		}
		return object;
	}
	
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
	
	//region Adders
	public void add(String key, JsonElement value) {
		this.map.put(validateKey(key), value == null ? JsonNull.INSTANCE : value);
	}
	
	public void add(String key, String value) {
		this.map.put(validateKey(key), value == null ? JsonNull.INSTANCE : new JsonString(value));
	}
	
	public void add(String key, Number value) {
		this.map.put(validateKey(key), value == null ? JsonNull.INSTANCE : new JsonNumber(value));
	}
	
	public void add(String key, Boolean value) {
		this.map.put(validateKey(key), value == null ? JsonNull.INSTANCE : new JsonBoolean(value));
	}
	
	public void add(String key, JsonObject value) {
		this.map.put(validateKey(key), value == null ? JsonNull.INSTANCE : value);
	}
	
	public void add(String key, JsonArray value) {
		this.map.put(validateKey(key), value == null ? JsonNull.INSTANCE : value);
	}
	//endregion
	
	public int size() {
		return this.map.size();
	}
	
	public Set<String> keySet() {
		return this.map.keySet();
	}
	
	public boolean has(String key) {
		return this.map.containsKey(key);
	}
	
	//region Getters
	public JsonElement get(String key) {
		if (this.has(key)) {
			return this.map.get(key);
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
	
	public JsonArray getAsArray(String key) {
		if (this.has(key)) {
			if (this.get(key).isArray()) {
				return this.get(key).getAsArray();
			}
			throw new JsonException("Not a JsonArray: " + this.get(key));
		}
		throw new IllegalStateException("No such key: " + key);
	}
	//endregion
	
	@Override
	public @NotNull Iterator<Map.Entry<String, JsonElement>> iterator() {
		return this.map.entrySet().iterator();
	}
	
	@Override
	public String toJsonString() {
		if (this.map.isEmpty()) {
			return "{}";
		}
		List<String> values = Lists.newArrayList();
		for (Map.Entry<String, JsonElement> entry : this.map.entrySet()) {
			String key = entry.getKey();
			if (key.charAt(0) == '"' && key.charAt(key.length() - 1) == '"') {
				values.add("\"" + "\\\"" + key.substring(1, key.length() - 1) + "\\\"" + "\"" + ":" + entry.getValue().toJsonString());
			} else {
				values.add("\"" + key + "\":" + entry.getValue().toJsonString());
			}
		}
		return "{" + String.join(",", values) + "}";
	}
	
	//region Object overrides
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof JsonObject that)) return false;
		
		return this.map.equals(that.map);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.map);
	}
	
	@Override
	public String toString() {
		return this.map.toString();
	}
	//endregion
}
