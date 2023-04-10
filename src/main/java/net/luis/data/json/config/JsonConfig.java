package net.luis.data.json.config;

import net.luis.data.common.config.DataConfig;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record JsonConfig(boolean prettyPrint, String indent, boolean simplifyPrimitiveArrays, boolean simplifyPrimitiveObjects, boolean allowBlankKeys, boolean allowQuotedStrings, boolean allowCustomExtensions) implements DataConfig {
	
	public static final JsonConfig DEFAULT = new JsonConfig(true, "\t", false, false, true, false, false);
	
	public static @NotNull JsonConfig.Builder builder() {
		return new JsonConfig.Builder();
	}
	
	//region Withers
	public @NotNull JsonConfig withPrettyPrint(boolean prettyPrint) {
		return new JsonConfig(prettyPrint, this.indent, this.simplifyPrimitiveArrays, this.simplifyPrimitiveObjects, this.allowBlankKeys, this.allowQuotedStrings, this.allowCustomExtensions);
	}
	
	public @NotNull JsonConfig withIndent(String indent) {
		return new JsonConfig(this.prettyPrint, indent, this.simplifyPrimitiveArrays, this.simplifyPrimitiveObjects, this.allowBlankKeys, this.allowQuotedStrings, this.allowCustomExtensions);
	}
	
	public @NotNull JsonConfig withSimplifyPrimitiveArrays(boolean simplifyPrimitiveArrays) {
		return new JsonConfig(this.prettyPrint, this.indent, simplifyPrimitiveArrays, this.simplifyPrimitiveObjects, this.allowBlankKeys, this.allowQuotedStrings, this.allowCustomExtensions);
	}
	
	public @NotNull JsonConfig withSimplifyPrimitiveObjects(boolean simplifyPrimitiveObjects) {
		return new JsonConfig(this.prettyPrint, this.indent, this.simplifyPrimitiveArrays, simplifyPrimitiveObjects, this.allowBlankKeys, this.allowQuotedStrings, this.allowCustomExtensions);
	}
	
	public @NotNull JsonConfig withAllowBlankKeys(boolean allowBlankKeys) {
		return new JsonConfig(this.prettyPrint, this.indent, this.simplifyPrimitiveArrays, this.simplifyPrimitiveObjects, allowBlankKeys, this.allowQuotedStrings, this.allowCustomExtensions);
	}
	
	public @NotNull JsonConfig withAllowQuotedStrings(boolean allowQuotedStrings) {
		return new JsonConfig(this.prettyPrint, this.indent, this.simplifyPrimitiveArrays, this.simplifyPrimitiveObjects, this.allowBlankKeys, allowQuotedStrings, this.allowCustomExtensions);
	}
	
	public @NotNull JsonConfig withAllowCustomExtensions(boolean allowCustomExtensions) {
		return new JsonConfig(this.prettyPrint, this.indent, this.simplifyPrimitiveArrays, this.simplifyPrimitiveObjects, this.allowBlankKeys, this.allowQuotedStrings, allowCustomExtensions);
	}
	//endregion
	
	//region Object overrides
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof JsonConfig that)) return false;
		
		if (this.prettyPrint != that.prettyPrint) return false;
		if (!Objects.equals(this.indent, that.indent)) return false;
		if (this.simplifyPrimitiveArrays != that.simplifyPrimitiveArrays) return false;
		if (this.simplifyPrimitiveObjects != that.simplifyPrimitiveObjects) return false;
		if (this.allowBlankKeys != that.allowBlankKeys) return false;
		if (this.allowQuotedStrings != that.allowQuotedStrings) return false;
		return this.allowCustomExtensions == that.allowCustomExtensions;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.prettyPrint, this.indent, this.simplifyPrimitiveArrays, this.simplifyPrimitiveObjects, this.allowBlankKeys, this.allowQuotedStrings, this.allowCustomExtensions);
	}
	
	@Override
	public String toString() {
		return "JsonConfig{prettyPrint=" + this.prettyPrint + ", indent=" + this.indent + ", simplifyPrimitiveArrays=" + this.simplifyPrimitiveArrays + ", simplifyPrimitiveObjects=" + this.simplifyPrimitiveObjects +
				", allowBlankKeys=" + this.allowBlankKeys + ", allowQuotedStrings=" + this.allowQuotedStrings + ", allowCustomExtensions=" + this.allowCustomExtensions + "}";
	}
	//endregion
	
	//region Builder
	public static class Builder implements DataConfig.Builder<JsonConfig> {
		
		private boolean prettyPrint = JsonConfig.DEFAULT.prettyPrint();
		private String indent = JsonConfig.DEFAULT.indent();
		private boolean simplifyPrimitiveArrays = JsonConfig.DEFAULT.simplifyPrimitiveArrays();
		private boolean simplifyPrimitiveObjects = JsonConfig.DEFAULT.simplifyPrimitiveObjects();
		private boolean allowBlankKeys = JsonConfig.DEFAULT.allowBlankKeys();
		private boolean allowQuotedStrings = JsonConfig.DEFAULT.allowQuotedStrings();
		private boolean allowCustomExtensions = JsonConfig.DEFAULT.allowCustomExtensions();
		
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
