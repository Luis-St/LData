package net.luis.data.json;

import net.luis.data.json.config.JsonConfig;
import net.luis.data.json.exception.JsonException;
import net.luis.data.json.io.JsonReader;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Json is the base interface for all json types
 *
 * @see JsonArray
 * @see JsonNull
 * @see JsonObject
 * @see JsonPrimitive
 *
 * @author Luis-St
 */

public sealed interface Json permits JsonArray, JsonNull, JsonObject, JsonPrimitive {
	
	/**
	 * Parses the given json string into a Json element
	 * @param json The json string to parse
	 * @return The parsed Json element
	 */
	static @NotNull Json parse(String json) {
		return new JsonReader(json).toJson();
	}
	
	/**
	 * @return The name of the Json element
	 */
	@NotNull
	@ApiStatus.Internal
	String getName();
	
	/**
	 * @return A copy of the Json element
	 */
	@NotNull Json copy();
	
	//region JsonArray
	/**
	 * @return True if the Json element is a {@link JsonArray json array}
	 */
	default boolean isArray() {
		return this instanceof JsonArray;
	}
	
	/**
	 * @return The Json element as a {@link JsonArray json array}
	 * @throws JsonException If the Json element is not a {@link JsonArray json array}
	 */
	default JsonArray getAsArray() {
		if (this.isArray()) {
			return (JsonArray) this;
		}
		throw new JsonException("Not a json array: " + this.getName());
	}
	//endregion
	
	//region JsonPrimitive
	/**
	 * @return True if the Json element is a {@link JsonPrimitive json primitive}
	 */
	default boolean isPrimitive() {
		return this instanceof JsonPrimitive;
	}
	
	/**
	 * @return The Json element as a {@link JsonPrimitive json primitive}
	 * @throws JsonException If the Json element is not a {@link JsonPrimitive json primitive}
	 */
	default JsonPrimitive getAsPrimitive() {
		if (this.isPrimitive()) {
			return (JsonPrimitive) this;
		}
		throw new JsonException("Not a json primitive: " + this.getName());
	}
	
	/**
	 * @return True if the Json element is a {@link JsonBoolean json boolean}
	 */
	default boolean isBoolean() {
		throw new JsonException("Not a json primitive: " + this.getName());
	}
	
	/**
	 * @return The Json element as a {@link Boolean boolean}
	 * @throws JsonException If the Json element is not a {@link JsonBoolean json boolean}
	 */
	default boolean getAsBoolean() {
		throw new JsonException("Not a json primitive: " + this.getName());
	}
	
	/**
	 * @return True if the Json element is a {@link JsonNumber json number}
	 */
	default boolean isNumber() {
		throw new JsonException("Not a json primitive: " + this.getName());
	}
	
	/**
	 * @return The Json element as a {@link Number number}
	 * @throws JsonException If the Json element is not a {@link JsonNumber json number}
	 */
	default Number getAsNumber() {
		throw new JsonException("Not a json primitive: " + this.getName());
	}
	
	/**
	 * @return The Json element as a {@link Integer integer}
	 * @throws JsonException If the Json element is not a {@link JsonNumber json number}
	 */
	default int getAsInt() {
		throw new JsonException("Not a json primitive: " + this.getName());
	}
	
	/**
	 * @return The Json element as a {@link Long long}
	 * @throws JsonException If the Json element is not a {@link JsonNumber json number}
	 */
	default long getAsLong() {
		throw new JsonException("Not a json primitive: " + this.getName());
	}
	
	/**
	 * @return The Json element as a {@link Double double}
	 * @throws JsonException If the Json element is not a {@link JsonNumber json number}
	 */
	default double getAsDouble() {
		throw new JsonException("Not a json primitive: " + this.getName());
	}
	
	/**
	 * @return True if the Json element is a {@link JsonString json string}
	 */
	default boolean isString() {
		throw new JsonException("Not a json primitive: " + this.getName());
	}
	
	/**
	 * @return The Json element as a {@link String string}
	 * @throws JsonException If the Json element is not a {@link JsonString json string}
	 */
	default String getAsString() {
		throw new JsonException("Not a json primitive: " + this.getName());
	}
	//endregion
	
	//region JsonObject
	/**
	 * @return True if the Json element is a {@link JsonObject json object}
	 */
	default boolean isObject() {
		return this instanceof JsonObject;
	}
	
	/**
	 * @return The Json element as a {@link JsonObject json object}
	 * @throws JsonException If the Json element is not a {@link JsonObject json object}
	 */
	default JsonObject getAsObject() {
		if (this.isObject()) {
			return (JsonObject) this;
		}
		throw new JsonException("Not a json object: " + this.getName());
	}
	//endregion
	
	//region JsonNull
	/**
	 * @return True if the Json element is a {@link JsonNull json null}
	 */
	default boolean isNull() {
		return this instanceof JsonNull;
	}
	//endregion
	
	/**
	 * Converts the Json element to a json string using the json configuration
	 * @param config The json configuration to use
	 * @return The json string
	 * @throws NullPointerException If the json configuration is null
	 */
	@NotNull String toString(JsonConfig config);
}
