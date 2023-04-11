package net.luis.data.json.config;

import net.luis.data.common.config.DataConfig;
import org.jetbrains.annotations.NotNull;

public record JsonConfig(boolean prettyPrint, String indent, boolean simplifyPrimitiveArrays, boolean simplifyPrimitiveObjects, boolean allowBlankKeys, boolean allowQuotedStrings, boolean allowCustomExtensions) implements DataConfig {
	
	public static final JsonConfig DEFAULT = new JsonConfig(true, "\t", false, false, true, false, false);
	
	public static @NotNull JsonConfig.Builder builder() {
		return new JsonConfig.Builder(JsonConfig.DEFAULT);
	}
	
	@Override
	public boolean allowAppend() {
		return false;
	}
	
	@Override
	public String toString() {
		return "JsonConfig";
	}
	
	//region Builder
	public static class Builder implements DataConfig.Builder<JsonConfig> {
		
		private boolean prettyPrint;
		private String indent;
		private boolean simplifyPrimitiveArrays;
		private boolean simplifyPrimitiveObjects;
		private boolean allowBlankKeys;
		private boolean allowQuotedStrings;
		private boolean allowCustomExtensions;
		
		private Builder(@NotNull JsonConfig config) {
			this.prettyPrint = config.prettyPrint();
			this.indent = config.indent();
			this.simplifyPrimitiveArrays = config.simplifyPrimitiveArrays();
			this.simplifyPrimitiveObjects = config.simplifyPrimitiveObjects();
			this.allowBlankKeys = config.allowBlankKeys();
			this.allowQuotedStrings = config.allowQuotedStrings();
			this.allowCustomExtensions = config.allowCustomExtensions();
		}
		
		public JsonConfig.Builder prettyPrint(boolean prettyPrint) {
			this.prettyPrint = prettyPrint;
			return this;
		}
		
		public JsonConfig.Builder indent(String indent) {
			this.indent = indent;
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
