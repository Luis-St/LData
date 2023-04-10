package net.luis.data.json;

import net.luis.data.json.config.JsonConfig;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class JsonNull implements JsonElement {
	
	public static final JsonNull INSTANCE = new JsonNull();
	
	private JsonNull() {
	
	}
	
	@Override
	public @NotNull JsonElement copy() {
		return this;
	}
	
	@Override
	public @NotNull String toJson(JsonConfig config) {
		return "null";
	}
}
