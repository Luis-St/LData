package net.luis.data.properties;

import net.luis.data.json.JsonObject;
import net.luis.data.properties.config.PropertyConfig;
import net.luis.data.properties.exception.PropertyException;
import net.luis.data.properties.primitive.PropertyPrimitive;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public interface Property {
	
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
		throw new PropertyException("Not a property array: " + this);
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
		throw new PropertyException("Not a property primitive: " + this);
	}
	
	default boolean isBoolean() {
		throw new PropertyException("Not a property primitive: " + this);
	}
	
	default boolean getAsBoolean() {
		throw new PropertyException("Not a property primitive: " + this);
	}
	
	default boolean isNumber() {
		throw new PropertyException("Not a property primitive: " + this);
	}
	
	default Number getAsNumber() {
		throw new PropertyException("Not a property primitive: " + this);
	}
	
	default int getAsInt() {
		throw new PropertyException("Not a property number: " + this);
	}
	
	default long getAsLong() {
		throw new PropertyException("Not a property number: " + this);
	}
	
	default double getAsDouble() {
		throw new PropertyException("Not a property number: " + this);
	}
	
	default boolean isString() {
		throw new PropertyException("Not a property primitive: " + this);
	}
	
	default String getAsString() {
		throw new PropertyException("Not a property primitive: " + this);
	}
	//endregion
	
	//region PropertyJson
	default boolean isJson() {
		return this instanceof PropertyJson;
	}
	
	default JsonObject getAsJson() {
		throw new UnsupportedOperationException("Not a property json: " + this);
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
		throw new PropertyException("Not a object property: " + this);
	}
	//endregion
	
	@NotNull String toString(PropertyConfig config);
}
