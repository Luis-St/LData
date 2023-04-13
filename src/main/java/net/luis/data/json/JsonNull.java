package net.luis.data.json;

import net.luis.data.json.config.JsonConfig;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class JsonNull implements Json {
	
	public static final JsonNull INSTANCE = new JsonNull();
	
	private JsonNull() {
	
	}
	
	@Override
	public @NotNull JsonNull copy() {
		return this;
	}
	
	@Override
	public @NotNull String toString(JsonConfig config) {
		return "null";
	}
}
