package net.luis.data.json;

import com.google.common.collect.Lists;
import net.luis.data.json.config.JsonConfig;
import net.luis.data.json.exception.JsonException;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Helper class for {@link Json}
 *
 * @author Luis-St
 */

@ApiStatus.Internal
class JsonHelper {
	
	/**
	 * Returns the correct indent for the given {@link Json} using the given {@link JsonConfig}
	 * @param json The json
	 * @param config The config to use
	 * @param separator The separator between the json and the indent
	 * @return A string with the correct indent
	 */
	static @NotNull String correctIndents(Json json, JsonConfig config, String separator) {
		//region Validation
		Objects.requireNonNull(json, "Json must not be null");
		Objects.requireNonNull(config, "Json config must not be null");
		Objects.requireNonNull(separator, "String separator must not be null");
		//endregion
		if (json.isArray() || json.isObject()) {
			List<String> lines = Lists.newArrayList(json.toString(config).split(System.lineSeparator()));
			if (2 > lines.size()) {
				return config.indent() + separator + json.toString(config);
			} else {
				for (int i = 1; i < lines.size() - 1; i++) {
					lines.set(i, config.indent() + lines.get(i));
				}
				return config.indent() + separator + String.join(System.lineSeparator(), lines);
			}
		}
		return config.indent() + separator + json.toString(config);
	}
	
	/**
	 * Returns true if the given {@link Collection} of {@link Json} contains only primitives
	 * @param collection The collection to check
	 * @param configValue The {@link JsonConfig} value
	 * @return Whether the json collection can be simplified
	 */
	static boolean canBeSimplified(Collection<Json> collection, boolean configValue) {
		if (!configValue) {
			return false;
		}
		Objects.requireNonNull(collection, "Json collection must not be null");
		for (Json element : collection) {
			if (!element.isPrimitive()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Returns the given string with quotes if the given {@link JsonConfig} allows it and the string is not already quoted
	 * @param json The json string to quote
	 * @param config The config to use
	 * @return The quoted string
	 * @throws JsonException If the given string is already quoted and the config does not allow it
	 */
	static @NotNull String quote(String json, JsonConfig config) {
		//region Validation
		Objects.requireNonNull(json, "Json string must not be null");
		Objects.requireNonNull(config, "Json config must not be null");
		//endregion
		if (json.charAt(0) == '"' && json.charAt(json.length() - 1) == '"') {
			if (!config.allowQuotedStrings()) {
				throw new JsonException("Quoted strings are not allowed in this configuration");
			}
			return "\"" + "\\\"" + json.substring(1, json.length() - 1) + "\\\"" + "\"";
		}
		return "\"" + json + "\"";
	}
}
