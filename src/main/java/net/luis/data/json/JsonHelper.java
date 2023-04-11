package net.luis.data.json;

import com.google.common.collect.Lists;
import net.luis.data.json.config.JsonConfig;
import net.luis.data.json.exception.JsonException;
import net.luis.data.json.exception.JsonSyntaxException;
import net.luis.data.json.io.JsonReader;
import net.luis.data.json.primitive.JsonBoolean;
import net.luis.data.json.primitive.JsonNumber;
import net.luis.data.json.primitive.JsonString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class JsonHelper {
	
	public static @NotNull String correctIndents(@NotNull JsonElement element, JsonConfig config, String separator) {
		if (element.isArray() || element.isObject()) {
			List<String> lines = Lists.newArrayList(element.toJson(config).split(System.lineSeparator()));
			if (2 > lines.size()) {
				return config.indent() + separator + element.toJson(config);
			} else {
				for (int i = 1; i < lines.size() - 1; i++) {
					lines.set(i, config.indent() + lines.get(i));
				}
				return config.indent() + separator + String.join(System.lineSeparator(), lines);
			}
		}
		return config.indent() + separator + element.toJson(config);
	}
	
	public static boolean canBeSimplified(Collection<JsonElement> elements, boolean configValue) {
		if (!configValue) {
			return false;
		}
		for (JsonElement element : elements) {
			if (!element.isPrimitive()) {
				return false;
			}
		}
		return true;
	}
	
	public static @NotNull String quote(@NotNull String value, JsonConfig config) {
		if (value.charAt(0) == '"' && value.charAt(value.length() - 1) == '"') {
			if (!config.allowQuotedStrings()) {
				throw new JsonException("Quoted strings are not allowed");
			}
			return "\"" + "\\\"" + value.substring(1, value.length() - 1) + "\\\"" + "\"";
		}
		return "\"" + value + "\"";
	}
	
	public static JsonElement parse(@Nullable String key, String value) {
		//region Validation
		Objects.requireNonNull(value, "Json value of key key '" + key + "' must not be null");
		if (value.isEmpty()) {
			throw new JsonSyntaxException("Json value of key '" + key + "' is empty");
		}
		if (value.isBlank()) {
			throw new JsonSyntaxException("Json value of key '" + key + "' is blank");
		}
		char first = value.charAt(0);
		char last = value.charAt(value.length() - 1);
		if ((first == '"' && last != '"') || (first != '"' && last == '"')) {
			throw new JsonSyntaxException("Json value of key '" + key + "' is not a string");
		}
		if ((first == '{' && last != '}') || (first != '{' && last == '}')) {
			throw new JsonSyntaxException("Json value of key '" + key + "' is not an object");
		}
		if ((first == '[' && last != ']') || (first != '[' && last == ']')) {
			throw new JsonSyntaxException("Json value of key '" + key + "' is not an array");
		}
		//endregion
		if (value.charAt(0) == '"' && value.charAt(value.length() - 1) == '"') {
			return new JsonString(value.substring(1, value.length() - 1));
		} else if (value.charAt(0) == '[' && value.charAt(value.length() - 1) == ']') {
			JsonArray array = new JsonArray();
			if (value.length() == 2) {
				return array;
			}
			new JsonReader(value).forEach(array::add);
			return array;
		} else if (value.charAt(0) == '{' && value.charAt(value.length() - 1) == '}') {
			JsonObject object = new JsonObject();
			if (value.length() == 2) {
				return object;
			}
			for (JsonElement element : new JsonReader(value)) {
				if (!(element instanceof JsonObject jsonObject)) {
					throw new JsonSyntaxException("Json element is not valid, expected a JsonObject but got " + element.getClass().getSimpleName());
				}
				jsonObject.forEach((entry) -> object.add(entry.getKey(), entry.getValue()));
			}
			return object;
		} else if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
			return new JsonBoolean(Boolean.parseBoolean(value));
		} else if (value.equals("null")) {
			return JsonNull.INSTANCE;
		} else {
			return new JsonNumber(Double.parseDouble(value));
		}
	}
	
}
