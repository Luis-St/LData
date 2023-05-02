package net.luis.data.xml;

import net.luis.data.xml.config.XmlConfig;
import net.luis.data.xml.exception.XmlException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * Represents an xml element
 *
 * @author Luis-St
 */

public final class XmlElement {
	
	private final String name;
	private final String value;
	private final XmlAttributes attributes = new XmlAttributes();
	private final XmlElements elements = new XmlElements();
	
	/**
	 * Constructs a new xml element with the given name
	 * @param name The name of the xml element
	 * @throws NullPointerException If the name is null
	 * @throws XmlException If the name contains an invalid xml character
	 */
	public XmlElement(String name) {
		this.name = XmlHelper.validateXmlEscape(Objects.requireNonNull(name, "Xml element name must not be null"));
		this.value = null;
	}
	
	/**
	 * Constructs a new xml element with the given name and value
	 * @param name The name of the xml element
	 * @param value The value of the xml element as a boolean
	 * @throws NullPointerException If the name is null
	 */
	public XmlElement(String name, boolean value) {
		this(name, String.valueOf(value));
	}
	
	/**
	 * Constructs a new xml element with the given name and value
	 * @param name The name of the xml element
	 * @param value The value of the xml element as a number
	 * @throws NullPointerException If the name is null
	 */
	public XmlElement(String name, Number value) {
		this(name, String.valueOf(value));
	}
	
	/**
	 * Constructs a new xml element with the given name and value
	 * @param name The name of the xml element
	 * @param value The value of the xml element
	 * @throws NullPointerException If the name or value is null
	 * @throws XmlException If the name contains an invalid xml character
	 */
	public XmlElement(String name, String value) {
		this.name = XmlHelper.validateXmlEscape(Objects.requireNonNull(name, "Xml element name must not be null"));
		this.value = StringEscapeUtils.unescapeXml(Objects.requireNonNull(value, "Xml element value must not be null"));
	}
	
	/**
	 * @return The name of the xml element
	 */
	public @NotNull String getName() {
		return this.name;
	}
	
	/**
	 * @return A copy of the xml element
	 */
	public @NotNull XmlElement copy() {
		XmlElement copy = this.hasValue() ? new XmlElement(this.name, this.value) : new XmlElement(this.name);
		this.attributes.forEach(attribute -> copy.addAttribute(attribute.copy()));
		if (!this.hasValue()) {
			this.elements.forEach(element -> copy.addElement(element.copy()));
		}
		return copy;
	}
	
	//region Value
	
	/**
	 * @return True if the xml element has a value
	 */
	public boolean hasValue() {
		return this.value != null;
	}
	
	/**
	 * @return The value of the xml element as a string
	 * @throws XmlException If the xml element has no value
	 */
	public String getAsString() {
		try {
			return Objects.requireNonNull(this.value);
		} catch (NullPointerException e) {
			throw new XmlException("Xml element '" + this.getName() + "' has no value");
		}
	}
	
	/**
	 * @return The value of the xml element as a boolean
	 * @throws XmlException If the xml element has no value
	 * @throws NullPointerException If the value is not a boolean
	 */
	public boolean getAsBoolean() {
		if (this.value == null) {
			throw new XmlException("Xml element '" + this.getName() + "' has no value");
		} else if (this.value.equalsIgnoreCase("true") || this.value.equalsIgnoreCase("false")) {
			return Boolean.parseBoolean(this.value);
		}
		throw new NullPointerException("Xml element '" + this.getName() + "' value is not a boolean: " + this.value);
	}
	
	/**
	 * @return The value of the xml element as an integer
	 * @throws XmlException If the xml element has no value
	 * @throws NumberFormatException If the value is not an integer
	 */
	public int getAsInt() {
		try {
			return Integer.parseInt(this.value);
		} catch (NullPointerException e) {
			throw new XmlException("Xml element '" + this.getName() + "' has no value");
		}
	}
	
	/**
	 * @return The value of the xml element as a long
	 * @throws XmlException If the xml element has no value
	 * @throws NumberFormatException If the value is not a long
	 */
	public long getAsLong() {
		try {
			return Long.parseLong(this.value);
		} catch (NullPointerException e) {
			throw new XmlException("Xml element '" + this.getName() + "' has no value");
		}
	}
	
	/**
	 * @return The value of the xml element as a double
	 * @throws XmlException If the xml element has no value
	 * @throws NumberFormatException If the value is not a double
	 */
	public double getAsDouble() {
		try {
			return Double.parseDouble(this.value);
		} catch (NullPointerException e) {
			throw new XmlException("Xml element '" + this.getName() + "' has no value");
		}
	}
	//endregion
	
	//region Attributes
	
	/**
	 * @return True if the xml element has attributes
	 */
	public boolean hasAttributes() {
		return !this.attributes.isEmpty();
	}
	
	/**
	 * Delegates to {@link XmlAttributes#has(String)}
	 */
	public boolean hasAttribute(String name) {
		return this.attributes.has(name);
	}
	
	/**
	 * Delegates to {@link XmlAttributes#has(XmlAttribute)}
	 */
	public boolean hasAttribute(XmlAttribute attribute) {
		return this.attributes.has(attribute);
	}
	
	/**
	 * Delegates to {@link XmlAttributes#add(String, boolean)}
	 */
	public void addAttribute(String name, boolean value) {
		this.attributes.add(name, value);
	}
	
	/**
	 * Delegates to {@link XmlAttributes#add(String, Number)}
	 */
	public void addAttribute(String name, Number value) {
		this.attributes.add(name, value);
	}
	
	/**
	 * Delegates to {@link XmlAttributes#add(String, String)}
	 */
	public void addAttribute(String name, String value) {
		this.attributes.add(name, value);
	}
	
	/**
	 * Delegates to {@link XmlAttributes#add(XmlAttribute)}
	 */
	public void addAttribute(XmlAttribute attribute) {
		this.attributes.add(attribute);
	}
	
	/**
	 * Delegates to {@link XmlAttributes#get(String)}
	 */
	public XmlAttribute getAttribute(String name) {
		return this.attributes.get(name);
	}
	
	/**
	 * Delegates to {@link XmlAttributes#remove(String) }
	 */
	public XmlAttribute removeAttribute(String name) {
		return this.attributes.remove(name);
	}
	
	/**
	 * @return The attributes of the xml element
	 */
	public @NotNull XmlAttributes getAttributes() {
		return this.attributes;
	}
	//endregion
	
	//region Elements
	
	/**
	 * @return True if the xml element has no value and has elements
	 */
	public boolean hasElements() {
		return !this.hasValue() && !this.elements.isEmpty();
	}
	
	/**
	 * Checks if the xml element has an element with the given name
	 * @param name The name of the element
	 * @return True if the xml element has no value and has an element with the given name
	 */
	public boolean hasElement(String name) {
		return !this.hasElements() && this.elements.has(name);
	}
	
	/**
	 * Checks if the xml element has the given element
	 * @param element The element to check
	 * @return True if the xml element has no value and has the given element
	 */
	public boolean hasElement(XmlElement element) {
		return !this.hasElements() && this.elements.has(element);
	}
	
	/**
	 * Adds an element with the given name and value
	 * @param name The name of the element
	 * @param value The value of the element
	 * @throws XmlException If the xml element has a value
	 */
	public void addElement(String name, String value) {
		if (this.hasValue()) {
			throw new XmlException("Xml element '" + this.getName() + "' has a value");
		}
		this.elements.add(name, value);
	}
	
	/**
	 * Adds the given element
	 * @param element The element to add
	 * @throws XmlException If the xml element has a value
	 */
	public void addElement(XmlElement element) {
		if (this.hasValue()) {
			throw new XmlException("Xml element '" + this.getName() + "' has a value");
		}
		this.elements.add(element);
	}
	
	/**
	 * Gets all elements with the given name
	 * @param name The name of the elements
	 * @return A unmodifiable list of elements with the given name
	 */
	public @NotNull List<XmlElement> getAllElements(String name) {
		return List.copyOf(this.elements.getAll(name));
	}
	
	/**
	 * Delegates to {@link XmlElements#get(String)}
	 */
	public XmlElement getElement(String name) {
		return this.elements.get(name);
	}
	
	/**
	 * Delegates to {@link XmlElements#remove(String)}
	 */
	public boolean removeElement(String name) {
		return this.elements.remove(name);
	}
	
	/**
	 * @return The elements of the xml element if it has no value
	 */
	public @NotNull XmlElements getElements() {
		return this.hasElements() ? this.elements : new XmlElements();
	}
	//endregion
	
	/**
	 * Converts the xml element to a string using the given config
	 * @param config The config to use
	 * @return The xml element as a string
	 */
	public @NotNull String toString(XmlConfig config) {
		Objects.requireNonNull(config, "Xml config must not be null");
		StringBuilder builder = new StringBuilder("<");
		//region Validation
		if (!config.allowAttributes() && this.hasAttributes()) {
			throw new XmlException("Xml element attributes are not allowed in this configuration");
		}
		//endregion
		builder.append(this.name);
		if (this.hasAttributes()) {
			builder.append(" ").append(this.attributes.toString(config));
		}
		if (this.hasValue()) {
			builder.append(">").append(StringEscapeUtils.escapeXml(this.value)).append("</").append(this.name);
			builder.append(config.prettyPrint() ? ">\n" : ">");
			return builder.toString();
		}
		if (!this.hasElements()) {
			return builder.append(config.prettyPrint() ? "/>\n" : "/>").toString();
		}
		builder.append(config.prettyPrint() ? ">\n" : ">");
		String intent = config.prettyPrint() ? config.indent() : "";
		for (XmlElement element : this.elements) {
			if (!config.prettyPrint()) {
				builder.append(element.toString(config));
				continue;
			}
			String[] parts = element.toString(config).split("\n");
			builder.append(intent).append(parts[0]).append("\n");
			if (parts.length == 1) {
				continue;
			}
			for (int i = 1; i < parts.length - 1; i++) {
				builder.append(intent).append(parts[i]).append("\n");
			}
			builder.append(intent).append(parts[parts.length - 1]).append("\n");
		}
		builder.append("</").append(this.name).append(config.prettyPrint() ? ">\n" : ">");
		return builder.toString();
	}
	
	//region Object overrides
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof XmlElement element)) return false;
		
		if (!this.name.equals(element.name)) return false;
		if (!Objects.equals(this.value, element.value)) return false;
		if (!this.attributes.equals(element.attributes)) return false;
		return this.elements.equals(element.elements);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.name, this.value, this.attributes, this.elements);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("XmlElement \"");
		builder.append(this.name).append("\"");
		if (this.hasValue()) {
			builder.append(": ").append(this.value);
		}
		return builder.toString();
	}
	//endregion
}
