package net.luis.data.properties;

import net.luis.data.json.JsonObject;
import net.luis.data.properties.config.PropertyConfig;
import net.luis.data.properties.primitive.PropertyPrimitive;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ObjectProperty implements Property {
	
	private final String object;
	private final Property actual;
	
	public ObjectProperty(String object, Property actual) {
		if (actual instanceof CommentedProperty property) {
			throw new IllegalArgumentException("Cannot nest a commented property inside an object property");
		}
		this.object = Objects.requireNonNull(object, "Object must not be null");
		this.actual = Objects.requireNonNull(actual, "Property must not be null");
	}
	
	public @NotNull String getObject() {
		return this.object;
	}
	
	@Override
	public @NotNull String getKey() {
		return this.object + "." + this.actual.getKey();
	}
	
	@Override
	public @NotNull ObjectProperty copy() {
		return new ObjectProperty(this.object, this.actual.copy());
	}
	
	public Property getInner() {
		return this.actual;
	}
	
	public Property getInnerFrom(String object) {
		Objects.requireNonNull(object, "Object must not be null");
		if (object.equals(this.object)) {
			return this.actual;
		} else if (!object.contains(".")) {
			if (this.actual.isObject()) {
				return this.actual.getAsObject().getInnerFrom(object);
			}
			throw new IllegalArgumentException("Object " + object + " does not exist in " + this.object);
		}
		String nextObject = object.startsWith(this.object + ".") ? object.substring(this.object.length() + 1) : object;
		if (this.actual.isObject()) {
			return this.actual.getAsObject().getInnerFrom(nextObject);
		}
		throw new IllegalArgumentException("Object " + object + " does not exist in " + this.object);
	}
	
	public Property getActual() {
		if (this.actual.isObject()) {
			return this.actual.getAsObject().getActual();
		}
		return this.actual;
	}
	
	//region Property delegation
	@Override
	public boolean isArray() {
		return this.actual.isArray();
	}
	
	@Override
	public PropertyArray getAsArray() {
		return this.actual.getAsArray();
	}
	
	@Override
	public boolean isPrimitive() {
		return this.actual.isPrimitive();
	}
	
	@Override
	public PropertyPrimitive getAsPrimitive() {
		return this.actual.getAsPrimitive();
	}
	
	@Override
	public boolean isBoolean() {
		return this.actual.isBoolean();
	}
	
	@Override
	public boolean getAsBoolean() {
		return this.actual.getAsBoolean();
	}
	
	@Override
	public boolean isNumber() {
		return this.actual.isNumber();
	}
	
	@Override
	public Number getAsNumber() {
		return this.actual.getAsNumber();
	}
	
	@Override
	public int getAsInt() {
		return this.actual.getAsInt();
	}
	
	@Override
	public long getAsLong() {
		return this.actual.getAsLong();
	}
	
	@Override
	public double getAsDouble() {
		return this.actual.getAsDouble();
	}
	
	@Override
	public boolean isString() {
		return this.actual.isString();
	}
	
	@Override
	public String getAsString() {
		return this.actual.getAsString();
	}
	
	@Override
	public boolean isJson() {
		return this.actual.isJson();
	}
	
	@Override
	public JsonObject getAsJson() {
		return this.actual.getAsJson();
	}
	
	@Override
	public boolean isNull() {
		return this.actual.isNull();
	}
	//endregion
	
	@Override
	public @NotNull String toString(PropertyConfig config) {
		return this.actual.toString(config);
	}
	
	//region Object overrides
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ObjectProperty that)) return false;
		
		if (!this.object.equals(that.object)) return false;
		return this.actual.equals(that.actual);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.object, this.actual);
	}
	
	@Override
	public String toString() {
		return this.object + "." + this.actual.toString();
	}
	//endregion
}
