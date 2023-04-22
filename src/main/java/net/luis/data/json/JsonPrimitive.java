package net.luis.data.json;

import net.luis.data.json.exception.JsonException;

/**
 *
 * @author Luis-St
 *
 */

public abstract sealed class JsonPrimitive implements Json permits JsonBoolean, JsonNumber, JsonString {
	
	//region JsonElement overrides
	@Override
	public boolean isBoolean() {
		return this instanceof JsonBoolean;
	}
	
	@Override
	public boolean getAsBoolean() {
		if (this.isBoolean()) {
			throw new UnsupportedOperationException("Not implemented");
		}
		throw new IllegalStateException("Not a json boolean: " + this.getName());
	}
	
	@Override
	public boolean isNumber() {
		return this instanceof JsonNumber;
	}
	
	@Override
	public Number getAsNumber() {
		if (this.isNumber()) {
			throw new UnsupportedOperationException("Not implemented");
		}
		throw new JsonException("Not a json number: " + this.getName());
	}
	
	@Override
	public int getAsInt() {
		if (this.isNumber()) {
			throw new UnsupportedOperationException("Not implemented");
		}
		throw new JsonException("Not an json number (int): " + this.getName());
	}
	
	@Override
	public long getAsLong() {
		if (this.isNumber()) {
			throw new UnsupportedOperationException("Not implemented");
		}
		throw new JsonException("Not a json number (long): " + this.getName());
	}
	
	@Override
	public double getAsDouble() {
		if (this.isNumber()) {
			throw new UnsupportedOperationException("Not implemented");
		}
		throw new JsonException("Not a json number (double): " + this.getName());
	}
	
	@Override
	public boolean isString() {
		return this instanceof JsonString;
	}
	
	@Override
	public String getAsString() {
		if (this.isString()) {
			throw new UnsupportedOperationException("Not implemented");
		}
		throw new IllegalStateException("Not a json string: " + this.getName());
	}
	//endregion
	
	//region Object overrides
	@Override
	public String toString() {
		return this.getAsString();
	}
	//endregion
}
