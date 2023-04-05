package net.luis.data.json.primitive;

import net.luis.data.json.JsonElement;
import net.luis.data.json.exception.JsonException;

/**
 *
 * @author Luis-St
 *
 */

public abstract class JsonPrimitive implements JsonElement {
	
	@Override
	public boolean isBoolean() {
		return this instanceof JsonBoolean;
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
		return this instanceof JsonNumber;
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
	public float getAsFloat() {
		if (this.isNumber()) {
			throw new UnsupportedOperationException("Not implemented");
		}
		throw new JsonException("Not a float: " + this);
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
		return this instanceof JsonString;
	}
	
	@Override
	public String getAsString() {
		if (this.isString()) {
			throw new UnsupportedOperationException("Not implemented");
		}
		throw new IllegalStateException("Not a string: " + this);
	}
	
	//region Object overrides
	@Override
	public String toString() {
		return this.getAsString();
	}
	//endregion
}
