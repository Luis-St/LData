package net.luis.data.properties;

import net.luis.data.json.JsonNull;
import net.luis.data.json.JsonObject;
import net.luis.data.properties.config.PropertyConfig;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

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
	public @NotNull JsonObject toJson() {
		return new JsonObject(this.getKey(), JsonNull.INSTANCE);
	}
	
	@Override
	public @NotNull String toString(PropertyConfig config) {
		return "null";
	}
}
