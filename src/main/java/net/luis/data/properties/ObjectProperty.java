package net.luis.data.properties;

import net.luis.data.json.JsonObject;
import net.luis.data.properties.config.PropertyConfig;
import net.luis.data.properties.primitive.PropertyPrimitive;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ObjectProperty implements Property {
	
	private final String object;
	private final Property value;
	
	public ObjectProperty(String object, Property value) {
		this.object = Objects.requireNonNull(object, "Object must not be null");
		this.value = Objects.requireNonNull(value, "Property must not be null");
	}
	
	public @NotNull String getObject() {
		return this.object;
	}
	
	@Override
	public @NotNull String getKey() {
		return this.object + "." + this.value.getKey();
	}
	
	@Override
	public @NotNull ObjectProperty copy() {
		return new ObjectProperty(this.object, this.value.copy());
	}
	
	//region Getters
	public Property getInner() {
		return this.value;
	}
	
	public Property getActual() {
		if (this.value instanceof ObjectProperty objectProperty) {
			return objectProperty.getActual();
		}
		return this.value;
	}
	
	@Override
	public boolean isArray() {
		return this.value.isArray();
	}
	
	@Override
	public PropertyArray getAsArray() {
		return this.value.getAsArray();
	}
	
	@Override
	public boolean isPrimitive() {
		return this.value.isPrimitive();
	}
	
	@Override
	public PropertyPrimitive getAsPrimitive() {
		return this.value.getAsPrimitive();
	}
	
	@Override
	public boolean isBoolean() {
		return this.value.isBoolean();
	}
	
	@Override
	public boolean getAsBoolean() {
		return this.value.getAsBoolean();
	}
	
	@Override
	public boolean isNumber() {
		return this.value.isNumber();
	}
	
	@Override
	public Number getAsNumber() {
		return this.value.getAsNumber();
	}
	
	@Override
	public int getAsInt() {
		return this.value.getAsInt();
	}
	
	@Override
	public long getAsLong() {
		return this.value.getAsLong();
	}
	
	@Override
	public double getAsDouble() {
		return this.value.getAsDouble();
	}
	
	@Override
	public boolean isString() {
		return this.value.isString();
	}
	
	@Override
	public String getAsString() {
		return this.value.getAsString();
	}
	
	@Override
	public boolean isJson() {
		return this.value.isJson();
	}
	
	@Override
	public JsonObject getAsJson() {
		return this.value.getAsJson();
	}
	
	@Override
	public boolean isObject() {
		return this.value.isObject();
	}
	
	@Override
	public ObjectProperty getAsObject() {
		return this.value.getAsObject();
	}
	
	@Override
	public boolean isNull() {
		return this.value.isNull();
	}
	
	//endregion
	
	@Override
	public @NotNull String toString(PropertyConfig config) {
		return this.value.toString(config);
	}
	
	//region Object overrides
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ObjectProperty that)) return false;
		
		if (!this.object.equals(that.object)) return false;
		return this.value.equals(that.value);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.object, this.value);
	}
	
	@Override
	public String toString() {
		return this.object + "." + this.value.toString();
	}
	//endregion
}
