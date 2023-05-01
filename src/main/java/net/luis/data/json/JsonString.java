package net.luis.data.json;

import net.luis.data.json.config.JsonConfig;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Json element that represents a string
 *
 * @author Luis-St
 */

public final class JsonString extends JsonPrimitive {
	
	private final String value;
	
	/**
	 * Constructs a new {@link JsonString json string} with the given value
	 * @param value The value of the json string
	 *              If the value is null, it will be converted to an empty string
	 */
	public JsonString(String value) {
		this.value = StringUtils.defaultString(value);
	}
	
	@Override
	public @NotNull String getName() {
		return "json string";
	}
	
	@Override
	public @NotNull JsonString copy() {
		return new JsonString(this.value);
	}
	
	//region Getters
	/**
	 * @return The value of the {@link JsonString json string} as a boolean using {@link Boolean#parseBoolean(String)}
	 */
	@Override
	public boolean getAsBoolean() {
		return Boolean.parseBoolean(this.value);
	}
	
	/**
	 * @return The value of the {@link JsonString json string} as a number using {@link Double#parseDouble(String)}
	 */
	@Override
	public Number getAsNumber() {
		return this.getAsDouble();
	}
	
	/**
	 * @return The value of the {@link JsonString json string} as a int using {@link Integer#parseInt(String)}
	 */
	@Override
	public int getAsInt() {
		return Integer.parseInt(this.value);
	}
	
	/**
	 * @return The value of the {@link JsonString json string} as a long using {@link Long#parseLong(String)}
	 */
	@Override
	public long getAsLong() {
		return Long.parseLong(this.value);
	}
	
	/**
	 * @return The value of the {@link JsonString json string} as a double using {@link Double#parseDouble(String)}
	 */
	@Override
	public double getAsDouble() {
		return Double.parseDouble(this.value);
	}
	
	/**
	 * @return The value of the {@link JsonString json string}
	 */
	@Override
	public String getAsString() {
		return this.value;
	}
	//endregion
	
	@Override
	public @NotNull String toString(JsonConfig config) {
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
