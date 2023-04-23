package net.luis.data.json;

import net.luis.data.json.config.JsonConfig;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 *
 * @author Luis-St
 *
 */

public final class JsonNumber extends JsonPrimitive {
	
	private final Number value;
	
	public JsonNumber(Number value) {
		this.value = Objects.requireNonNull(value);
	}
	
	@Override
	public @NotNull String getName() {
		return "json number";
	}
	
	@Override
	public @NotNull JsonNumber copy() {
		return new JsonNumber(this.value);
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
	//endregion
	
	@Override
	public @NotNull String toString(JsonConfig config) {
		return this.getAsString();
	}
	
	//region Object overrides
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof JsonNumber that)) return false;
		
		return this.value.equals(that.value);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.value);
	}
	//endregion
}
