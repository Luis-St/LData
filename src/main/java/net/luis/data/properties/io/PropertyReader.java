package net.luis.data.properties.io;

import net.luis.data.common.io.AbstractReader;
import net.luis.data.json.io.JsonReader;
import net.luis.data.properties.*;
import net.luis.data.properties.exception.PropertyException;
import net.luis.data.properties.primitive.PropertyBoolean;
import net.luis.data.properties.primitive.PropertyNumber;
import net.luis.data.properties.primitive.PropertyString;
import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class PropertyReader extends AbstractReader<Property> {
	
	private final char delimiter;
	
	public PropertyReader(File file, char delimiter) {
		super(file);
		this.delimiter = delimiter;
	}
	
	public PropertyReader(String value, char delimiter) {
		super(value);
		this.delimiter = delimiter;
		System.out.println(this.delimiter);
	}
	
	@Override
	protected void validate(String value) {
		//region Validation
		super.validate(value);
		for (String line : value.split(System.lineSeparator())) {
			if (line.isBlank() || line.startsWith("#")) {
				continue;
			}
			if (!line.contains(String.valueOf(this.delimiter))) {
				throw new PropertyException("Invalid property line, no delimiter (" + this.delimiter + ") found: '" + line + "'");
			}
			if (line.substring(0, line.indexOf(this.delimiter)).isBlank()) {
				throw new PropertyException("Invalid property line, no key found: '" + line + "'");
			}
		}
		//endregion
	}
	
	@Override
	protected String modify(String original) {
		Objects.requireNonNull(original, "Original must not be null");
		return String.join(System.lineSeparator(), Arrays.stream(original.split(System.lineSeparator())).filter(line -> !line.isBlank() && !line.startsWith("#")).toArray(String[]::new));
	}
	
	@Override
	public Property next() {
		//region Validation
		if (!this.hasNext()) {
			throw new IndexOutOfBoundsException("Index out of bounds");
		}
		//endregion
		String line = this.fromIndex(this.findNext(System.lineSeparator()).orElse(this.length()));
		String key = line.substring(0, line.indexOf(this.delimiter)).strip();
		String value = line.substring(line.indexOf(this.delimiter) + 1).strip();
		this.index += line.length() + System.lineSeparator().length();
		if (key.contains(".")) {
			List<String> objects = Arrays.asList(key.split("\\."));
			Property result = this.parse(objects.get(objects.size() - 1), value);
			for (int i = objects.size() - 2; i >= 0; i--) {
				result = new ObjectProperty(objects.get(i), result);
			}
			return result;
		} else {
			return this.parse(key, value);
		}
	}
	
	public Properties toProperties() {
		Properties properties = new Properties();
		while (this.hasNext()) {
			properties.add(this.next());
		}
		this.close();
		return properties;
	}
	
	//region Helper methods
	private Optional<Integer> findNext(String value) {
		if (value == null || value.isEmpty()) {
			return Optional.empty();
		}
		for (int i = this.index; i < this.length(); i++) {
			if (this.value().charAt(i) == value.charAt(0)) {
				if (value.length() == 1) {
					return Optional.of(i);
				}
				boolean found = true;
				for (int j = 0; j < value.length() && i + j < this.length(); j++) {
					if (value.charAt(j) != this.value().charAt(i + j)) {
						found = false;
						break;
					}
				}
				if (found) {
					return Optional.of(i);
				}
			}
		}
		return Optional.empty();
	}
	
	private @NotNull Property parse(String key, String value) {
		Objects.requireNonNull(value);
		if (value.isBlank()) {
			return new PropertyString(key, "");
		}
		char first = value.charAt(0);
		char last = value.charAt(value.length() - 1);
		String inner = value.substring(1, value.length() - 1);
		if (first == '{' && last == '}') {
			return new PropertyJson(key, new JsonReader(value).toJson());
		} else if (first == '[' && last == ']') {
			PropertyArray array = new PropertyArray(key);
			if (inner.isBlank()) {
				return array;
			}
			for (String part : Stream.of(inner.split(",")).map(String::strip).filter(part -> !part.isBlank()).toArray(String[]::new)) {
				if (part.equalsIgnoreCase("true") || part.equalsIgnoreCase("false")) {
					array.add(Boolean.parseBoolean(part));
				} else if (NumberUtils.isNumber(part)) {
					array.add(NumberUtils.toDouble(part));
				} else {
					array.add(part);
				}
			}
			return array;
		} else if ("null".equalsIgnoreCase(value) || "\"null\"".equalsIgnoreCase(value)) {
			return new PropertyNull(key);
		} else if (first == '"' && last == '"') {
			if (inner.isBlank()) {
				return new PropertyString(key, value.substring(1, value.length() - 1));
			} else {
				return new PropertyString(key, value);
			}
		} else if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
			return new PropertyBoolean(key, Boolean.parseBoolean(value));
		} else if (NumberUtils.isNumber(value)) {
			return new PropertyNumber(key, NumberUtils.toDouble(value));
		} else {
			return new PropertyString(key, value);
		}
	}
	//endregion
}
