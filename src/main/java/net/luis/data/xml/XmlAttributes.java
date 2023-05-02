package net.luis.data.xml;

import com.google.common.collect.Maps;
import net.luis.data.xml.config.XmlConfig;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * XmlAttributes is a collection of {@link XmlAttribute XmlAttributes}
 *
 * @see XmlAttribute
 *
 * @author Luis-St
 */

public final class XmlAttributes implements Iterable<XmlAttribute> {
	
	private final Map<String, XmlAttribute> attributes = Maps.newHashMap();
	
	@ApiStatus.Internal
	XmlAttributes() {
		super();
	}
	
	/**
	 * @return A copy of the xml attribute collection
	 */
	public @NotNull XmlAttributes copy() {
		XmlAttributes attributes = new XmlAttributes();
		for (Map.Entry<String, XmlAttribute> entry : this.attributes.entrySet()) {
			attributes.add(entry.getValue().copy());
		}
		return attributes;
	}
	
	/**
	 * Checks if the xml attribute collection contains an attribute with the given name
	 * @param name The name of the attribute
	 * @return True if the xml attribute collection contains an attribute with the given name
	 */
	public boolean has(String name) {
		return this.attributes.containsKey(name);
	}
	
	/**
	 * Checks if the xml attribute collection contains the given xml attribute
	 * @param attribute The attribute to check
	 * @return True if the xml attribute collection contains the given xml attribute
	 */
	public boolean has(XmlAttribute attribute) {
		return this.attributes.containsValue(attribute);
	}
	
	//region Adders
	
	/**
	 * Adds the given attribute to the attributes
	 * @param attribute The attribute to add
	 * @throws NullPointerException If the given attribute is null
	 */
	public void add(XmlAttribute attribute) {
		Objects.requireNonNull(attribute, "Attribute must not be null");
		this.attributes.put(attribute.getName(), attribute);
	}
	
	/**
	 * Adds a new attribute with the given name and value to the attributes
	 * @param name The name of the attribute
	 * @param value The value of the attribute as a boolean
	 */
	public void add(String name, boolean value) {
		this.add(new XmlAttribute(name, value));
	}
	
	/**
	 * Adds a new attribute with the given name and value to the attributes
	 * @param name The name of the attribute
	 * @param value The value of the attribute as a number
	 */
	public void add(String name, Number value) {
		this.add(new XmlAttribute(name, value));
	}
	
	/**
	 * Adds a new attribute with the given name and value to the attributes
	 * @param name The name of the attribute
	 * @param value The value of the attribute as a string
	 */
	public void add(String name, String value) {
		this.add(new XmlAttribute(name, value));
	}
	//endregion
	
	//region Getters
	
	/**
	 * Gets the xml attribute for the given name
	 * @param name The name of the attribute
	 * @return The attribute with the given name
	 * @throws NullPointerException If there is no attribute with the given name
	 */
	public XmlAttribute get(String name) {
		return this.attributes.get(name);
	}
	
	/**
	 * @return A immutable list of the xml attribute collection
	 */
	public @NotNull List<XmlAttribute> getAttributes() {
		return List.copyOf(this.attributes.values());
	}
	//endregion
	
	/**
	 * @return The number of xml attribute collection
	 */
	public int size() {
		return this.attributes.size();
	}
	
	/**
	 * @return True if the xml attribute collection is empty
	 */
	public boolean isEmpty() {
		return this.attributes.isEmpty();
	}
	
	/**
	 * Removes the xml attribute with the given name if it exists from the xml attribute collection
	 * @param name The name of the attribute
	 * @return The removed attribute
	 */
	public XmlAttribute remove(String name) {
		return this.attributes.remove(name);
	}
	
	@Override
	public @NotNull Iterator<XmlAttribute> iterator() {
		return this.attributes.values().iterator();
	}
	
	/**
	 * Converts the xml attribute collection to a string
	 * @param config The xml config to use
	 * @return The xml attribute collection as a string
	 */
	public @NotNull String toString(XmlConfig config) {
		if (this.isEmpty()) {
			return "";
		}
		Objects.requireNonNull(config, "Xml config must not be null");
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
