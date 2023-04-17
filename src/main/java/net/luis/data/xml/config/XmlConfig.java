package net.luis.data.xml.config;

import net.luis.data.common.config.DataConfig;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public record XmlConfig(Charset encoding, boolean prettyPrint, String indent, boolean allowAttributes, boolean allowCustomExtensions) implements DataConfig {
	
	public static final XmlConfig DEFAULT = new XmlConfig(StandardCharsets.UTF_8, true, "\t", true, false);
	
	public XmlConfig {
		Objects.requireNonNull(indent, "Indent cannot be null");
		if (!indent.isBlank()) {
			throw new IllegalArgumentException("Indent must be whitespace characters only");
		}
	}
	
	public static @NotNull XmlConfig.Builder builder() {
		return builder(DEFAULT);
	}
	
	public static @NotNull XmlConfig.Builder builder(XmlConfig baseConfig) {
		return new XmlConfig.Builder(baseConfig);
	}
	
	@Override
	public boolean allowAppend() {
		return false;
	}
	
	@Override
	public String toString() {
		return "XmlConfig";
	}
	
	//region Builder
	public static class Builder implements DataConfig.Builder<XmlConfig> {
		
		private Charset encoding;
		private boolean prettyPrint;
		private String indent;
		private boolean allowAttributes;
		private boolean allowCustomExtensions;
		
		private Builder(@NotNull XmlConfig config) {
			this.encoding = config.encoding();
			this.prettyPrint = config.prettyPrint();
			this.indent = config.indent();
			this.allowAttributes = config.allowAttributes();
			this.allowCustomExtensions = config.allowCustomExtensions();
		}
		
		public XmlConfig.Builder encoding(Charset encoding) {
			this.encoding = encoding;
			return this;
		}
		
		public XmlConfig.Builder prettyPrint(boolean prettyPrint) {
			this.prettyPrint = prettyPrint;
			return this;
		}
		
		public XmlConfig.Builder indent(String indent) {
			this.indent = indent;
			return this;
		}
		
		public XmlConfig.Builder allowAttributes(boolean allowAttributes) {
			this.allowAttributes = allowAttributes;
			return this;
		}
		
		public XmlConfig.Builder allowCustomExtensions(boolean allowCustomExtensions) {
			this.allowCustomExtensions = allowCustomExtensions;
			return this;
		}
		
		@Override
		public XmlConfig build() {
			return new XmlConfig(this.encoding, this.prettyPrint, this.indent, this.allowAttributes, this.allowCustomExtensions);
		}
	}
	//endregion
}
