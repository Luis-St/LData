package net.luis.data.json;

import com.google.common.collect.Maps;
import net.luis.data.json.exception.JsonException;
import net.luis.data.json.primitive.JsonBoolean;
import net.luis.data.json.primitive.JsonNumber;
import net.luis.data.json.primitive.JsonPrimitive;
import net.luis.data.json.primitive.JsonString;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Luis-St
 *
 */

public class JsonObject implements JsonElement {

	private final Map<String, JsonElement> map = Maps.newTreeMap();
	
	public JsonObject() {
	
	}
	
	//region Adders
	public void add(String key, JsonElement value) {
		this.map.put(key, value == null ? JsonNull.INSTANCE : value);
	}
	
	public void add(String key, String value) {
		this.map.put(key, value == null ? JsonNull.INSTANCE : new JsonString(value));
	}
	
	public void add(String key, Number value) {
		this.map.put(key, value == null ? JsonNull.INSTANCE : new JsonNumber(value));
	}
	
	public void add(String key, Boolean value) {
		this.map.put(key, value == null ? JsonNull.INSTANCE : new JsonBoolean(value));
	}
	
	public void add(String key, JsonObject value) {
		this.map.put(key, value == null ? JsonNull.INSTANCE : value);
	}
	
	public void add(String key, JsonArray value) {
		this.map.put(key, value == null ? JsonNull.INSTANCE : value);
	}
	//endregion
	
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
