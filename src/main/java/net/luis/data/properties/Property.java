package net.luis.data.properties;

import net.luis.data.json.JsonConvertible;
import net.luis.data.json.JsonObject;
import net.luis.data.properties.config.PropertyConfig;
import net.luis.data.properties.exception.PropertyException;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public sealed interface Property extends JsonConvertible<JsonObject> permits CommentedProperty, ObjectProperty, PropertyArray, PropertyJson, PropertyNull, PropertyPrimitive {
	
	@NotNull
	@ApiStatus.Internal
	String getName();
	
	@NotNull String getKey();
	
	@NotNull Property copy();
	
	//region PropertyArray
	default boolean isArray() {
		return this instanceof PropertyArray;
	}
	
	default PropertyArray getAsArray() {
		if (this.isArray()) {
			return (PropertyArray) this;
		}
		throw new PropertyException("Not a property array: " + this.getName());
	}
	//endregion
	
	//region PropertyPrimitive
	default boolean isPrimitive() {
		return this instanceof PropertyPrimitive;
	}
	
	default PropertyPrimitive getAsPrimitive() {
		if (this.isPrimitive()) {
			return (PropertyPrimitive) this;
		}
		throw new PropertyException("Not a property primitive: " + this.getName());
	}
	
	default boolean isBoolean() {
		throw new PropertyException("Not a property primitive: " + this.getName());
	}
	
	default boolean getAsBoolean() {
		throw new PropertyException("Not a property primitive: " + this.getName());
	}
	
	default boolean isNumber() {
		throw new PropertyException("Not a property primitive: " + this.getName());
	}
	
	default Number getAsNumber() {
		throw new PropertyException("Not a property primitive: " + this.getName());
	}
	
	default int getAsInt() {
		throw new PropertyException("Not a property number: " + this.getName());
	}
	
	default long getAsLong() {
		throw new PropertyException("Not a property number: " + this.getName());
	}
	
	default double getAsDouble() {
		throw new PropertyException("Not a property number: " + this.getName());
	}
	
	default boolean isString() {
		throw new PropertyException("Not a property primitive: " + this.getName());
	}
	
	default String getAsString() {
		throw new PropertyException("Not a property primitive: " + this.getName());
	}
	//endregion
	
	//region PropertyJson
	default boolean isJson() {
		return this instanceof PropertyJson;
	}
	
	default JsonObject getAsJson() {
		throw new UnsupportedOperationException("Not a property json: " + this.getName());
	}
	//endregion
	
	//region PropertyNull
	default boolean isNull() {
		return this instanceof PropertyNull;
	}
	//endregion
	
	//region ObjectProperty
	default boolean isObject() {
		return this instanceof ObjectProperty;
	}
	
	default ObjectProperty getAsObject() {
		if (this.isObject()) {
			return (ObjectProperty) this;
		}
		throw new PropertyException("Not a object property: " + this.getName());
	}
	//endregion
	
	//region CommentedProperty
	default boolean isCommented() {
		return this instanceof CommentedProperty;
	}
	
	default CommentedProperty getAsCommented() {
		if (this.isCommented()) {
			return (CommentedProperty) this;
		}
		throw new PropertyException("Not a commented property: " + this.getName());
	}
	//endregion
	
	@NotNull String toString(PropertyConfig config);
}
