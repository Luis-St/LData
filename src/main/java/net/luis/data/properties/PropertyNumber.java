package net.luis.data.properties;

import net.luis.data.json.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 *
 * @author Luis-St
 *
 */

public final class PropertyNumber extends PropertyPrimitive {
	
	private final Number value;
	
	public PropertyNumber(String key, Number value) {
		super(key);
		this.value = Objects.requireNonNull(value, "Number must not be null");
	}
	
	@Override
	public @NotNull String getName() {
		return "property number";
	}
	
	@Override
	public @NotNull PropertyNumber copy() {
		return new PropertyNumber(this.getKey(), this.value);
	}
	
	//region Getters
	@Override
	public Number getAsNumber() {
		return this.value;
	}
	
	@Override
	public int getAsInt() {
		return this.value.intValue();
	}
	
	@Override
	public long getAsLong() {
		return this.value.longValue();
	}
	
	@Override
	public double getAsDouble() {
		return this.value.doubleValue();
	}
	
	@Override
	public String getAsString() {
		String value = Double.toString(this.getAsDouble());
		return value.endsWith(".0") ? value.substring(0, value.length() - 2) : value;
	}
	
	@Override
	public JsonObject getAsJson() {
		JsonObject object = new JsonObject();
		object.add(this.getKey(), this.value);
		return object;
	}
	//endregion
	
	@Override
	public @NotNull JsonObject toJson() {
		return new JsonObject(this.getKey(), this.value);
	}
	
	//region Object overrides
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PropertyNumber that)) return false;
		
		return this.value.equals(that.value);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.value);
	}
	//endregion
}
