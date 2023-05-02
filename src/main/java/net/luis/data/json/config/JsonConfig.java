package net.luis.data.json.config;

import net.luis.data.common.config.DataConfig;
import net.luis.data.json.io.JsonWriter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Json configuration which is use to write json to file
 *
 * @see JsonWriter
 *
 * @author Luis-St
 */

public record JsonConfig(boolean prettyPrint, String indent, boolean simplifyPrimitiveArrays, boolean simplifyPrimitiveObjects, boolean allowBlankKeys, boolean allowQuotedStrings, boolean allowCustomExtensions) implements DataConfig {
	
	public static final JsonConfig DEFAULT = new JsonConfig(true, "\t", false, false, true, false, false);
	
	/**
	 * Constructs a new {@link JsonConfig} with the given parameters
	 * @param prettyPrint Whether to pretty print the json.
	 *                    If pretty printing is enabled, the json will be written with newlines and indents
	 * @param indent The indent to use when pretty printing.
	 *               The indent must only contain whitespace characters
	 * @param simplifyPrimitiveArrays Whether to simplify arrays which only contain primitive values into a single line
	 * @param simplifyPrimitiveObjects Whether to simplify objects which only contain primitive values into a single line
	 * @param allowBlankKeys Whether to allow blank keys in json objects
	 * @param allowQuotedStrings Whether to allow quoted strings in json strings like "Hello World"
	 * @param allowCustomExtensions Whether to allow writing into none json files like .txt files
	 * @throws NullPointerException If the indent is null
	 * @throws IllegalArgumentException If the indent is not whitespace characters only
	 */
	public JsonConfig {
		Objects.requireNonNull(indent, "Json indent cannot be null");
		if (!indent.isBlank()) {
			throw new IllegalArgumentException("Json indent must be whitespace characters only");
		}
	}
	
	/**
	 * @return A new {@link JsonConfig.Builder} with the default configuration
	 */
	public static @NotNull JsonConfig.Builder builder() {
		return builder(DEFAULT);
	}
	
	/**
	 * @param baseConfig The base configuration to use
	 * @return A new {@link JsonConfig.Builder} with the given base configuration
	 */
	public static @NotNull JsonConfig.Builder builder(JsonConfig baseConfig) {
		return new JsonConfig.Builder(baseConfig);
	}
	
	/**
	 * @return False as json does not support appending
	 */
	@Override
	public boolean allowAppend() {
		return false;
	}
	
	//region Object overrides
	@Override
	public String toString() {
		return "JsonConfig";
	}
	//endregion
	
	//region Builder
	public static class Builder implements DataConfig.Builder<JsonConfig> {
		
		private boolean prettyPrint;
		private String indent;
		private boolean simplifyPrimitiveArrays;
		private boolean simplifyPrimitiveObjects;
		private boolean allowBlankKeys;
		private boolean allowQuotedStrings;
		private boolean allowCustomExtensions;
		
		private Builder(JsonConfig baseConfig) {
			Objects.requireNonNull(baseConfig, "Json base config must not be null");
			this.prettyPrint = baseConfig.prettyPrint();
			this.indent = baseConfig.indent();
			this.simplifyPrimitiveArrays = baseConfig.simplifyPrimitiveArrays();
			this.simplifyPrimitiveObjects = baseConfig.simplifyPrimitiveObjects();
			this.allowBlankKeys = baseConfig.allowBlankKeys();
			this.allowQuotedStrings = baseConfig.allowQuotedStrings();
			this.allowCustomExtensions = baseConfig.allowCustomExtensions();
		}
		
		public JsonConfig.Builder prettyPrint(boolean prettyPrint) {
			this.prettyPrint = prettyPrint;
			return this;
		}
		
		public JsonConfig.Builder indent(String indent) {
			this.indent = Objects.requireNonNull(indent, "Json indent must not be null");
			return this;
		}
		
		public JsonConfig.Builder simplifyPrimitiveArrays(boolean simplifyPrimitiveArrays) {
			this.simplifyPrimitiveArrays = simplifyPrimitiveArrays;
			return this;
		}
		
		public JsonConfig.Builder simplifyPrimitiveObjects(boolean simplifyPrimitiveObjects) {
			this.simplifyPrimitiveObjects = simplifyPrimitiveObjects;
			return this;
		}
		
		public JsonConfig.Builder allowBlankKeys(boolean allowBlankKeys) {
			this.allowBlankKeys = allowBlankKeys;
			return this;
		}
		
		public JsonConfig.Builder allowQuotedStrings(boolean allowQuotedStrings) {
			this.allowQuotedStrings = allowQuotedStrings;
			return this;
		}
		
		public JsonConfig.Builder allowCustomExtensions(boolean allowCustomExtensions) {
			this.allowCustomExtensions = allowCustomExtensions;
			return this;
		}
		
		@Override
		public JsonConfig build() {
			return new JsonConfig(this.prettyPrint, this.indent, this.simplifyPrimitiveArrays, this.simplifyPrimitiveObjects, this.allowBlankKeys, this.allowQuotedStrings, this.allowCustomExtensions);
		}
	}
	//endregion
}
