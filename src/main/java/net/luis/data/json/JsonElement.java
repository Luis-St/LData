package net.luis.data.json;

import net.luis.data.json.exception.JsonException;
import net.luis.data.json.primitive.JsonPrimitive;

/**
 *
 * @author Luis-St
 *
 */

public interface JsonElement {
	
	JsonElement copy();
	
	//region JsonObject
	default boolean isObject() {
		return this instanceof JsonObject;
	}
	
	default JsonObject getAsObject() {
		if (this.isObject()) {
			return (JsonObject) this;
		}
		throw new JsonException("Not a JsonObject: " + this);
	}
	//endregion
	
	//region JsonArray
	default boolean isArray() {
		return this instanceof JsonArray;
	}
	
	default JsonArray getAsArray() {
		if (this.isArray()) {
			return (JsonArray) this;
		}
		throw new JsonException("Not a JsonArray: " + this);
	}
	//endregion
	
	//region JsonPrimitive
	default boolean isPrimitive() {
		return this instanceof JsonPrimitive;
	}
	
	default JsonPrimitive getAsPrimitive() {
		if (this.isPrimitive()) {
			return (JsonPrimitive) this;
		}
		throw new JsonException("Not a JsonPrimitive: " + this);
	}
	
	default boolean isBoolean() {
		throw new JsonException("Not a JsonPrimitive: " + this);
	}
	
	default boolean getAsBoolean() {
		throw new JsonException("Not a JsonPrimitive: " + this);
	}
	
	default boolean isNumber() {
		throw new JsonException("Not a JsonPrimitive: " + this);
	}
	
	default int getAsInt() {
		throw new JsonException("Not a JsonPrimitive: " + this);
	}
	
	default long getAsLong() {
		throw new JsonException("Not a JsonPrimitive: " + this);
	}
	
	default float getAsFloat() {
		throw new JsonException("Not a JsonPrimitive: " + this);
	}
	
	default double getAsDouble() {
		throw new JsonException("Not a JsonPrimitive: " + this);
	}
	
	default boolean isString() {
		throw new JsonException("Not a JsonPrimitive: " + this);
	}
	
	default String getAsString() {
		throw new JsonException("Not a JsonPrimitive: " + this);
	}
	//endregion
	
	//region JsonNull
	default boolean isNull() {
		return this instanceof JsonNull;
	}
	//endregion
	
	String toJsonString();
	
}
