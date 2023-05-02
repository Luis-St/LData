package net.luis.data.properties.io;

import net.luis.data.common.io.AbstractWriter;
import net.luis.data.common.util.DataUtils;
import net.luis.data.properties.Property;
import net.luis.data.properties.config.PropertyConfig;

import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * A writer for {@link Property} objects to write them to a file
 *
 * @see Property
 * @see AbstractWriter
 *
 * @author Luis-St
 */

public class PropertyWriter extends AbstractWriter<PropertyConfig> {
	
	private static final List<String> ALLOWED_EXTENSIONS = List.of("props", "properties", "cfg", "conf", "config", "configuration", "ini", "settings", "prefs", "preferences");
	
	/**
	 * Constructs a new {@link PropertyWriter} with the default {@link PropertyConfig}
	 * @param file The file to write to as a string
	 */
	public PropertyWriter(String file) {
		super(file, PropertyConfig.DEFAULT);
	}
	
	/**
	 * Constructs a new {@link PropertyWriter} with the default {@link PropertyConfig}
	 * @param file The file to write to
	 */
	public PropertyWriter(File file) {
		super(file, PropertyConfig.DEFAULT);
	}
	
	/**
	 * Constructs a new {@link PropertyWriter} with the given {@link PropertyConfig}
	 * @param file The file to write to as a string
	 * @param config The {@link PropertyConfig} to use
	 */
	public PropertyWriter(String file, PropertyConfig config) {
		super(file, config);
	}
	
	/**
	 * Constructs a new {@link PropertyWriter} with the given {@link PropertyConfig}
	 * @param file The file to write to
	 * @param config The {@link PropertyConfig} to use
	 */
	public PropertyWriter(File file, PropertyConfig config) {
		super(file, config);
	}
	
	@Override
	protected boolean isExtensionNotAllowed(String extension) {
		return !ALLOWED_EXTENSIONS.contains(extension);
	}
	
	/**
	 * Writes a comment to the file
	 * @param comment The comment to write
	 * @throws NullPointerException If the comment is null
	 * @throws UnsupportedOperationException If comments are not allowed in the {@link PropertyConfig}
	 * @throws RuntimeException If an error occurs while writing the comment to the file
	 */
	public void writeComment(String comment) {
		Objects.requireNonNull(comment, "Comment must not be null");
		if (this.config.allowComments()) {
			if (comment.isBlank()) {
				return;
			}
			if (comment.startsWith("#")) {
				this.write(comment);
			} else {
				this.write(this.config.prettyPrint() ? "# " : "#" + comment);
			}
		} else {
			throw new UnsupportedOperationException("Comments are not allowed in this configuration");
		}
	}
	
	/**
	 * Writes a {@link Property} to the file
	 * @param property The property to write
	 * @throws NullPointerException If the property is null
	 * @throws IllegalArgumentException If the property key or value is invalid
	 * @throws RuntimeException If an error occurs while writing the property to the file
	 */
	public void write(Property property) {
		Objects.requireNonNull(property, "Property must not be null");
		String key = Objects.requireNonNull(property.getKey(), "Property key must not be null");
		//region Key validation
		if (key.isEmpty()) {
			throw new IllegalArgumentException("Property key must not be empty");
		}
		if (key.isBlank()) {
			throw new IllegalArgumentException("Property key must not be blank");
		}
		if (key.contains(" ")) {
			throw new IllegalArgumentException("Property key must not contain spaces");
		}
		if (key.contains(String.valueOf(this.config.delimiter()))) {
			throw new IllegalArgumentException("Property key must not contain the delimiter character '" + this.config.delimiter() + "'");
		}
		//endregion
		String value = Objects.requireNonNull(property.toString(this.config), "Property value must not be null");
		//region Value validation
		if (value.isEmpty() && !this.config.allowEmptyValues()) {
			throw new IllegalArgumentException("Empty property values are not allowed in this configuration");
		}
		if (value.isBlank()) {
			throw new IllegalArgumentException("Blank property values can not be written, put them into quotes");
		}
		int index = value.indexOf(this.config.delimiter());
		if (index != -1) {
			if (DataUtils.isNotEscaped(value, index)) {
				throw new IllegalArgumentException("Property value must not contain the delimiter character '" + this.config.delimiter() + "' unless it is escaped");
			}
			if (!this.config.allowEscapedDelimiter()) {
				throw new IllegalArgumentException("Escaped delimiter characters '" + this.config.delimiter() + "' are not allowed in the property value in this configuration");
			}
		}
		//endregion
		if (property.isCommented()) {
			this.write(property.getAsCommented().getComment(this.config));
		}
		if (this.config.prettyPrint()) {
			this.write(key.toLowerCase() + " " + this.config.delimiter() + " " + value);
		} else {
			this.write(key.toLowerCase() + this.config.delimiter() + value);
		}
	}
	
	private void write(String line) {
		try {
			this.writer.write(line + System.lineSeparator());
		} catch (Exception e) {
			throw new RuntimeException("Fail to write to property file", e);
		}
	}
}
