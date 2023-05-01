package net.luis.data.properties;

import net.luis.data.json.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Property that represents a boolean value
 *
 * @author Luis-St
 */

public final class PropertyBoolean extends PropertyPrimitive {
	
	private final boolean value;
	
	/**
	 * Constructs a new property boolean with the given key and value
	 * @param key The key of the property boolean
	 * @param value The value of the property boolean
	 * @throws NullPointerException If the key is null
	 */
	public PropertyBoolean(String key, boolean value) {
		super(key);
		this.value = value;
	}
	
	@Override
	public @NotNull String getName() {
		return "property boolean";
	}
	
	@Override
	public @NotNull PropertyBoolean copy() {
		return new PropertyBoolean(this.getKey(), this.value);
	}
	
	//region Getters
	
	/**
	 * @return The value of the {@link PropertyBoolean property boolean}
	 */
	@Override
	public boolean getAsBoolean() {
		return this.value;
	}
	
	/**
	 * @return The value of the {@link PropertyBoolean property boolean} as an integer, 1 if true otherwise 0
	 */
	@Override
	public int getAsInt() {
		return this.value ? 1 : 0;
	}
	
	/**
	 * @return The value of the {@link PropertyBoolean property boolean} as a long, 1 if true otherwise 0
	 */
	@Override
	public long getAsLong() {
		return this.value ? 1L : 0L;
	}
	
	/**
	 * @return The value of the {@link PropertyBoolean property boolean} as a string, "true" if true otherwise "false"
	 */
	@Override
	public @NotNull String getAsString() {
		return Boolean.toString(this.value);
	}
	
	/**
	 * @return The value of the {@link PropertyBoolean property boolean} as {@link JsonObject json object}
	 */
	@Override
	public @NotNull JsonObject getAsJson() {
		JsonObject object = new JsonObject();
		object.add(this.getKey(), this.value);
		return object;
	}
	//endregion
	
	//region Object overrides
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PropertyBoolean that)) return false;
		
		return this.value == that.value;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.value);
	}
	//endregion
}
