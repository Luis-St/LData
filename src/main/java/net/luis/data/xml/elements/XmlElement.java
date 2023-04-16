package net.luis.data.xml.elements;

import net.luis.data.xml.XmlHelper;
import net.luis.data.xml.attributes.XmlAttribute;
import net.luis.data.xml.attributes.XmlAttributes;
import net.luis.data.xml.config.XmlConfig;
import net.luis.data.xml.exception.XmlException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class XmlElement {
	
	private final String prefix;
	private final String name;
	private final String value;
	private final XmlAttributes attributes = new XmlAttributes();
	private final XmlElements elements = new XmlElements();
	
	//region Constructor overloads
	public XmlElement(String name) {
		Objects.requireNonNull(name, "Name must not be null");
		if (name.contains(":")) {
			String[] parts = name.split(":");
			if (parts.length != 2) {
				throw new XmlException("Invalid element name: " + name);
			}
			this.prefix = XmlHelper.validateXmlEscape(parts[0]);
			this.name = XmlHelper.validateXmlEscape(parts[1]);
		} else {
			this.prefix = "";
			this.name = XmlHelper.validateXmlEscape(name);
		}
		this.value = null;
	}
	
	public XmlElement(String name, String value) {
		Objects.requireNonNull(name, "Name must not be null");
		if (name.contains(":")) {
			String[] parts = name.split(":");
			if (parts.length != 2) {
				throw new XmlException("Invalid element name: " + name);
			}
			this.prefix = XmlHelper.validateXmlEscape(parts[0]);
			this.name = XmlHelper.validateXmlEscape(parts[1]);
		} else {
			this.prefix = "";
			this.name = XmlHelper.validateXmlEscape(name);
		}
		this.value = StringEscapeUtils.unescapeXml(Objects.requireNonNull(value, "Value must not be null"));
	}
	//endregion
	
	public XmlElement(String prefix, String name, String value) {
		this.prefix = XmlHelper.validateXmlEscape(Objects.requireNonNull(prefix, "Prefix must not be null"));
		this.name = XmlHelper.validateXmlEscape(Objects.requireNonNull(name, "Name must not be null"));
		this.value = StringEscapeUtils.unescapeXml(Objects.requireNonNull(value, "Value must not be null"));
	}
	
	public String getPrefix() {
		return this.prefix;
	}
	
	public String getName() {
		return this.name;
	}
	
	public @NotNull XmlElement copy() {
		XmlElement copy = this.hasValue() ? new XmlElement(this.name, this.value) : new XmlElement(this.name);
		this.attributes.forEach(attribute -> copy.addAttribute(attribute.copy()));
		if (!this.hasValue()) {
			this.elements.forEach(element -> copy.addElement(element.copy()));
		}
		return copy;
	}
	
	//region Value
	public boolean hasValue() {
		return this.value != null;
	}
	
	public String getAsString() {
		try {
			return Objects.requireNonNull(this.value);
		} catch (NullPointerException e) {
			throw new XmlException("Element has no value");
		}
	}
	
	public boolean getAsBoolean() {
		try {
			return Boolean.parseBoolean(this.value);
		} catch (NullPointerException e) {
			throw new XmlException("Element has no value");
		}
	}
	
	public int getAsInt() {
		try {
			return Integer.parseInt(this.value);
		} catch (NullPointerException e) {
			throw new XmlException("Element has no value");
		} catch (NumberFormatException e) {
			throw new XmlException("Element value is not an integer: " + this.value);
		}
	}
	
	public long getAsLong() {
		try {
			return Long.parseLong(this.value);
		} catch (NullPointerException e) {
			throw new XmlException("Element has no value");
		} catch (NumberFormatException e) {
			throw new XmlException("Element value is not a long: " + this.value);
		}
	}
	
	public double getAsDouble() {
		try {
			return Double.parseDouble(this.value);
		} catch (NullPointerException e) {
			throw new XmlException("Element has no value");
		} catch (NumberFormatException e) {
			throw new XmlException("Element value is not a double: " + this.value);
		}
	}
	//endregion
	
	//region Attributes
	public boolean hasAttributes() {
		return !this.attributes.isEmpty();
	}
	
	public boolean hasAttribute(String name) {
		return this.attributes.has(name);
	}
	
	public boolean hasAttribute(XmlAttribute attribute) {
		return this.attributes.has(attribute);
	}
	
	public void addAttribute(XmlAttribute attribute) {
		this.attributes.add(attribute);
	}
	
	public void addAttribute(String name, boolean value) {
		this.attributes.add(name, value);
	}
	
	public void addAttribute(String name, Number value) {
		this.attributes.add(name, value);
	}
	
	public void addAttribute(String name, String value) {
		this.attributes.add(name, value);
	}
	
	public XmlAttribute getAttribute(String name) {
		return this.attributes.get(name);
	}
	
	public XmlAttribute removeAttribute(String name) {
		return this.attributes.remove(name);
	}
	
	public @NotNull XmlAttributes getAttributes() {
		return this.attributes;
	}
	//endregion
	
	//region Elements
	public boolean hasElements() {
		return !this.hasValue() && !this.elements.isEmpty();
	}
	
	public boolean hasElement(String name) {
		return !this.hasElements() && this.elements.has(name);
	}
	
	public boolean hasElement(String prefix, String name) {
		return !this.hasElements() && this.elements.has(prefix, name);
	}
	
	public boolean hasElement(XmlElement element) {
		return !this.hasElements() && this.elements.has(element);
	}
	
	public void addElement(XmlElement element) {
		if (this.hasValue()) {
			throw new XmlException("Element has a value");
		}
		this.elements.add(element);
	}
	
	public void addElement(String name, String value) {
		if (this.hasValue()) {
			throw new XmlException("Element has a value");
		}
		this.elements.add(name, value);
	}
	
	public void addElement(String prefix, String name, String value) {
		if (this.hasValue()) {
			throw new XmlException("Element has a value");
		}
		this.elements.add(prefix, name, value);
	}
	
	public List<XmlElement> getAllElements(String name) {
		return elements.getAll(name);
	}
	
	public List<XmlElement> getAllElements(String prefix, String name) {
		return elements.getAll(prefix, name);
	}
	
	public XmlElement getElement(String name) {
		return this.elements.get(name);
	}
	
	public XmlElement getElement(String prefix, String name) {
		return this.elements.get(prefix, name);
	}
	
	public boolean removeElement(String name) {
		return this.elements.remove(name);
	}
	
	public @NotNull XmlElements getElements() {
		return this.hasElements() ? this.elements : new XmlElements();
	}
	//endregion
	
	public @NotNull String toString(XmlConfig config) {
		
		
		return null;
	}
	
	//region Object overrides
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof XmlElement element)) return false;
		
		if (!this.prefix.equals(element.prefix)) return false;
		if (!this.name.equals(element.name)) return false;
		if (!Objects.equals(this.value, element.value)) return false;
		if (!this.attributes.equals(element.attributes)) return false;
		return this.elements.equals(element.elements);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.prefix, this.name, this.value, this.attributes, this.elements);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("XmlElement \"");
		if (this.prefix.isEmpty()) {
			builder.append(this.name);
		} else {
			builder.append(this.prefix).append(":").append(this.name);
		}
		builder.append("\"");
		if (this.hasValue()) {
			builder.append(": ").append(this.value);
		}
		return builder.toString();
	}
	//endregion
}
