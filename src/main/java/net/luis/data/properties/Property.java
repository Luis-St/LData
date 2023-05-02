package net.luis.data.properties;

import net.luis.data.json.JsonObject;
import net.luis.data.properties.config.PropertyConfig;
import net.luis.data.properties.exception.PropertyException;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Property is the base interface for all property types
 *
 * @see CommentedProperty
 * @see ObjectProperty
 * @see PropertyArray
 * @see PropertyJson
 * @see PropertyNull
 * @see PropertyPrimitive
 *
 * @author Luis-St
 */

public sealed interface Property permits CommentedProperty, ObjectProperty, PropertyArray, PropertyJson, PropertyNull, PropertyPrimitive {
	
	/**
	 * @return The name of the Property
	 */
	@NotNull
	@ApiStatus.Internal
	String getName();
	
	/**
	 * @return The key of the Property in lowercase
	 */
	@NotNull String getKey();
	
	/**
	 * @return A copy of the Property
	 */
	@NotNull Property copy();
	
	//region PropertyArray
	
	/**
	 * @return True if the Property is a {@link PropertyArray}
	 */
	default boolean isArray() {
		return this instanceof PropertyArray;
	}
	
	/**
	 * @return The Property as a {@link PropertyArray}
	 * @throws PropertyException If the Property is not a {@link PropertyArray}
	 */
	default PropertyArray getAsArray() {
		if (this.isArray()) {
			return (PropertyArray) this;
		}
		throw new PropertyException("Not a property array: " + this.getName());
	}
	//endregion
	
	//region PropertyPrimitive
	
	/**
	 * @return True if the Property is a {@link PropertyPrimitive}
	 */
	default boolean isPrimitive() {
		return this instanceof PropertyPrimitive;
	}
	
	/**
	 * @return The Property as a {@link PropertyPrimitive}
	 * @throws PropertyException If the Property is not a {@link PropertyPrimitive}
	 */
	default PropertyPrimitive getAsPrimitive() {
		if (this.isPrimitive()) {
			return (PropertyPrimitive) this;
		}
		throw new PropertyException("Not a property primitive: " + this.getName());
	}
	
	/**
	 * @return True if the Property is a {@link PropertyBoolean}
	 */
	default boolean isBoolean() {
		throw new PropertyException("Not a property primitive: " + this.getName());
	}
	
	/**
	 * @return The Property as a {@link Boolean}
	 * @throws PropertyException If the Property is not a {@link PropertyBoolean}
	 */
	default boolean getAsBoolean() {
		throw new PropertyException("Not a property primitive: " + this.getName());
	}
	
	/**
	 * @return True if the Property is a {@link PropertyNumber}
	 */
	default boolean isNumber() {
		throw new PropertyException("Not a property primitive: " + this.getName());
	}
	
	/**
	 * @return The Property as a {@link Number}
	 * @throws PropertyException If the Property is not a {@link PropertyNumber}
	 */
	default Number getAsNumber() {
		throw new PropertyException("Not a property primitive: " + this.getName());
	}
	
	/**
	 * @return The Property as an {@link Integer}
	 * @throws PropertyException If the Property is not a {@link PropertyNumber}
	 */
	default int getAsInt() {
		throw new PropertyException("Not a property number: " + this.getName());
	}
	
	/**
	 * @return The property as a {@link Long}
	 * @throws PropertyException If the Property is not a {@link PropertyNumber}
	 */
	default long getAsLong() {
		throw new PropertyException("Not a property number: " + this.getName());
	}
	
	/**
	 * @return The property as a {@link Double}
	 * @throws PropertyException If the Property is not a {@link PropertyNumber}
	 */
	default double getAsDouble() {
		throw new PropertyException("Not a property number: " + this.getName());
	}
	
	/**
	 * @return True if the Property is a {@link PropertyString}
	 */
	default boolean isString() {
		throw new PropertyException("Not a property primitive: " + this.getName());
	}
	
	/**
	 * @return The Property as a {@link String}
	 * @throws PropertyException If the Property is not a {@link PropertyString}
	 */
	default String getAsString() {
		throw new PropertyException("Not a property primitive: " + this.getName());
	}
	//endregion
	
	//region PropertyJson
	
	/**
	 * @return True if the Property is a {@link PropertyJson}
	 */
	default boolean isJson() {
		return this instanceof PropertyJson;
	}
	
	/**
	 * @return The Property as a {@link PropertyJson}
	 * @throws PropertyException If the Property is not a {@link PropertyJson}
	 */
	default JsonObject getAsJson() {
		throw new PropertyException("Not a property json: " + this.getName());
	}
	//endregion
	
	//region PropertyNull
	default boolean isNull() {
		return this instanceof PropertyNull;
	}
	//endregion
	
	//region ObjectProperty
	
	/**
	 * @return True if the Property is a {@link ObjectProperty object property}
	 */
	default boolean isObject() {
		return this instanceof ObjectProperty;
	}
	
	/**
	 * @return The Property as a {@link ObjectProperty object property}
	 * @throws PropertyException If the Property is not a {@link ObjectProperty object property}
	 */
	default ObjectProperty getAsObject() {
		if (this.isObject()) {
			return (ObjectProperty) this;
		}
		throw new PropertyException("Not a object property: " + this.getName());
	}
	//endregion
	
	//region CommentedProperty
	
	/**
	 * @return True if the Property is a {@link CommentedProperty}
	 */
	default boolean isCommented() {
		return this instanceof CommentedProperty;
	}
	
	/**
	 * @return The Property as a {@link CommentedProperty}
	 * @throws PropertyException If the Property is not a {@link CommentedProperty}
	 */
	default CommentedProperty getAsCommented() {
		if (this.isCommented()) {
			return (CommentedProperty) this;
		}
		throw new PropertyException("Not a commented property: " + this.getName());
	}
	//endregion
	
	/**
	 * Converts the Property to a string using the given configuration
	 * @param config The configuration to use
	 * @return The property string
	 * @throws NullPointerException If the configuration is null
	 */
	@NotNull String toString(PropertyConfig config);
}
