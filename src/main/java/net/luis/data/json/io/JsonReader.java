package net.luis.data.json.io;

import net.luis.data.common.io.AbstractReader;
import net.luis.data.common.io.FileHelper;
import net.luis.data.common.util.Utils;
import net.luis.data.json.JsonArray;
import net.luis.data.json.JsonElement;
import net.luis.data.json.JsonHelper;
import net.luis.data.json.JsonObject;
import net.luis.data.json.exception.JsonSyntaxException;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Stack;

public class JsonReader extends AbstractReader<JsonElement> {
	
	private final JsonType type;
	
	public JsonReader(File file) {
		this(FileHelper.read(file));
	}
	
	public JsonReader(String json) {
		super(json);
		this.type = this.value.isEmpty() ? JsonType.OBJECT : getType(this.value.charAt(0), this.value.charAt(this.value.length() - 1));
	}
	
	private static JsonType getType(char first, char last) {
		if (first == '[' && last == ']') {
			return JsonType.ARRAY;
		} else if (first == '{' && last == '}') {
			return JsonType.OBJECT;
		} else if (first != '{' && first != '[' && last != '}' && last != ']') {
			return JsonType.PROPERTY;
		} else {
			throw new JsonSyntaxException("Json string is not a valid json object, expected a property, array or object");
		}
	}
	
	@Override
	protected void validate(String value) {
		//region Validation
		Objects.requireNonNull(value, "Json string must not be null");
		boolean inQuotes = false;
		Stack<Character> stack = new Stack<>();
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			if (value.charAt(i) == '"' && Utils.isNotEscaped(value, i)) {
				inQuotes = !inQuotes;
			}
			if (!inQuotes) {
				switch (c) {
					case '{', '[' -> stack.push(c);
					case '}' -> {
						if (stack.isEmpty()) {
							throw new JsonSyntaxException("Found invalid closing bracket '}' for a none existing opening bracket of a json object");
						}
						if (stack.pop() != '{') {
							throw new JsonSyntaxException("Found invalid closing bracket '}' for a opening bracket of a json array");
						}
					}
					case ']' -> {
						if (stack.isEmpty()) {
							throw new JsonSyntaxException("Found invalid closing bracket ']' for a none existing opening bracket of a json array");
						}
						if (stack.pop() != '[') {
							throw new JsonSyntaxException("Found invalid closing bracket ']' for a opening bracket of a json object");
						}
					}
				}
			}
		}
		if (!stack.isEmpty()) {
			throw new JsonSyntaxException("The given json string is invalid");
		}
		//endregion
	}
	
	@Override
	protected String modify(String original) {
		return Utils.deleteWhitespace(original);
	}
	
	@Override
	public JsonElement next() {
		//region Validation
		if (!this.hasNext()) {
			throw new IndexOutOfBoundsException("Index out of bounds");
		}
		if (this.type != JsonType.PROPERTY && this.index == 0) {
			this.index++;
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
		//endregion
		String value = this.value.substring(this.index, this.findNextInScope(this.index, ',').orElse(this.length - 1));
		this.index += value.length() + 1;
		return JsonHelper.parse("JsonArray part", value);
	}
	
	private @NotNull JsonElement nextObject() {
		//region Setup
		if (this.length == 2) {
			this.close();
			return new JsonObject();
		}
		//endregion
		int valueIndex = this.findNextInScope(this.index, ',').orElse(this.length - 1);
		int keyIndex = this.findNextInScope(this.index, ':').orElseThrow(() -> new JsonSyntaxException("Json is not a valid object, miss a separator (':') in '" + this.fromIndex(valueIndex) + "'"));
		String key = this.validateKey(this.fromIndex(keyIndex));
		this.index = keyIndex + 1;
		JsonObject object = new JsonObject();
		object.add(key, JsonHelper.parse(key, this.fromIndex(valueIndex)));
		this.index = valueIndex + 1;
		return object;
	}
	
	private @NotNull JsonElement nextProperty() {
		int index = this.findNextInScope(0, ':').orElseThrow(() -> new JsonSyntaxException("Json is not a valid property, miss a separator (':') in '" + this.value + "'"));
		this.close();
		JsonObject object = new JsonObject();
		String key = this.validateKey(this.value.substring(0, index));
		object.add(key, JsonHelper.parse(key, this.value.substring(index + 1)));
		return object;
	}
	
	public JsonObject toJson() {
		if (this.type != JsonType.OBJECT) {
			this.close();
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
		this.close();
		return result;
	}
	
	//region Helper methods
	private Optional<Integer> findNextInScope(int nextIndex, char key) {
		int result = -1;
		boolean inQuotes = false;
		Stack<Character> stack = new Stack<>();
		for (int i = nextIndex; i < (this.type == JsonType.PROPERTY ? this.length : this.length - 1); i++) {
			char c = this.value.charAt(i);
			if (c == '"' && Utils.isNotEscaped(this.value, i)) {
				inQuotes = !inQuotes;
			}
			if (inQuotes) {
				continue;
			}
			if (Utils.isNotEscaped(this.value, i)) {
				if (c == '{' || c == '[') {
					stack.push(c);
					continue;
				} else if (c == '}' || c == ']') {
					stack.pop();
					continue;
				}
			}
			if (c == key && stack.isEmpty()) {
				return Optional.of(i);
			}
		}
		return Optional.empty();
	}
	
	private @NotNull String validateKey(String key) {
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
	//endregion
	
	private enum JsonType {
		ARRAY, OBJECT, PROPERTY
	}
}
