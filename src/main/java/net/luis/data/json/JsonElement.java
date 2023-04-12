package net.luis.data.json;

import net.luis.data.json.config.JsonConfig;
import net.luis.data.json.exception.JsonException;
import net.luis.data.json.primitive.JsonPrimitive;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public interface JsonElement {
	
	@NotNull JsonElement copy();
	
	//region JsonArray
	default boolean isArray() {
		return this instanceof JsonArray;
	}
	
	default JsonArray getAsArray() {
		if (this.isArray()) {
			return (JsonArray) this;
		}
		throw new JsonException("Not a json array: " + this);
	}
	//endregion
	
	//region JsonObject
	default boolean isObject() {
		return this instanceof JsonObject;
	}
	
	default JsonObject getAsObject() {
		if (this.isObject()) {
			return (JsonObject) this;
		}
		throw new JsonException("Not a json object: " + this);
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
		throw new JsonException("Not a json primitive: " + this);
	}
	
	default boolean isBoolean() {
		throw new JsonException("Not a json primitive: " + this);
	}
	
	default boolean getAsBoolean() {
		throw new JsonException("Not a json primitive: " + this);
	}
	
	default boolean isNumber() {
		throw new JsonException("Not a json primitive: " + this);
	}
	
	default Number getAsNumber() {
		throw new JsonException("Not a json primitive: " + this);
	}
	
	default int getAsInt() {
		throw new JsonException("Not a json primitive: " + this);
	}
	
	default long getAsLong() {
		throw new JsonException("Not a json primitive: " + this);
	}
	
	default double getAsDouble() {
		throw new JsonException("Not a json primitive: " + this);
	}
	
	default boolean isString() {
		throw new JsonException("Not a json primitive: " + this);
	}
	
	default String getAsString() {
		throw new JsonException("Not a json primitive: " + this);
	}
	//endregion
	
	//region JsonNull
	default boolean isNull() {
		return this instanceof JsonNull;
	}
	//endregion
	
	@NotNull String toJson(JsonConfig config);
}
