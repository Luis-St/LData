package net.luis.data.json.primitive;

import net.luis.data.json.JsonElement;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 *
 * @author Luis-St
 *
 */

public class JsonString extends JsonPrimitive {
	
	private final String value;
	
	public JsonString(String value) {
		this.value = StringUtils.defaultString(value);
	}
	
	@Override
	public JsonElement copy() {
		return new JsonString(this.value);
	}
	
	//region Getters
	@Override
	public boolean getAsBoolean() {
		return Boolean.parseBoolean(this.value);
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
	public float getAsFloat() {
		return Float.parseFloat(this.value);
	}
	
	@Override
	public double getAsDouble() {
		return Double.parseDouble(this.value);
	}
	
	@Override
	public String getAsString() {
		return this.value;
	}
	//endregion
	
	@Override
	public String toJsonString() {
		if (this.value.charAt(0) == '"' && this.value.charAt(this.value.length() - 1) == '"') {
			return "\"" + "\\\"" + this.value.substring(1, this.value.length() - 1) + "\\\"" + "\"";
		}
		return "\"" + this.value + "\"";
	}
	
	//region Object overrides
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof JsonString that)) return false;
		
		return this.value.equals(that.value);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.value);
	}
	//endregion
}
