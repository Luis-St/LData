package net.luis.data.json.primitive;

import net.luis.data.json.JsonElement;
import net.luis.data.json.config.JsonConfig;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 *
 * @author Luis-St
 *
 */

public class JsonNumber extends JsonPrimitive {
	
	private final Number number;
	
	public JsonNumber(Number number) {
		this.number = Objects.requireNonNull(number);
	}
	
	@Override
	public @NotNull JsonNumber copy() {
		return new JsonNumber(this.number);
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
	//endregion
	
	@Override
	public @NotNull String toJson(JsonConfig config) {
		return this.getAsString();
	}
	
	//region Object overrides
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof JsonNumber that)) return false;
		
		return this.number.equals(that.number);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.number);
	}
	//endregion
}
