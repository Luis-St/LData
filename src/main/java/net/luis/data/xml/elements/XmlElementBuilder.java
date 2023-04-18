package net.luis.data.xml.elements;

import com.google.common.collect.Lists;
import net.luis.data.xml.attributes.XmlAttribute;
import net.luis.data.xml.exception.XmlException;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class XmlElementBuilder {
	
	private final XmlElement root;
	private final XmlElementBuilder rootBuilder;
	private final String name;
	private final Mutable<String> value = new MutableObject<>(null);
	private final List<XmlAttribute> attributes = Lists.newArrayList();
	private final List<XmlElement> elements = Lists.newArrayList();
	
	@ApiStatus.Internal
	XmlElementBuilder(XmlElement root, String name) {
		this.root = Objects.requireNonNull(root, "Root element must not be null");
		this.rootBuilder = null;
		this.name = Objects.requireNonNull(name, "Name must not be null");
	}
	
	@ApiStatus.Internal
	private XmlElementBuilder(XmlElementBuilder rootBuilder, String name) {
		this.root = null;
		this.rootBuilder = Objects.requireNonNull(rootBuilder, "Root element builder must not be null");
		this.name = Objects.requireNonNull(name, "Name must not be null");
	}
	
	public void setValue(String value) {
		if (this.elements.size() > 0) {
			throw new XmlException("Cannot set value of element with child elements");
		}
		this.value.setValue(Objects.requireNonNull(value, "Value must not be null"));
	}
	
	public XmlElementBuilder addAttribute(String name, boolean value) {
		return this.addAttribute(new XmlAttribute(name, value));
	}
	
	public XmlElementBuilder addAttribute(String name, Number value) {
		return this.addAttribute(new XmlAttribute(name, Objects.requireNonNull(value, "Value must not be null")));
	}
	
	public XmlElementBuilder addAttribute(String name, String value) {
		return this.addAttribute(new XmlAttribute(name, Objects.requireNonNull(value, "Value must not be null")));
	}
	
	public XmlElementBuilder addAttribute(XmlAttribute attribute) {
		this.attributes.add(Objects.requireNonNull(attribute, "Attribute must not be null"));
		return this;
	}
	
	public XmlElementBuilder addElement(String name) {
		return new XmlElementBuilder(this, name);
	}
	
	public XmlElementBuilder addElement(String name, String value) {
		return this.addElement(new XmlElement(name, value));
	}
	
	private XmlElementBuilder addElement(XmlElement element) {
		if (this.value.getValue() != null) {
			throw new XmlException("Cannot add child elements to element with value");
		}
		this.elements.add(Objects.requireNonNull(element, "Element must not be null"));
		return this;
	}
	
	public XmlElementBuilder build() {
		if (this.root != null) {
			this.root.addElement(this.buildElement());
			return this;
		} else if (this.rootBuilder != null) {
			return this.rootBuilder.addElement(this.buildElement());
		}
		throw new IllegalStateException("Cannot build element without root part");
	}
	
	private @NotNull XmlElement buildElement() {
		return new XmlElement(this.name, this.value.getValue(), this.attributes, this.elements);
	}
	
}
