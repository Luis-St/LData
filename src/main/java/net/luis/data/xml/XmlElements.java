package net.luis.data.xml;

import com.google.common.collect.Lists;
import net.luis.data.xml.exception.XmlException;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * XmlElements is a collection of {@link XmlElement XmlElements}
 *
 * @author Luis-St
 */

public final class XmlElements implements Iterable<XmlElement> {
	
	private final List<String> elementNames = Lists.newArrayList();
	private final List<XmlElement> elements = Lists.newArrayList();
	
	@ApiStatus.Internal
	XmlElements() {
		super();
	}
	
	/**
	 * @return A copy of the xml element collection
	 */
	public @NotNull XmlElements copy() {
		XmlElements elements = new XmlElements();
		for (XmlElement element : this.elements) {
			elements.add(element.copy());
		}
		return elements;
	}
	
	/**
	 * Checks if the xml element collection contains an element with the given name
	 * @param name The name of the element
	 * @return True if the xml element collection contains an element with the given name
	 */
	public boolean has(String name) {
		return this.elementNames.contains(name);
	}
	
	/**
	 * Checks if the xml element collection contains the given xml element
	 * @param element The element to check
	 * @return True if the xml element collection contains the given xml element
	 */
	public boolean has(XmlElement element) {
		return this.elements.contains(element);
	}
	
	//region Adders
	
	/**
	 * Adds the given xml element to the xml element collection
	 * @param element The xml element to add
	 * @throws NullPointerException If the given xml element is null
	 */
	public void add(XmlElement element) {
		Objects.requireNonNull(element, "Xml element must not be null");
		this.elementNames.add(element.getName());
		this.elements.add(element);
	}
	
	/**
	 * Adds a new xml element with the given name and value to the xml element collection
	 * @param name The name of the xml element
	 * @param value The value of the xml element
	 */
	public void add(String name, String value) {
		this.add(new XmlElement(name, value));
	}
	//endregion
	
	//region Getters
	
	/**
	 * Gets all xml elements with the given name
	 * @param name The name of the xml elements
	 * @return A list of xml elements with the given name
	 */
	public @NotNull List<XmlElement> getAll(String name) {
		List<XmlElement> elements = Lists.newArrayList();
		for (XmlElement element : this.elements) {
			if (element.getName().equals(name)) {
				elements.add(element);
			}
		}
		return elements;
	}
	
	/**
	 * Gets the first xml element with the given name
	 * @param name The name of the xml element
	 * @return The first xml element with the given name
	 * @throws XmlException If multiple xml elements where found for the given name
	 */
	public XmlElement get(String name) {
		List<XmlElement> elements = this.getAll(name);
		if (elements.size() > 1) {
			throw new XmlException("Multiple xml elements where found for name: " + name);
		}
		return elements.isEmpty() ? null : elements.get(0);
	}
	
	/**
	 * @return A unmodifiable list of all xml elements
	 */
	public @NotNull List<XmlElement> getElements() {
		return List.copyOf(this.elements);
	}
	//endregion
	
	/**
	 * @return The size of the xml element collection
	 */
	public int size() {
		return this.elements.size();
	}
	
	/**
	 * @return True if the xml element collection is empty
	 */
	public boolean isEmpty() {
		return this.elements.isEmpty();
	}
	
	/**
	 * Removes the element for the given name from the xml element collection
	 * @param name The name of the element to remove
	 * @return True if the element was removed successfully
	 */
	public boolean remove(String name) {
		this.elementNames.remove(name);
		return this.elements.remove(this.get(name));
	}
	
	@Override
	public @NotNull Iterator<XmlElement> iterator() {
		return this.elements.iterator();
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
