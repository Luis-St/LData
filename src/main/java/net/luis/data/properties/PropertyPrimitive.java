package net.luis.data.properties;

import net.luis.data.json.exception.JsonException;
import net.luis.data.properties.config.PropertyConfig;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Property that represents a primitive value
 *
 * @author Luis-St
 */

public abstract sealed class PropertyPrimitive implements Property permits PropertyBoolean, PropertyNumber, PropertyString {
	
	private final String key;
	
	/**
	 * Constructs a new property primitive with the given key
	 * @param key The key of the property primitive
	 * @throws NullPointerException If the key is null
	 */
	public PropertyPrimitive(String key) {
		this.key = Objects.requireNonNull(key, "Property key must not be null");
	}
	
	@Override
	public final @NotNull String getKey() {
		return this.key.toLowerCase();
	}
	
	//region Property overrides
	@Override
	public boolean isBoolean() {
		return this instanceof PropertyBoolean;
	}
	
	@Override
	public boolean getAsBoolean() {
		if (this.isBoolean()) {
			throw new UnsupportedOperationException("Not implemented");
		}
		throw new IllegalStateException("Not a boolean: " + this);
	}
	
	@Override
	public boolean isNumber() {
		return this instanceof PropertyNumber;
	}
	
	@Override
	public Number getAsNumber() {
		return Property.super.getAsNumber();
	}
	
	@Override
	public int getAsInt() {
		if (this.isNumber()) {
			throw new UnsupportedOperationException("Not implemented");
		}
		throw new JsonException("Not an int: " + this);
	}
	
	@Override
	public long getAsLong() {
		if (this.isNumber()) {
			throw new UnsupportedOperationException("Not implemented");
		}
		throw new JsonException("Not a long: " + this);
	}
	
	@Override
	public double getAsDouble() {
		if (this.isNumber()) {
			throw new UnsupportedOperationException("Not implemented");
		}
		throw new JsonException("Not a double: " + this);
	}
	
	@Override
	public boolean isString() {
		return this instanceof PropertyString;
	}
	
	@Override
	public String getAsString() {
		if (this.isString()) {
			throw new UnsupportedOperationException("Not implemented");
		}
		throw new IllegalStateException("Not a string: " + this);
	}
	//endregion
	
	@Override
	public @NotNull String toString(PropertyConfig config) {
		return this.getAsString();
	}
	
	//region Object overrides
	@Override
	public String toString() {
		return this.key + "=" + this.getAsString();
	}
	//endregion
}
