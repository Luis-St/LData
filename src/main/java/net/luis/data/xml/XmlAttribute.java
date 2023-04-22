package net.luis.data.xml;

import net.luis.data.xml.config.XmlConfig;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class XmlAttribute {
	
	private final String name;
	private final String value;
	
	public XmlAttribute(String name, boolean value) {
		this(name, String.valueOf(value));
	}
	
	public XmlAttribute(String name, Number value) {
		this(name, String.valueOf(value));
	}
	
	public XmlAttribute(String name, String value) {
		this.name = XmlHelper.validateXmlEscape(Objects.requireNonNull(name, "Xml attribute name must not be null"));
		this.value = Objects.requireNonNull(value, "Xml attribute value must not be null");
	}
	
	public String getName() {
		return this.name;
	}
	
	public @NotNull XmlAttribute copy() {
		return new XmlAttribute(this.name, this.value);
	}
	
	//region Getters
	public String getAsString() {
		return this.value;
	}
	
	public boolean getAsBoolean() {
		return Boolean.parseBoolean(this.value);
	}
	
	public int getAsInt() {
		return Integer.parseInt(this.value);
	}
	
	public long getAsLong() {
		return Long.parseLong(this.value);
	}
	
	public double getAsDouble() {
		return Double.parseDouble(this.value);
	}
	//endregion
	
	public @NotNull String toString(XmlConfig config) {
		return this.name + "=\"" + this.value + "\"";
	}
	
	//region Object overrides
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof XmlAttribute that)) return false;
		
		if (!this.name.equals(that.name)) return false;
		return this.value.equals(that.value);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.name, this.value);
	}
	
	@Override
	public String toString() {
		return "XmlAttribute " + this.name + "=" + this.value;
	}
	//endregion
}
