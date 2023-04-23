package net.luis.data.properties;

import net.luis.data.json.JsonObject;
import net.luis.data.properties.config.PropertyConfig;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 *
 * @author Luis-St
 *
 */

public final record ObjectProperty(String getObject, Property getActual) implements Property {
	
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
		return this.getObject + "." + this.getActual.getKey();
	}
	
	@Override
	public @NotNull ObjectProperty copy() {
		return new ObjectProperty(this.getObject, this.getActual.copy());
	}
	
	public Property getInner() {
		return this.getActual;
	}
	
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
	
	@Override
	public Property getActual() {
		if (this.getActual.isObject()) {
			return this.getActual.getAsObject().getActual();
		}
		return this.getActual;
	}
	
	//region Property delegation
	@Override
	public boolean isArray() {
		return this.getActual.isArray();
	}
	
	@Override
	public PropertyArray getAsArray() {
		return this.getActual.getAsArray();
	}
	
	@Override
	public boolean isPrimitive() {
		return this.getActual.isPrimitive();
	}
	
	@Override
	public PropertyPrimitive getAsPrimitive() {
		return this.getActual.getAsPrimitive();
	}
	
	@Override
	public boolean isBoolean() {
		return this.getActual.isBoolean();
	}
	
	@Override
	public boolean getAsBoolean() {
		return this.getActual.getAsBoolean();
	}
	
	@Override
	public boolean isNumber() {
		return this.getActual.isNumber();
	}
	
	@Override
	public Number getAsNumber() {
		return this.getActual.getAsNumber();
	}
	
	@Override
	public int getAsInt() {
		return this.getActual.getAsInt();
	}
	
	@Override
	public long getAsLong() {
		return this.getActual.getAsLong();
	}
	
	@Override
	public double getAsDouble() {
		return this.getActual.getAsDouble();
	}
	
	@Override
	public boolean isString() {
		return this.getActual.isString();
	}
	
	@Override
	public String getAsString() {
		return this.getActual.getAsString();
	}
	
	@Override
	public boolean isJson() {
		return this.getActual.isJson();
	}
	
	@Override
	public JsonObject getAsJson() {
		return this.getActual.getAsJson();
	}
	
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
