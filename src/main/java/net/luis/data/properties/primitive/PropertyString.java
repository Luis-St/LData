package net.luis.data.properties.primitive;

import net.luis.data.json.JsonElement;
import net.luis.data.json.JsonObject;
import net.luis.data.properties.Property;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PropertyString extends PropertyPrimitive {
	
	private final String value;
	
	public PropertyString(String key, String value) {
		super(key);
		this.value = Objects.requireNonNull(value, "String must not be null");
	}
	
	@Override
	public @NotNull PropertyString copy() {
		return new PropertyString(this.getKey(), this.value);
	}
	
	//region Getters
	@Override
	public boolean getAsBoolean() {
		return Boolean.parseBoolean(this.value);
	}
	
	@Override
	public Number getAsNumber() {
		return this.getAsDouble();
	}
	
	@Override
	public int getAsInt() {
		return Integer.parseInt(this.value);
	}
	
	@Override
	public long getAsLong() {
		return Long.parseLong(this.value);
	}
	
	@Override
	public double getAsDouble() {
		return Double.parseDouble(this.value);
	}
	
	@Override
	public String getAsString() {
		return this.value;
	}
	
	@Override
	public JsonObject getAsJson() {
		JsonObject object = new JsonObject();
		object.add(this.getKey(), this.value);
		return object;
	}
	//endregion
	
	//region Object overrides
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PropertyString that)) return false;
		
		return this.value.equals(that.value);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.value);
	}
	//endregion
}
