package net.luis.data.properties.config;

import net.luis.data.internal.config.DataConfig;
import net.luis.data.properties.io.PropertyWriter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Property configuration which is use to write properties to file
 *
 * @see PropertyWriter
 *
 * @author Luis-St
 */

public record PropertyConfig(char delimiter, boolean prettyPrint, boolean allowAppend, boolean allowComments, boolean allowEmptyValues, boolean allowEscapedDelimiter, boolean allowCustomExtensions) implements DataConfig {
	
	public static final PropertyConfig DEFAULT = new PropertyConfig('=', true, false, true, true, true, false);
	
	/**
	 * Constructs a new {@link PropertyConfig}
	 * @param delimiter The delimiter to separate the key and the value of a property.
	 *                  The delimiter cannot be a whitespace character, a backslash, a hash or a dot
	 * @param prettyPrint Whether to pretty print the properties
	 * @param allowAppend Whether to allow appending the properties to an existing file.
	 *                    If set to false, the file will be overwritten
	 * @param allowComments Whether to allow comments in the properties file
	 * @param allowEmptyValues Whether to allow empty values in the properties file
	 * @param allowEscapedDelimiter Whether to allow escaped delimiters in the value of a property
	 * @param allowCustomExtensions Whether to allow writing into none properties files like .txt files
	 * @throws IllegalArgumentException If the delimiter is a whitespace character, a backslash, a hash or a dot
	 */
	public PropertyConfig {
		if (Character.isWhitespace(delimiter)) {
			throw new IllegalArgumentException("Property delimiter cannot be a whitespace character");
		}
		if (delimiter == '\\') {
			throw new IllegalArgumentException("Property delimiter cannot be a backslash");
		}
		if (delimiter == '#') {
			throw new IllegalArgumentException("Property delimiter cannot be a hash");
		}
		if (delimiter == '.') {
			throw new IllegalArgumentException("Property delimiter cannot be a dot");
		}
	}
	
	/**
	 * @return A new {@link PropertyConfig.Builder} with the default configuration
	 */
	public static @NotNull PropertyConfig.Builder builder() {
		return builder(DEFAULT);
	}
	
	/**
	 * @param baseConfig The base configuration to use
	 * @return A new {@link PropertyConfig.Builder} with the given base configuration
	 */
	public static @NotNull PropertyConfig.Builder builder(PropertyConfig baseConfig) {
		return new PropertyConfig.Builder(baseConfig);
	}
	
	//region Object overrides
	@Override
	public String toString() {
		return "PropertiesConfig";
	}
	//endregion
	
	//region Builder
	public static class Builder implements DataConfig.Builder<PropertyConfig> {
		
		private char delimiter;
		private boolean prettyPrint;
		private boolean allowAppend;
		private boolean allowComments;
		private boolean allowEmptyValues;
		private boolean allowEscapedDelimiter;
		private boolean allowCustomExtensions;
		
		private Builder(PropertyConfig baseConfig) {
			Objects.requireNonNull(baseConfig, "Property base config must not be null");
			this.delimiter = baseConfig.delimiter();
			this.prettyPrint = baseConfig.prettyPrint();
			this.allowAppend = baseConfig.allowAppend();
			this.allowComments = baseConfig.allowComments();
			this.allowEmptyValues = baseConfig.allowEmptyValues();
			this.allowEscapedDelimiter = baseConfig.allowEscapedDelimiter();
			this.allowCustomExtensions = baseConfig.allowCustomExtensions();
		}
		
		public Builder setDelimiter(char delimiter) {
			this.delimiter = delimiter;
			return this;
		}
		
		public Builder setPrettyPrint(boolean prettyPrint) {
			this.prettyPrint = prettyPrint;
			return this;
		}
		
		public Builder setAllowAppend(boolean allowAppend) {
			this.allowAppend = allowAppend;
			return this;
		}
		
		public Builder setAllowComments(boolean allowComments) {
			this.allowComments = allowComments;
			return this;
		}
		
		public Builder setAllowEmptyValues(boolean allowEmptyValues) {
			this.allowEmptyValues = allowEmptyValues;
			return this;
		}
		
		public Builder setAllowEscapedDelimiter(boolean allowEscapedDelimiter) {
			this.allowEscapedDelimiter = allowEscapedDelimiter;
			return this;
		}
		
		public Builder setAllowCustomExtension(boolean allowCustomExtensions) {
			this.allowCustomExtensions = allowCustomExtensions;
			return this;
		}
		
		@Override
		public PropertyConfig build() {
			return new PropertyConfig(this.delimiter, this.prettyPrint, this.allowAppend, this.allowComments, this.allowEmptyValues, this.allowEscapedDelimiter, this.allowCustomExtensions);
		}
	}
	//endregion
}
