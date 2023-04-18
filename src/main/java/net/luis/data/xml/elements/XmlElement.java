package net.luis.data.xml.elements;

import net.luis.data.xml.XmlHelper;
import net.luis.data.xml.attributes.XmlAttribute;
import net.luis.data.xml.attributes.XmlAttributes;
import net.luis.data.xml.config.XmlConfig;
import net.luis.data.xml.exception.XmlException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class XmlElement {
	
	private final String name;
	private final String value;
	private final XmlAttributes attributes = new XmlAttributes();
	private final XmlElements elements = new XmlElements();
	
	public XmlElement(String name) {
		this.name = XmlHelper.validateXmlEscape(Objects.requireNonNull(name, "Name must not be null"));
		this.value = null;
	}
	
	public XmlElement(String name, boolean value) {
		this(name, String.valueOf(value));
	}
	
	public XmlElement(String name, Number value) {
		this(name, String.valueOf(value));
	}
	
	public XmlElement(String name, String value) {
		this.name = XmlHelper.validateXmlEscape(Objects.requireNonNull(name, "Name must not be null"));
		this.value = StringEscapeUtils.unescapeXml(Objects.requireNonNull(value, "Value must not be null"));
	}
	
	@ApiStatus.Internal
	XmlElement(String name, String value, List<XmlAttribute> attributes, List<XmlElement> elements) {
		this.name = XmlHelper.validateXmlEscape(Objects.requireNonNull(name, "Name must not be null"));
		this.value = StringUtils.isBlank(value) ? null : StringEscapeUtils.unescapeXml(Objects.requireNonNull(value, "Value must not be null"));
		Objects.requireNonNull(attributes, "Attributes must not be null").forEach(this.attributes::add);
		Objects.requireNonNull(elements, "Elements must not be null").forEach(this.elements::add);
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
	
	public void addAttribute(String name, boolean value) {
		this.attributes.add(name, value);
	}
	
	public void addAttribute(String name, Number value) {
		this.attributes.add(name, value);
	}
	
	public void addAttribute(String name, String value) {
		this.attributes.add(name, value);
	}
	
	public void addAttribute(XmlAttribute attribute) {
		this.attributes.add(attribute);
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
	
	public boolean hasElement(XmlElement element) {
		return !this.hasElements() && this.elements.has(element);
	}
	
	public void addElement(String name, String value) {
		if (this.hasValue()) {
			throw new XmlException("Element has a value");
		}
		this.elements.add(name, value);
	}
	
	public void addElement(XmlElement element) {
		if (this.hasValue()) {
			throw new XmlException("Element has a value");
		}
		this.elements.add(element);
	}
	
	public XmlElementBuilder addElement(String name) {
		return new XmlElementBuilder(this, name);
	}
	
	public List<XmlElement> getAllElements(String name) {
		return elements.getAll(name);
	}
	
	public XmlElement getElement(String name) {
		return this.elements.get(name);
	}
	
	public boolean removeElement(String name) {
		return this.elements.remove(name);
	}
	
	public @NotNull XmlElements getElements() {
		return this.hasElements() ? this.elements : new XmlElements();
	}
	//endregion
	
	public @NotNull String toString(XmlConfig config) {
		StringBuilder builder = new StringBuilder("<");
		//region Validation
		if (!config.allowAttributes() && this.hasAttributes()) {
			throw new XmlException("Element attributes are not allowed in this configuration");
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
