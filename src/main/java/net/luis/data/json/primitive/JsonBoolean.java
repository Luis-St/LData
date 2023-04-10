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

public class JsonBoolean extends JsonPrimitive {
	
	private final boolean value;
	
	public JsonBoolean(boolean value) {
		this.value = value;
	}
	
	@Override
	public @NotNull JsonElement copy() {
		return new JsonBoolean(this.value);
	}
	
	//region Getters
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
	//endregion
	
	@Override
	public @NotNull String toJson(JsonConfig config) {
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
