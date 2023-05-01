package net.luis.data.properties;

import net.luis.data.json.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Property that represents a string value
 *
 * @author Luis-St
 */

public final class PropertyString extends PropertyPrimitive {
	
	private final String value;
	
	/**
	 * Constructs a new property string with the given key and value
	 * @param key The key of the property string
	 * @param value The value of the property string
	 * @throws NullPointerException If the key or value is null
	 */
	public PropertyString(String key, String value) {
		super(key);
		this.value = Objects.requireNonNull(value, "String value must not be null");
	}
	
	@Override
	public @NotNull String getName() {
		return "property string";
	}
	
	@Override
	public @NotNull PropertyString copy() {
		return new PropertyString(this.getKey(), this.value);
	}
	
	//region Getters
	
	/**
	 * @return The value of the {@link PropertyString property string} as a boolean using {@link Boolean#parseBoolean(String)}
	 */
	@Override
	public boolean getAsBoolean() {
		return Boolean.parseBoolean(this.value);
	}
	
	/**
	 * @return The value of the {@link PropertyString property string} as a number using {@link Double#parseDouble(String)}
	 */
	@Override
	public Number getAsNumber() {
		return this.getAsDouble();
	}
	
	/**
	 * @return The value of the {@link PropertyString property string} as a int using {@link Integer#parseInt(String)}
	 */
	@Override
	public int getAsInt() {
		return Integer.parseInt(this.value);
	}
	
	/**
	 * @return The value of the {@link PropertyString property string} as a long using {@link Long#parseLong(String)}
	 */
	@Override
	public long getAsLong() {
		return Long.parseLong(this.value);
	}
	
	/**
	 * @return The value of the {@link PropertyString property string} as a double using {@link Double#parseDouble(String)}
	 */
	@Override
	public double getAsDouble() {
		return Double.parseDouble(this.value);
	}
	
	/**
	 * @return The value of the {@link PropertyString property string}
	 */
	@Override
	public String getAsString() {
		return this.value;
	}
	
	@Override
	public JsonObject getAsJson() {
		JsonObject object = new JsonObject();
		object.add(this.getKey(), this.value);
		return object;
	}
	//endregion
	
	//region Object overrides
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PropertyString that)) return false;
		
		return this.value.equals(that.value);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.value);
	}
	//endregion
}
