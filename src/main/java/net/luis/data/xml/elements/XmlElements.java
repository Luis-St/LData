package net.luis.data.xml.elements;

import com.google.common.collect.Lists;
import net.luis.data.xml.config.XmlConfig;
import net.luis.data.xml.exception.XmlException;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class XmlElements implements Iterable<XmlElement> {
	
	private final List<String> elementNames = Lists.newArrayList();
	private final List<XmlElement> elements = Lists.newArrayList();
	
	XmlElements() {
	
	}
	
	public @NotNull XmlElements copy() {
		XmlElements elements = new XmlElements();
		for (XmlElement element : this.elements) {
			elements.add(element.copy());
		}
		return elements;
	}
	
	public boolean has(String name) {
		return this.elementNames.contains(name);
	}
	
	public boolean has(String prefix, String name) {
		return this.has(prefix + ":" + name);
	}
	
	public boolean has(XmlElement element) {
		return this.elements.contains(element);
	}
	
	//region Adders
	public void add(XmlElement element) {
		Objects.requireNonNull(element, "Element must not be null");
		this.elementNames.add(element.getName());
		this.elements.add(element);
	}
	
	public void add(String name, String value) {
		this.add(new XmlElement(name, value));
	}
	
	public void add(String prefix, String name, String value) {
		this.add(new XmlElement(prefix, name, value));
	}
	//endregion
	
	//region Getters
	public List<XmlElement> getAll(String name) {
		List<XmlElement> elements = Lists.newArrayList();
		for (XmlElement element : this.elements) {
			if (element.getName().equals(name)) {
				elements.add(element);
			}
		}
		return elements;
	}
	
	public XmlElement get(String name) {
		List<XmlElement> elements = this.getAll(name);
		if (elements.size() > 1) {
			throw new XmlException("Multiple elements where found for: " + name);
		}
		return elements.isEmpty() ? null : elements.get(0);
	}
	
	public List<XmlElement> getAll(String prefix, String name) {
		return this.getAll(prefix + ":" + name);
	}
	
	public XmlElement get(String prefix, String name) {
		return this.get(prefix + ":" + name);
	}
	
	public List<XmlElement> getAttributes() {
		return List.copyOf(this.elements);
	}
	//endregion
	
	public int size() {
		return this.elements.size();
	}
	
	public boolean isEmpty() {
		return this.elements.isEmpty();
	}
	
	public boolean remove(String name) {
		this.elementNames.remove(name);
		return this.elements.remove(this.get(name));
	}
	
	public @NotNull Iterator<XmlElement> iterator() {
		return this.elements.iterator();
	}
	
	public @NotNull String toString(XmlConfig config) {
		
		return null;
	}
	
	//region Object overrides
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof XmlElements elements1)) return false;
		
		return this.elements.equals(elements1.elements);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.elements);
	}
	
	@Override
	public String toString() {
		return "XmlElements" + this.elements;
	}
	//endregion
}