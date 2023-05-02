package net.luis.data.xml;

import net.luis.data.xml.config.XmlConfig;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Represents an Xml attribute
 *
 * @author Luis-St
 */

public final class XmlAttribute {
	
	private final String name;
	private final String value;
	
	/**
	 * Constructs a new Xml attribute with the given name and value
	 * @param name The name of the attribute
	 * @param value The value of the attribute as a boolean
	 * @throws NullPointerException If the name is null
	 */
	public XmlAttribute(String name, boolean value) {
		this(name, String.valueOf(value));
	}
	
	/**
	 * Constructs a new Xml attribute with the given name and value
	 * @param name The name of the attribute
	 * @param value The value of the attribute as a number
	 * @throws NullPointerException If the name is null
	 */
	public XmlAttribute(String name, Number value) {
		this(name, String.valueOf(value));
	}
	
	/**
	 * Constructs a new Xml attribute with the given name and value
	 * @param name The name of the attribute
	 * @param value The value of the attribute
	 * @throws NullPointerException If the name or value is null
	 * @throws IllegalArgumentException If the value contains an invalid xml character
	 */
	public XmlAttribute(String name, String value) {
		this.name = XmlHelper.validateXmlEscape(Objects.requireNonNull(name, "Xml attribute name must not be null"));
		this.value = Objects.requireNonNull(value, "Xml attribute value must not be null");
	}
	
	/**
	 * @return The name of the attribute
	 */
	public @NotNull String getName() {
		return this.name;
	}
	
	/**
	 * @return A copy of the xml attribute
	 */
	public @NotNull XmlAttribute copy() {
		return new XmlAttribute(this.name, this.value);
	}
	
	//region Getters
	
	/**
	 * @return The value of the attribute
	 */
	public String getAsString() {
		return this.value;
	}
	
	/**
	 * @return The value of the attribute as a boolean
	 */
	public boolean getAsBoolean() {
		return Boolean.parseBoolean(this.value);
	}
	
	/**
	 * @return The value of the attribute as a int
	 * @throws NumberFormatException If the value is not a valid int
	 */
	public int getAsInt() {
		return Integer.parseInt(this.value);
	}
	
	/**
	 * @return The value of the attribute as a long
	 * @throws NumberFormatException If the value is not a valid long
	 */
	public long getAsLong() {
		return Long.parseLong(this.value);
	}
	
	/**
	 * @return The value of the attribute as a double
	 * @throws NumberFormatException If the value is not a valid double
	 */
	public double getAsDouble() {
		return Double.parseDouble(this.value);
	}
	//endregion
	
	/**
	 * Converts the attribute to a string using the given config
	 * @param config The config to use
	 * @return The attribute as a string
	 */
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
