package net.luis.data.properties;

import net.luis.data.properties.config.PropertyConfig;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Property that represents a null value
 *
 * @author Luis-St
 */
public record PropertyNull(String getKey) implements Property {
	
	/**
	 * Constructs a new {@link PropertyNull}
	 * @param getKey The key of the property
	 * @throws NullPointerException If the key is null
	 */
	public PropertyNull {
		Objects.requireNonNull(getKey, "Key must not be null");
	}
	
	@Override
	public @NotNull String getName() {
		return "property null";
	}
	
	@Override
	public @NotNull String getKey() {
		return this.getKey.toLowerCase();
	}
	
	@Override
	public @NotNull PropertyNull copy() {
		return new PropertyNull(this.getKey());
	}
	
	@Override
	public @NotNull String toString(PropertyConfig config) {
		return "null";
	}
}
