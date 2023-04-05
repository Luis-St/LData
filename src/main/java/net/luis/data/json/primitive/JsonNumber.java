package net.luis.data.json.primitive;

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
	public int getAsInt() {
		return this.number.intValue();
	}
	
	@Override
	public long getAsLong() {
		return this.number.longValue();
	}
	
	@Override
	public float getAsFloat() {
		return this.number.floatValue();
	}
	
	@Override
	public double getAsDouble() {
		return this.number.doubleValue();
	}
	
	@Override
	public String getAsString() {
		return this.number.toString();
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
