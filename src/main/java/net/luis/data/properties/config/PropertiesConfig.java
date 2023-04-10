package net.luis.data.properties.config;

import net.luis.data.common.config.DataConfig;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 *
 * @author Luis-St
 *
 */

public class PropertiesConfig implements DataConfig {
	
	public static final PropertiesConfig DEFAULT = new PropertiesConfig('=', true, false, true, true, true, true, false);
	
	private final char delimiter;
	private final boolean prettyPrint;
	private final boolean allowAppend;
	private final boolean allowComments;
	private final boolean allowEmptyValues;
	private final boolean allowDuplicateValues;
	private final boolean allowEscapedDelimiter;
	private final boolean allowCustomExtensions;
	
	private PropertiesConfig(char delimiter, boolean prettyPrint, boolean allowAppend, boolean allowComments, boolean allowEmptyValues, boolean allowDuplicateValues, boolean allowEscapedDelimiter, boolean allowCustomExtensions) {
		//region Validation
		if (Character.isWhitespace(delimiter)) {
			throw new IllegalArgumentException("Delimiter cannot be a whitespace character");
		}
		if (delimiter == '\\') {
			throw new IllegalArgumentException("Delimiter cannot be a backslash");
		}
		if (delimiter == '#') {
			throw new IllegalArgumentException("Delimiter cannot be a hash");
		}
		//endregion
		this.delimiter = delimiter;
		this.prettyPrint = prettyPrint;
		this.allowAppend = allowAppend;
		this.allowComments = allowComments;
		this.allowEmptyValues = allowEmptyValues;
		this.allowDuplicateValues = allowDuplicateValues;
		this.allowEscapedDelimiter = allowEscapedDelimiter;
		this.allowCustomExtensions = allowCustomExtensions;
	}
	
	public static @NotNull PropertiesConfig.Builder builder() {
		return new PropertiesConfig.Builder();
	}
	
	//region Getters
	public char getDelimiter() {
		return this.delimiter;
	}
	
	public boolean prettyPrint() {
		return this.prettyPrint;
	}
	
	public boolean allowAppend() {
		return this.allowAppend;
	}
	
	public boolean allowComments() {
		return this.allowComments;
	}
	
	public boolean allowEmptyValues() {
		return this.allowEmptyValues;
	}
	
	public boolean allowDuplicateValues() {
		return this.allowDuplicateValues;
	}
	
	public boolean allowEscapedDelimiter() {
		return this.allowEscapedDelimiter;
	}
	
	public boolean allowCustomExtensions() {
		return this.allowCustomExtensions;
	}
	//endregion
	
	//region Withers
	public PropertiesConfig withDelimiter(char delimiter) {
		return new PropertiesConfig(delimiter, this.prettyPrint, this.allowAppend, this.allowComments, this.allowEmptyValues, this.allowDuplicateValues, this.allowEscapedDelimiter, this.allowCustomExtensions);
	}
	
	public PropertiesConfig withPrettyPrint(boolean prettyPrint) {
		return new PropertiesConfig(this.delimiter, prettyPrint, this.allowAppend, this.allowComments, this.allowEmptyValues, this.allowDuplicateValues, this.allowEscapedDelimiter, this.allowCustomExtensions);
	}
	
	public PropertiesConfig withAllowAppend(boolean allowAppend) {
		return new PropertiesConfig(this.delimiter, this.prettyPrint, allowAppend, this.allowComments, this.allowEmptyValues, this.allowDuplicateValues, this.allowEscapedDelimiter, this.allowCustomExtensions);
	}
	
	public PropertiesConfig withAllowComments(boolean allowComments) {
		return new PropertiesConfig(this.delimiter, this.prettyPrint, this.allowAppend, allowComments, this.allowEmptyValues, this.allowDuplicateValues, this.allowEscapedDelimiter, this.allowCustomExtensions);
	}
	
	public PropertiesConfig withAllowEmptyValues(boolean allowEmptyValues) {
		return new PropertiesConfig(this.delimiter, this.prettyPrint, this.allowAppend, this.allowComments, allowEmptyValues, this.allowDuplicateValues, this.allowEscapedDelimiter, this.allowCustomExtensions);
	}
	
	public PropertiesConfig withAllowDuplicateValues(boolean allowDuplicateValues) {
		return new PropertiesConfig(this.delimiter, this.prettyPrint, this.allowAppend, this.allowComments, this.allowEmptyValues, allowDuplicateValues, this.allowEscapedDelimiter, this.allowCustomExtensions);
	}
	
	public PropertiesConfig withAllowEscapedDelimiter(boolean allowEscapedDelimiter) {
		return new PropertiesConfig(this.delimiter, this.prettyPrint, this.allowAppend, this.allowComments, this.allowEmptyValues, this.allowDuplicateValues, allowEscapedDelimiter, this.allowCustomExtensions);
	}
	
	public PropertiesConfig withAllowCustomExtensions(boolean allowCustomExtension) {
		return new PropertiesConfig(this.delimiter, this.prettyPrint, this.allowAppend, this.allowComments, this.allowEmptyValues, this.allowDuplicateValues, this.allowEscapedDelimiter, allowCustomExtension);
	}
	//endregion
	
	//region Object overrides
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PropertiesConfig that)) return false;
		
		if (this.delimiter != that.delimiter) return false;
		if (this.prettyPrint != that.prettyPrint) return false;
		if (this.allowAppend != that.allowAppend) return false;
		if (this.allowComments != that.allowComments) return false;
		if (this.allowEmptyValues != that.allowEmptyValues) return false;
		if (this.allowDuplicateValues != that.allowDuplicateValues) return false;
		if (this.allowEscapedDelimiter != that.allowEscapedDelimiter) return false;
		return this.allowCustomExtensions == that.allowCustomExtensions;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.delimiter, this.prettyPrint, this.allowAppend, this.allowComments, this.allowEmptyValues, this.allowDuplicateValues, this.allowEscapedDelimiter, this.allowCustomExtensions);
	}
	
	@Override
	public String toString() {
		return "PropertiesConfig{delimiter=" + this.delimiter + ", prettyPrint=" + this.prettyPrint + ", allowAppend=" + this.allowAppend + ", allowComments=" + this.allowComments + ", allowEmptyValues=" + this.allowEmptyValues +
				", allowDuplicateValues=" + this.allowDuplicateValues + ", allowEscapedDelimiter=" + this.allowEscapedDelimiter + ", allowCustomExtensions=" + this.allowCustomExtensions + "}";
	}
	//endregion
	
	//region Builder
	public static class Builder implements DataConfig.Builder<PropertiesConfig> {
		
		private char delimiter = PropertiesConfig.DEFAULT.getDelimiter();
		private boolean prettyPrint = PropertiesConfig.DEFAULT.prettyPrint();
		private boolean allowAppend = PropertiesConfig.DEFAULT.allowAppend();
		private boolean allowComments = PropertiesConfig.DEFAULT.allowComments();
		private boolean allowEmptyValues = PropertiesConfig.DEFAULT.allowEmptyValues();
		private boolean allowDuplicateValues = PropertiesConfig.DEFAULT.allowDuplicateValues();
		private boolean allowEscapedDelimiter = PropertiesConfig.DEFAULT.allowEscapedDelimiter();
		private boolean allowCustomExtensions = PropertiesConfig.DEFAULT.allowCustomExtensions();
		
		private Builder() {
		
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
		
		public Builder setAllowDuplicateValues(boolean allowDuplicateValues) {
			this.allowDuplicateValues = allowDuplicateValues;
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
		public PropertiesConfig build() {
			return new PropertiesConfig(this.delimiter, this.prettyPrint, this.allowAppend, this.allowComments, this.allowEmptyValues, this.allowDuplicateValues, this.allowEscapedDelimiter, this.allowCustomExtensions);
		}
		
	}
	//endregion
	
}
