package net.luis.data.json;

import net.luis.data.json.config.JsonConfig;
import net.luis.data.json.exception.JsonException;
import net.luis.data.json.io.JsonReader;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public sealed interface Json permits JsonArray, JsonNull, JsonObject, JsonPrimitive {
	
	static @NotNull Json parse(String json) {
		return new JsonReader(json).toJson();
	}
	
	@NotNull
	@ApiStatus.Internal
	String getName();
	
	@NotNull Json copy();
	
	//region JsonArray
	default boolean isArray() {
		return this instanceof JsonArray;
	}
	
	default JsonArray getAsArray() {
		if (this.isArray()) {
			return (JsonArray) this;
		}
		throw new JsonException("Not a json array: " + this.getName());
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
		throw new JsonException("Not a json primitive: " + this.getName());
	}
	
	default boolean isBoolean() {
		throw new JsonException("Not a json primitive: " + this.getName());
	}
	
	default boolean getAsBoolean() {
		throw new JsonException("Not a json primitive: " + this.getName());
	}
	
	default boolean isNumber() {
		throw new JsonException("Not a json primitive: " + this.getName());
	}
	
	default Number getAsNumber() {
		throw new JsonException("Not a json primitive: " + this.getName());
	}
	
	default int getAsInt() {
		throw new JsonException("Not a json primitive: " + this.getName());
	}
	
	default long getAsLong() {
		throw new JsonException("Not a json primitive: " + this.getName());
	}
	
	default double getAsDouble() {
		throw new JsonException("Not a json primitive: " + this.getName());
	}
	
	default boolean isString() {
		throw new JsonException("Not a json primitive: " + this.getName());
	}
	
	default String getAsString() {
		throw new JsonException("Not a json primitive: " + this.getName());
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
		throw new JsonException("Not a json object: " + this.getName());
	}
	//endregion
	
	//region JsonNull
	default boolean isNull() {
		return this instanceof JsonNull;
	}
	//endregion
	
	@NotNull String toString(JsonConfig config);
}
