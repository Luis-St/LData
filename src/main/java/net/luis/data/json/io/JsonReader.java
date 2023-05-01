package net.luis.data.json.io;

import net.luis.data.common.io.AbstractReader;
import net.luis.data.common.io.FileHelper;
import net.luis.data.common.util.DataUtils;
import net.luis.data.json.*;
import net.luis.data.json.exception.JsonException;
import net.luis.data.json.exception.JsonReaderIndexOutOfBoundsException;
import net.luis.data.json.exception.JsonSyntaxException;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Stack;

/**
 * A reader for json strings which converts them into {@link Json json} objects
 *
 * @see Json
 * @see AbstractReader
 *
 * @author Luis-St
 */

public class JsonReader extends AbstractReader<Json> implements JsonSerializable<Json> {
	
	private final JsonType type;
	
	/**
	 * Constructs a new {@link JsonReader} with the given json file
	 * @param file The json file to read
	 */
	public JsonReader(File file) {
		this(FileHelper.read(file));
	}
	
	/**
	 * Constructs a new {@link JsonReader} with the given json string
	 * @param json The json string to read
	 */
	public JsonReader(String json) {
		super(json);
		this.type = this.value().isEmpty() ? JsonType.OBJECT : getType(this.value().charAt(0), this.value().charAt(this.value().length() - 1));
	}
	
	private static JsonType getType(char first, char last) {
		if (first == '[' && last == ']') {
			return JsonType.ARRAY;
		} else if (first == '{' && last == '}') {
			return JsonType.OBJECT;
		} else if (first != '{' && first != '[' && last != '}' && last != ']') {
			return JsonType.PROPERTY;
		} else {
			throw new JsonSyntaxException("Json string is not a valid, expected a property, array or object");
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
			if (value.charAt(i) == '"' && DataUtils.isNotEscaped(value, i)) {
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
			throw new JsonSyntaxException("Given json string is invalid because it is missing at least one closing bracket");
		}
		//endregion
	}
	
	@Override
	protected String modify(String original) {
		return DataUtils.deleteWhitespace(original, '\"');
	}
	
	@Override
	public Json next() {
		//region Validation
		if (!this.hasNext()) {
			throw new JsonReaderIndexOutOfBoundsException("Json reader is at the end of the file");
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
	
	private @NotNull Json nextArray() {
		//region Setup
		if (this.length() == 2) {
			this.close();
			return new JsonArray();
		}
		//endregion
		String value = this.value().substring(this.index, this.findNextInScope(this.index, ',').orElse(this.length() - 1));
		this.index += value.length() + 1;
		return this.parse("json array part", value);
	}
	
	private @NotNull Json nextObject() {
		//region Setup
		if (this.length() == 2) {
			this.close();
			return new JsonObject();
		}
		//endregion
		int valueIndex = this.findNextInScope(this.index, ',').orElse(this.length() - 1);
		int keyIndex = this.findNextInScope(this.index, ':').orElseThrow(() -> new JsonSyntaxException("Json is not a valid object, miss a separator (':') in '" + this.fromIndex(valueIndex) + "'"));
		String key = this.validateKey(this.fromIndex(keyIndex));
		this.index = keyIndex + 1;
		JsonObject object = new JsonObject();
		object.add(key, this.parse(key, this.fromIndex(valueIndex)));
		this.index = valueIndex + 1;
		return object;
	}
	
	private @NotNull Json nextProperty() {
		int index = this.findNextInScope(0, ':').orElseThrow(() -> new JsonSyntaxException("Json is not a valid property, miss a separator (':') in '" + this.value() + "'"));
		this.close();
		JsonObject object = new JsonObject();
		String key = this.validateKey(this.value().substring(0, index));
		object.add(key, this.parse(key, this.value().substring(index + 1)));
		return object;
	}
	
	@Override
	public @NotNull Json toJson() {
		this.reset();
		return switch (this.type) {
			case ARRAY -> this.toArray();
			case OBJECT ->  this.toObject();
			case PROPERTY -> {
				Json json = this.next();
				this.close();
				yield json;
			}
			default -> throw new JsonException("Json type is not a valid type");
		};
	}
	
	private @NotNull Json toArray() {
		JsonArray array = new JsonArray();
		while (this.hasNext()) {
			array.add(this.next());
		}
		this.close();
		return array;
	}
	
	private @NotNull Json toObject() {
		JsonObject object = new JsonObject();
		while (this.hasNext()) {
			Json json = this.next();
			if (!(json instanceof JsonObject jsonObject)) {
				throw new JsonSyntaxException("Expected json object but got: " + json.getName());
			}
			for (Map.Entry<String, Json> entry : jsonObject) {
				object.add(entry.getKey(), entry.getValue());
			}
		}
		this.close();
		return object;
	}
	
	
	//region Helper methods
	private Optional<Integer> findNextInScope(int nextIndex, char key) {
		boolean inQuotes = false;
		Stack<Character> stack = new Stack<>();
		for (int i = nextIndex; i < (this.type == JsonType.PROPERTY ? this.length() : this.length() - 1); i++) {
			char c = this.value().charAt(i);
			if (c == '"' && DataUtils.isNotEscaped(this.value(), i)) {
				inQuotes = !inQuotes;
			}
			if (inQuotes) {
				continue;
			}
			if (DataUtils.isNotEscaped(this.value(), i)) {
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
	
	private Json parse(String key, String json) {
		//region Validation
		Objects.requireNonNull(key, "Json key must not be null");
		Objects.requireNonNull(json, "Json string of key '" + key + "' must not be null");
		if (json.isEmpty()) {
			throw new JsonSyntaxException("Json string of key '" + key + "' is empty");
		}
		if (json.isBlank()) {
			throw new JsonSyntaxException("Json value of key '" + key + "' is blank");
		}
		char first = json.charAt(0);
		char last = json.charAt(json.length() - 1);
		if ((first == '"' && last != '"') || (first != '"' && last == '"')) {
			throw new JsonSyntaxException("Json string of key '" + key + "' is not a string");
		}
		if ((first == '{' && last != '}') || (first != '{' && last == '}')) {
			throw new JsonSyntaxException("Json string of key '" + key + "' is not an object");
		}
		if ((first == '[' && last != ']') || (first != '[' && last == ']')) {
			throw new JsonSyntaxException("Json string of key '" + key + "' is not an array");
		}
		//endregion
		if (json.charAt(0) == '"' && json.charAt(json.length() - 1) == '"') {
			return new JsonString(json.substring(1, json.length() - 1));
		} else if (json.charAt(0) == '[' && json.charAt(json.length() - 1) == ']') {
			JsonArray array = new JsonArray();
			if (json.length() == 2) {
				return array;
			}
			new JsonReader(json).forEach(array::add);
			return array;
		} else if (json.charAt(0) == '{' && json.charAt(json.length() - 1) == '}') {
			JsonObject object = new JsonObject();
			if (json.length() == 2) {
				return object;
			}
			for (Json element : new JsonReader(json)) {
				if (!(element instanceof JsonObject jsonObject)) {
					throw new JsonSyntaxException("Json is not valid, expected a json object but got " + element.getName());
				}
				jsonObject.forEach((entry) -> object.add(entry.getKey(), entry.getValue()));
			}
			return object;
		} else if (json.equalsIgnoreCase("true") || json.equalsIgnoreCase("false")) {
			return new JsonBoolean(Boolean.parseBoolean(json));
		} else if (json.equals("null")) {
			return JsonNull.INSTANCE;
		} else {
			return new JsonNumber(Double.parseDouble(json));
		}
	}
	//endregion
	
	private enum JsonType {
		ARRAY, OBJECT, PROPERTY
	}
}
