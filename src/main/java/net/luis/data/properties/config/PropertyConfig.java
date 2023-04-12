package net.luis.data.properties.config;

import net.luis.data.common.config.DataConfig;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 *
 * @author Luis-St
 *
 */

public record PropertyConfig(char delimiter, boolean prettyPrint, boolean allowAppend, boolean allowComments, boolean allowEmptyValues, boolean allowEscapedDelimiter, boolean allowCustomExtensions) implements DataConfig {
	
	public static final PropertyConfig DEFAULT = new PropertyConfig('=', true, false, true, true, true, false);
	
	public PropertyConfig {
		if (Character.isWhitespace(delimiter)) {
			throw new IllegalArgumentException("Delimiter cannot be a whitespace character");
		}
		if (delimiter == '\\') {
			throw new IllegalArgumentException("Delimiter cannot be a backslash");
		}
		if (delimiter == '#') {
			throw new IllegalArgumentException("Delimiter cannot be a hash");
		}
		if (delimiter == '.') {
			throw new IllegalArgumentException("Delimiter cannot be a dot");
		}
	}
	
	public static @NotNull PropertyConfig.Builder builder() {
		return builder(DEFAULT);
	}
	
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
			Objects.requireNonNull(baseConfig, "Base config must not be null");
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
