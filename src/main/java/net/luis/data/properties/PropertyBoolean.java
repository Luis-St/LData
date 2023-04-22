package net.luis.data.properties;

import net.luis.data.json.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class PropertyBoolean extends PropertyPrimitive {
	
	private final boolean value;
	
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
		if (!(o instanceof PropertyBoolean that)) return false;
		
		return this.value == that.value;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.value);
	}
	//endregion
}
