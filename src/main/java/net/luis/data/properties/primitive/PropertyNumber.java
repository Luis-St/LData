package net.luis.data.properties.primitive;

import net.luis.data.json.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PropertyNumber extends PropertyPrimitive {
	
	private final Number number;
	
	public PropertyNumber(String key, Number number) {
		super(key);
		this.number = Objects.requireNonNull(number, "Number must not be null");
	}
	
	@Override
	public @NotNull PropertyNumber copy() {
		return new PropertyNumber(this.getKey(), this.number);
	}
	
	//region Getters
	@Override
	public Number getAsNumber() {
		return this.number;
	}
	
	@Override
	public int getAsInt() {
		return this.number.intValue();
	}
	
	@Override
	public long getAsLong() {
		return this.number.longValue();
	}
	
	@Override
	public double getAsDouble() {
		return this.number.doubleValue();
	}
	
	@Override
	public String getAsString() {
		String value = Double.toString(this.getAsDouble());
		return value.endsWith(".0") ? value.substring(0, value.length() - 2) : value;
	}
	
	@Override
	public JsonObject getAsJson() {
		JsonObject object = new JsonObject();
		object.add(this.getKey(), this.number);
		return object;
	}
	//endregion
	
	//region Object overrides
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PropertyNumber that)) return false;
		
		return this.number.equals(that.number);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.number);
	}
	//endregion
}
