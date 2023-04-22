package net.luis.data.properties;

import net.luis.data.properties.config.PropertyConfig;
import org.jetbrains.annotations.NotNull;

public record PropertyNull(String getKey) implements Property {
	
	@Override
	public @NotNull String getName() {
		return "property null";
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
