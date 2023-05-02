package net.luis.data.properties;

import net.luis.data.json.JsonObject;
import net.luis.data.properties.config.PropertyConfig;
import net.luis.data.properties.exception.PropertyException;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Property that represents an property of an object
 *
 * @author Luis-St
 */

public record ObjectProperty(String getObject, Property getActual) implements Property {
	
	/**
	 * Constructs a new {@link ObjectProperty object property}
	 * @param getObject The object of the property
	 * @param getActual The actual property
	 */
	public ObjectProperty {
		if (getActual instanceof CommentedProperty property) {
			throw new IllegalArgumentException("Cannot nest a commented property inside an object property");
		}
		Objects.requireNonNull(getObject, "Object must not be null");
		Objects.requireNonNull(getActual, "Actual property must not be null");
		if (getObject.contains(".")) {
			throw new IllegalArgumentException("Object must not contain a dot");
		}
	}
	
	@Override
	public @NotNull String getName() {
		return "object property";
	}
	
	@Override
	public @NotNull String getKey() {
		return (this.getObject + "." + this.getActual.getKey()).toLowerCase();
	}
	
	@Override
	public @NotNull ObjectProperty copy() {
		return new ObjectProperty(this.getObject, this.getActual.copy());
	}
	
	/**
	 * @return The inner property of this object property
	 */
	public Property getInner() {
		return this.getActual;
	}
	
	/**
	 * Gets the inner property from the given object
	 * @param object The object to get the inner property from
	 * @return The inner property from the given object
	 */
	public Property getInnerFrom(String object) {
		Objects.requireNonNull(object, "Object must not be null");
		if (object.equals(this.getObject)) {
			return this.getActual;
		} else if (!object.contains(".")) {
			if (this.getActual.isObject()) {
				return this.getActual.getAsObject().getInnerFrom(object);
			}
			throw new IllegalArgumentException("Object " + object + " does not exist in " + this.getObject);
		}
		String nextObject = object.startsWith(this.getObject + ".") ? object.substring(this.getObject.length() + 1) : object;
		if (this.getActual.isObject()) {
			return this.getActual.getAsObject().getInnerFrom(nextObject);
		}
		throw new IllegalArgumentException("Object " + object + " does not exist in " + this.getObject);
	}
	
	public @NotNull String getPath() {
		String key = this.getKey();
		return key.substring(0, key.lastIndexOf('.')).toLowerCase();
	}
	
	/**
	 * @return The actual property inside the last nested {@link ObjectProperty object property}
	 */
	@Override
	public Property getActual() {
		if (this.getActual.isObject()) {
			return this.getActual.getAsObject().getActual();
		}
		return this.getActual;
	}
	
	//region Property delegation
	
	/**
	 * @return True if the actual property is a {@link PropertyArray}
	 */
	@Override
	public boolean isArray() {
		return this.getActual.isArray();
	}
	
	/**
	 * @return The actual property as an {@link PropertyArray}
	 * @throws PropertyException If the actual property is not a {@link PropertyArray}
	 */
	@Override
	public PropertyArray getAsArray() {
		return this.getActual.getAsArray();
	}
	
	/**
	 * @return True if the actual property is a {@link PropertyPrimitive}
	 */
	@Override
	public boolean isPrimitive() {
		return this.getActual.isPrimitive();
	}
	
	/**
	 * @return The actual property as an {@link PropertyPrimitive}
	 * @throws PropertyException If the actual property is not a {@link PropertyPrimitive}
	 */
	@Override
	public PropertyPrimitive getAsPrimitive() {
		return this.getActual.getAsPrimitive();
	}
	
	/**
	 * @return True if the actual property is an {@link PropertyBoolean}
	 */
	@Override
	public boolean isBoolean() {
		return this.getActual.isBoolean();
	}
	
	/**
	 * @return The actual property as a {@link Boolean}
	 * @throws PropertyException If the actual property is not a {@link PropertyBoolean}
	 */
	@Override
	public boolean getAsBoolean() {
		return this.getActual.getAsBoolean();
	}
	
	/**
	 * @return True if the actual property is a {@link PropertyNumber}
	 */
	@Override
	public boolean isNumber() {
		return this.getActual.isNumber();
	}
	
	/**
	 * @return The actual property as a {@link Number}
	 * @throws PropertyException If the actual property is not a {@link PropertyNumber}
	 */
	@Override
	public Number getAsNumber() {
		return this.getActual.getAsNumber();
	}
	
	/**
	 * @return The actual property as an {@link Integer}
	 * @throws PropertyException If the actual property is not a {@link PropertyNumber}
	 */
	@Override
	public int getAsInt() {
		return this.getActual.getAsInt();
	}
	
	/**
	 * @return The actual property as a {@link Long}
	 * @throws PropertyException If the actual property is not a {@link PropertyNumber}
	 */
	@Override
	public long getAsLong() {
		return this.getActual.getAsLong();
	}
	
	/**
	 * @return The actual property as a {@link Double}
	 * @throws PropertyException If the actual property is not a {@link PropertyNumber}
	 */
	@Override
	public double getAsDouble() {
		return this.getActual.getAsDouble();
	}
	
	/**
	 * @return True if the actual property is a {@link PropertyString}
	 */
	@Override
	public boolean isString() {
		return this.getActual.isString();
	}
	
	/**
	 * @return The actual property as a {@link String}
	 * @throws PropertyException If the actual property is not a {@link PropertyString}
	 */
	@Override
	public String getAsString() {
		return this.getActual.getAsString();
	}
	
	/**
	 * @return True if the actual property is a {@link PropertyJson}
	 */
	@Override
	public boolean isJson() {
		return this.getActual.isJson();
	}
	
	/**
	 * @return The actual property as a {@link JsonObject}
	 * @throws PropertyException If the actual property is not a {@link PropertyJson}
	 */
	@Override
	public JsonObject getAsJson() {
		return this.getActual.getAsJson();
	}
	
	/**
	 * @return True if the actual property is a {@link PropertyNull}
	 */
	@Override
	public boolean isNull() {
		return this.getActual.isNull();
	}
	//endregion
	
	@Override
	public @NotNull String toString(PropertyConfig config) {
		return this.getActual.toString(config);
	}
	
	//region Object overrides
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ObjectProperty that)) return false;
		
		if (!this.getObject.equals(that.getObject)) return false;
		return this.getActual.equals(that.getActual);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.getObject, this.getActual);
	}
	
	@Override
	public String toString() {
		return this.getObject + "." + this.getActual.toString();
	}
	//endregion
}
