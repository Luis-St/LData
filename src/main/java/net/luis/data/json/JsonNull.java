package net.luis.data.json;

import net.luis.data.json.config.JsonConfig;
import org.jetbrains.annotations.NotNull;

/**
 * Json element that represents a null value
 *
 * @author Luis-St
 */

public final class JsonNull implements Json {
	
	/**
	 * The only instance of {@code JsonNull json null}
	 */
	public static final JsonNull INSTANCE = new JsonNull();
	
	private JsonNull() {
		super();
	}
	
	@Override
	public @NotNull String getName() {
		return "json null";
	}
	
	@Override
	public @NotNull JsonNull copy() {
		return this;
	}
	
	@Override
	public @NotNull String toString(JsonConfig config) {
		return "null";
	}
	
	//region Object overrides
	@Override
	public String toString() {
		return "null";
	}
	//endregion
}
