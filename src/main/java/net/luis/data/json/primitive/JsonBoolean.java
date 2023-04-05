package net.luis.data.json.primitive;

import java.util.Objects;

/**
 *
 * @author Luis-St
 *
 */

public class JsonBoolean extends JsonPrimitive {
	
	private final boolean value;
	
	public JsonBoolean(boolean value) {
		this.value = value;
	}
	
	@Override
	public boolean getAsBoolean() {
		return this.value;
	}
	
	@Override
	public int getAsInt() {
		return this.value ? 1 : 0;
	}
	
	@Override
	public long getAsLong() {
		return this.value ? 1L : 0L;
	}
	
	@Override
	public String getAsString() {
		return Boolean.toString(this.value);
	}
	
	//region Object overrides
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof JsonBoolean that)) return false;
		
		return this.value == that.value;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.value);
	}
	//endregion
}
