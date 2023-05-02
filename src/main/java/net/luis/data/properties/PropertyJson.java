package net.luis.data.properties;

import net.luis.data.json.JsonObject;
import net.luis.data.json.config.JsonConfig;
import net.luis.data.json.io.JsonSerializable;
import net.luis.data.properties.config.PropertyConfig;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 *
 * @author Luis-St
 *
 */

public final class PropertyJson implements Property, JsonSerializable<JsonObject> {
	
	private final String key;
	private final JsonObject value;
	
	/**
	 * Constructs a new property json with the given key and value
	 * @param key The key of the property json
	 * @param value The value of the property json
	 * @throws NullPointerException If the key or the value is null
	 */
	public PropertyJson(String key, JsonObject value) {
		this.key = Objects.requireNonNull(key, "Property key must not be null");
		this.value = Objects.requireNonNull(value, "Json object must not be null");
	}
	
	@Override
	public @NotNull String getName() {
		return "property json";
	}
	
	@Override
	public @NotNull String getKey() {
		return this.key.toLowerCase();
	}
	
	@Override
	public @NotNull PropertyJson copy() {
		return new PropertyJson(this.key, this.value.copy());
	}
	
	//region Getters
	
	/**
	 * @return The value of the {@link PropertyJson} as a boolean using {@link JsonObject#getAsBoolean()}
	 */
	@Override
	public boolean getAsBoolean() {
		return this.value.getAsBoolean();
	}
	
	/**
	 * @return The value of the {@link PropertyJson} as a number using {@link JsonObject#getAsNumber()}
	 */
	@Override
	public Number getAsNumber() {
		return this.value.getAsNumber();
	}
	
	/**
	 * @return The value of the {@link PropertyJson} as an integer using {@link JsonObject#getAsInt()}
	 */
	@Override
	public int getAsInt() {
		return this.value.getAsInt();
	}
	
	/**
	 * @return The value of the {@link PropertyJson} as a long using {@link JsonObject#getAsLong()}
	 */
	@Override
	public long getAsLong() {
		return this.value.getAsLong();
	}
	
	/**
	 * @return The value of the {@link PropertyJson} as a double using {@link JsonObject#getAsDouble()}
	 */
	@Override
	public double getAsDouble() {
		return this.value.getAsDouble();
	}
	
	/**
	 * @return The value of the {@link PropertyJson} as a string using {@link JsonObject#getAsString()}
	 */
	@Override
	public String getAsString() {
		return this.value.getAsString();
	}
	
	/**
	 * @return The value of the {@link PropertyJson} as a json object
	 */
	@Override
	public JsonObject getAsJson() {
		return this.value;
	}
	//endregion
	
	@Override
	public @NotNull JsonObject toJson() {
		return new JsonObject(this.key, this.value);
	}
	
	@Override
	public @NotNull String toString(PropertyConfig config) {
		return this.value.toString(JsonConfig.builder().prettyPrint(false).simplifyPrimitiveArrays(true).simplifyPrimitiveObjects(true).build());
	}
	
	//region Object overrides
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PropertyJson that)) return false;
		
		if (!this.key.equals(that.key)) return false;
		return this.value.equals(that.value);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.key, this.value);
	}
	
	@Override
	public String toString() {
		return this.key + "=" + this.getAsString();
	}
	//endregion
}
