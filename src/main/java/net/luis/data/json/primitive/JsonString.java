package net.luis.data.json.primitive;

import net.luis.data.json.JsonElement;
import net.luis.data.json.JsonHelper;
import net.luis.data.json.config.JsonConfig;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

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
	public @NotNull JsonString copy() {
		return new JsonString(this.value);
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
	//endregion
	
	@Override
	public @NotNull String toJson(JsonConfig config) {
		return JsonHelper.quote(this.value, config);
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
