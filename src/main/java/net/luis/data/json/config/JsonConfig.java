package net.luis.data.json.config;

import net.luis.data.common.config.DataConfig;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record JsonConfig(boolean prettyPrint, String indent, boolean simplifyPrimitiveArrays, boolean simplifyPrimitiveObjects, boolean allowBlankKeys, boolean allowQuotedStrings, boolean allowCustomExtensions) implements DataConfig {
	
	public static final JsonConfig DEFAULT = new JsonConfig(true, "\t", false, false, true, false, false);
	
	public JsonConfig {
		Objects.requireNonNull(indent, "Json indent cannot be null");
		if (!indent.isBlank()) {
			throw new IllegalArgumentException("Json indent must be whitespace characters only");
		}
	}
	
	public static @NotNull JsonConfig.Builder builder() {
		return builder(DEFAULT);
	}
	
	public static @NotNull JsonConfig.Builder builder(JsonConfig baseConfig) {
		return new JsonConfig.Builder(baseConfig);
	}
	
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
