package net.luis.data.xml.attributes;

import com.google.common.collect.Maps;
import net.luis.data.xml.config.XmlConfig;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class XmlAttributes implements Iterable<XmlAttribute> {
	
	private final Map<String, XmlAttribute> attributes = Maps.newHashMap();
	
	@ApiStatus.Internal
	public XmlAttributes() {
	
	}
	
	public @NotNull XmlAttributes copy() {
		XmlAttributes attributes = new XmlAttributes();
		for (Map.Entry<String, XmlAttribute> entry : this.attributes.entrySet()) {
			attributes.add(entry.getValue().copy());
		}
		return attributes;
	}
	
	public boolean has(String name) {
		return this.attributes.containsKey(name);
	}
	
	public boolean has(XmlAttribute attribute) {
		return this.attributes.containsValue(attribute);
	}
	
	//region Adders
	public void add(XmlAttribute attribute) {
		Objects.requireNonNull(attribute, "Attribute must not be null");
		this.attributes.put(attribute.getName(), attribute);
	}
	
	public void add(String name, boolean value) {
		this.add(new XmlAttribute(name, value));
	}
	
	public void add(String name, Number value) {
		this.add(new XmlAttribute(name, value));
	}
	
	public void add(String name, String value) {
		this.add(new XmlAttribute(name, value));
	}
	//endregion
	
	//region Getters
	public XmlAttribute get(String name) {
		return this.attributes.get(name);
	}
	
	public List<XmlAttribute> getAttributes() {
		return List.copyOf(this.attributes.values());
	}
	//endregion
	
	public int size() {
		return this.attributes.size();
	}
	
	public boolean isEmpty() {
		return this.attributes.isEmpty();
	}
	
	public XmlAttribute remove(String name) {
		return this.attributes.remove(name);
	}
	
	public @NotNull Iterator<XmlAttribute> iterator() {
		return this.attributes.values().iterator();
	}
	
	public @NotNull String toString(XmlConfig config) {
		if (this.isEmpty()) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		for (XmlAttribute attribute : this) {
			builder.append(" ").append(attribute.toString(config));
		}
		return builder.toString().strip();
	}
	
	//region Object overrides
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof XmlAttributes that)) return false;
		
		return this.attributes.equals(that.attributes);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.attributes);
	}
	
	@Override
	public String toString() {
		return "XmlAttributes" + this.attributes;
	}
	//endregion
}
