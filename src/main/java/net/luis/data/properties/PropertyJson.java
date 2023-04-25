package net.luis.data.properties;

import net.luis.data.json.io.JsonSerializable;
import net.luis.data.json.JsonObject;
import net.luis.data.json.config.JsonConfig;
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
		return this.key;
	}
	
	@Override
	public @NotNull PropertyJson copy() {
		return new PropertyJson(this.key, this.value.copy());
	}
	
	//region Getters
	@Override
	public boolean getAsBoolean() {
		return this.value.getAsBoolean();
	}
	
	@Override
	public Number getAsNumber() {
		return this.value.getAsNumber();
	}
	
	@Override
	public int getAsInt() {
		return this.value.getAsInt();
	}
	
	@Override
	public long getAsLong() {
		return this.value.getAsLong();
	}
	
	@Override
	public double getAsDouble() {
		return this.value.getAsDouble();
	}
	
	@Override
	public String getAsString() {
		return this.value.getAsString();
	}
	
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
