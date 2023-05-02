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
	 * Parses the given json string into a {@link Json} element
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
	 * @return True if the Json element is a {@link JsonArray}
	 */
	default boolean isArray() {
		return this instanceof JsonArray;
	}
	
	/**
	 * @return The Json element as a {@link JsonArray}
	 * @throws JsonException If the Json element is not a {@link JsonArray}
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
	 * @return True if the Json element is a {@link JsonPrimitive}
	 */
	default boolean isPrimitive() {
		return this instanceof JsonPrimitive;
	}
	
	/**
	 * @return The Json element as a {@link JsonPrimitive}
	 * @throws JsonException If the Json element is not a {@link JsonPrimitive}
	 */
	default JsonPrimitive getAsPrimitive() {
		if (this.isPrimitive()) {
			return (JsonPrimitive) this;
		}
		throw new JsonException("Not a json primitive: " + this.getName());
	}
	
	/**
	 * @return True if the Json element is a {@link JsonBoolean}
	 */
	default boolean isBoolean() {
		throw new JsonException("Not a json primitive: " + this.getName());
	}
	
	/**
	 * @return The Json element as a {@link Boolean}
	 * @throws JsonException If the Json element is not a {@link JsonBoolean}
	 */
	default boolean getAsBoolean() {
		throw new JsonException("Not a json primitive: " + this.getName());
	}
	
	/**
	 * @return True if the Json element is a {@link JsonNumber}
	 */
	default boolean isNumber() {
		throw new JsonException("Not a json primitive: " + this.getName());
	}
	
	/**
	 * @return The Json element as a {@link Number}
	 * @throws JsonException If the Json element is not a {@link JsonNumber}
	 */
	default Number getAsNumber() {
		throw new JsonException("Not a json primitive: " + this.getName());
	}
	
	/**
	 * @return The Json element as a {@link Integer}
	 * @throws JsonException If the Json element is not a {@link JsonNumber}
	 */
	default int getAsInt() {
		throw new JsonException("Not a json primitive: " + this.getName());
	}
	
	/**
	 * @return The Json element as a {@link Long}
	 * @throws JsonException If the Json element is not a {@link JsonNumber}
	 */
	default long getAsLong() {
		throw new JsonException("Not a json primitive: " + this.getName());
	}
	
	/**
	 * @return The Json element as a {@link Double}
	 * @throws JsonException If the Json element is not a {@link JsonNumber}
	 */
	default double getAsDouble() {
		throw new JsonException("Not a json primitive: " + this.getName());
	}
	
	/**
	 * @return True if the Json element is a {@link JsonString}
	 */
	default boolean isString() {
		throw new JsonException("Not a json primitive: " + this.getName());
	}
	
	/**
	 * @return The Json element as a {@link String}
	 * @throws JsonException If the Json element is not a {@link JsonString}
	 */
	default String getAsString() {
		throw new JsonException("Not a json primitive: " + this.getName());
	}
	//endregion
	
	//region JsonObject
	
	/**
	 * @return True if the Json element is a {@link JsonObject}
	 */
	default boolean isObject() {
		return this instanceof JsonObject;
	}
	
	/**
	 * @return The Json element as a {@link JsonObject}
	 * @throws JsonException If the Json element is not a {@link JsonObject}
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
	 * @return True if the Json element is a {@link JsonNull}
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
