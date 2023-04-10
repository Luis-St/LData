package net.luis.data.json.io;

import net.luis.data.common.io.FileHelper;
import net.luis.data.common.io.Reader;
import net.luis.data.json.JsonArray;
import net.luis.data.json.JsonElement;
import net.luis.data.json.JsonNull;
import net.luis.data.json.JsonObject;
import net.luis.data.json.exception.JsonSyntaxException;
import net.luis.data.json.primitive.JsonBoolean;
import net.luis.data.json.primitive.JsonNumber;
import net.luis.data.json.primitive.JsonString;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;

public class JsonReader implements Reader<JsonElement> {
	
	private final JsonType type;
	private final String json;
	private final int length;
	private int index;
	
	public JsonReader(File file) {
		this(FileHelper.read(file));
	}
	
	public JsonReader(String json) {
		validateJson(json);
		String result = prepareJson(json);
		//region Type detection
		if (!result.isEmpty()) {
			char first = result.charAt(0);
			char last = result.charAt(result.length() - 1);
			if (first == '[' && last == ']') {
				this.type = JsonType.ARRAY;
			} else if (first == '{' && last == '}') {
				this.type = JsonType.OBJECT;
			} else if (first != '{' && first != '[' && last != '}' && last != ']') {
				this.type = JsonType.PROPERTY;
			} else {
				throw new JsonSyntaxException("Json string is not a valid json object, expected a property, array or object");
			}
		} else {
			this.type = JsonType.OBJECT;
		}
		//endregion
		this.json = result;
		this.length = this.json.length();
	}
	
	//region Static helper methods
	private static @NotNull String prepareJson(@NotNull String json) {
		StringBuilder result = new StringBuilder();
		boolean inQuotes = false;
		for (int i = 0; i < json.length(); i++) {
			char c = json.charAt(i);
			if (c == '"' && isNotEscaped(json, i)) {
				inQuotes = !inQuotes;
			}
			if (!Character.isWhitespace(c) || inQuotes) {
				result.append(c);
			}
		}
		return result.toString();
	}
	
	private static boolean isNotEscaped(String file, int index) {
		return index - 1 < 0 || file.charAt(index - 1) != '\\';
	}
	//endregion
	
	//region Validation
	private static void validateJson(String json) {
		Objects.requireNonNull(json, "Json string must not be null");
		boolean inQuotes = false;
		Stack<Character> stack = new Stack<>();
		for (int i = 0; i < json.length(); i++) {
			if (json.charAt(i) == '"' && isNotEscaped(json, i)) {
				inQuotes = !inQuotes;
			}
			if (!inQuotes) {
				if (json.charAt(i) == '{' || json.charAt(i) == '[') {
					stack.push(json.charAt(i));
				} else if (json.charAt(i) == '}') {
					if (stack.isEmpty()) {
						throw new JsonSyntaxException("Found invalid closing bracket '}' for a none existing opening bracket of a json object");
					}
					char c = stack.pop();
					if (c != '{') {
						throw new JsonSyntaxException("Found invalid closing bracket '}' for a opening bracket of a json array");
					}
				} else if (json.charAt(i) == ']') {
					if (stack.isEmpty()) {
						throw new JsonSyntaxException("Found invalid closing bracket ']' for a none existing opening bracket of a json array");
					}
					char c = stack.pop();
					if (c != '[') {
						throw new JsonSyntaxException("Found invalid closing bracket ']' for a opening bracket of a json object");
					}
				}
			}
		}
		if (!stack.isEmpty()) {
			String message;
			if (stack.size() == 1) {
				message = "Found invalid bracket '" + stack.pop() + "' in the json string";
			} else {
				message = "The json string is at several places invalid, the following brackets are invalid: " + stack.stream().map(String::valueOf).collect(Collectors.joining(""));
			}
			throw new JsonSyntaxException(message);
		}
	}
	
	private static @NotNull String validateKey(String key) {
		Objects.requireNonNull(key, "Json key must not be null");
		if (key.charAt(0) != '"' && key.charAt(key.length() - 1) != '"') {
			throw new JsonSyntaxException("Json key '" + key + "' is not a string");
		} else {
			key = key.substring(1, key.length() - 1);
		}
		if (key.isEmpty()) {
			throw new JsonSyntaxException("Json key is empty");
		}
		return key;
	}
	
	private static void validateValue(String key, String value) {
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
	}
	//endregion
	
	@Override
	public boolean hasNext() {
		return this.index < this.length && this.length > 0;
	}
	
	@Override
	public JsonElement next() {
		//region Validation
		if (!this.hasNext()) {
			throw new IndexOutOfBoundsException("Index out of bounds");
		}
		//endregion
		return switch (this.type) {
			case ARRAY -> this.nextArray();
			case OBJECT -> this.nextObject();
			case PROPERTY -> this.nextProperty();
		};
	}
	
	private @NotNull JsonElement nextArray() {
		//region Setup
		if (this.length == 2) {
			this.close();
			return new JsonArray();
		}
		if (this.index == 0) {
			this.index++;
		}
		//endregion
		String value = this.json.substring(this.index, this.findNextInScope(this.index, ',').orElse(this.length - 1));
		this.index += value.length() + 1;
		return this.parseValue("JsonArray part", value);
	}
	
	private @NotNull JsonElement nextObject() {
		//region Setup
		if (this.length == 2) {
			this.close();
			return new JsonObject();
		}
		if (this.index == 0) {
			this.index++;
		}
		//endregion
		int keyIndex = this.findNextInScope(this.index, ':')
				.orElseThrow(() -> new JsonSyntaxException("Json is not a valid object, miss a separator (':') in " + this.substring(this.findNextInScope(this.index, ',').orElse(this.length - 1))));
		int valueIndex = this.findNextInScope(this.index, ',').orElse(this.length - 1);
		String key = validateKey(this.substring(keyIndex));
		this.index = keyIndex + 1;
		JsonObject object = new JsonObject();
		String value = this.substring(valueIndex);
		object.add(key, this.parseValue(key, value));
		this.index = valueIndex + 1;
		return object;
	}
	
	private @NotNull JsonElement nextProperty() {
		int index = this.findNextInScope(0, ':').orElseThrow(() -> new JsonSyntaxException("Json is not a valid property, miss a separator (':') in " + this.json));
		this.close();
		JsonObject object = new JsonObject();
		String key = validateKey(this.json.substring(0, index));
		object.add(key, this.parseValue(key, this.json.substring(index + 1)));
		return object;
	}
	
	public JsonObject toJsonObject() {
		if (this.type != JsonType.OBJECT) {
			return new JsonObject();
		}
		JsonObject result = new JsonObject();
		while (this.hasNext()) {
			if (!(this.next() instanceof JsonObject object)) {
				throw new JsonSyntaxException("Expected JsonObject but got " + this.next().getClass().getSimpleName());
			}
			for (Map.Entry<String, JsonElement> entry : object) {
				result.add(entry.getKey(), entry.getValue());
			}
		}
		return result;
	}
	
	//region IO operations
	public void reset() {
		this.index = 0;
	}
	
	public void close() {
		this.index = this.length;
	}
	//endregion
	
	//region Helper methods
	private @NotNull String substring(int end) {
		return this.json.substring(this.index, end);
	}
	
	private JsonElement parseValue(String key, String value) {
		validateValue(key, value);
		if (value.charAt(0) == '"' && value.charAt(value.length() - 1) == '"') {
			return new JsonString(value.substring(1, value.length() - 1));
		} else if (value.charAt(0) == '[' && value.charAt(value.length() - 1) == ']') {
			JsonArray array = new JsonArray();
			if (value.length() == 2) {
				return array;
			}
			for (JsonElement element : new JsonReader(value)) {
				array.add(element);
			}
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
				for (Map.Entry<String, JsonElement> entry : jsonObject) {
					object.add(entry.getKey(), entry.getValue());
				}
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
	
	private Optional<Integer> findNextInScope(int nextIndex, char key) {
		int result = -1;
		boolean inQuotes = false;
		Stack<Character> stack = new Stack<>();
		for (int i = nextIndex; i < (this.type == JsonType.PROPERTY ? this.length : this.length - 1); i++) {
			char c = this.json.charAt(i);
			if (c == '"' && isNotEscaped(this.json, i)) {
				inQuotes = !inQuotes;
			}
			if (inQuotes) {
				continue;
			}
			if (isNotEscaped(this.json, i)) {
				if (c == '{' || c == '[') {
					stack.push(c);
					continue;
				} else if (c == '}') {
					stack.pop();
					continue;
				} else if (c == ']') {
					stack.pop();
					continue;
				}
			}
			if (c == key) {
				if (stack.isEmpty()) {
					result = i;
					break;
				}
			}
		}
		return result == -1 ? Optional.empty() : Optional.of(result);
	}
	//endregion
	
	//region Object overrides
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof JsonReader that)) return false;
		
		if (this.length != that.length) return false;
		if (this.index != that.index) return false;
		if (this.type != that.type) return false;
		return this.json.equals(that.json);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.type, this.json, this.length, this.index);
	}
	
	@Override
	public String toString() {
		return this.json;
	}
	//endregion
	
	//region Internal
	private enum JsonType {
		ARRAY, OBJECT, PROPERTY
	}
	//endregion
}
