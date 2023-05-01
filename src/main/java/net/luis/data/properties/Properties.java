package net.luis.data.properties;

import com.google.common.collect.Lists;
import net.luis.data.common.io.Writable;
import net.luis.data.properties.io.PropertyWriter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * A collection of {@link Property} objects
 *
 * @author Luis-St
 */

public class Properties implements Iterable<Property>, Writable<PropertyWriter> {
	
	private final List<Property> properties;
	
	/**
	 * Constructs a new {@link Properties properties} collection
	 */
	public Properties() {
		this.properties = Lists.newArrayList();
	}
	
	/**
	 * Constructs a new {@link Properties properties} collection with the given {@link Property properties}
	 * @param properties The properties to add
	 */
	public Properties(Property... properties) {
		this.properties = Arrays.asList(properties);
	}
	
	/**
	 * @return True if the properties collection is empty
	 */
	public boolean isEmpty() {
		return this.properties.isEmpty();
	}
	
	/**
	 * Checks if the properties collection contains the given {@link Property property}
	 * @param property The property to check
	 * @return True if the properties collection contains the given property
	 */
	public boolean contains(Property property) {
		return this.properties.contains(property);
	}
	
	/**
	 * Checks if the properties collection contains a property with the given key
	 * @param key The key to check
	 * @return True if the properties collection contains a property with the given key
	 */
	public boolean containsKey(String key) {
		return this.properties.stream().anyMatch(property -> property.getKey().equals(key));
	}
	
	/**
	 * @return The properties collection as an unmodifiable list
	 */
	public List<Property> getProperties() {
		return List.copyOf(this.properties);
	}
	
	/**
	 * Gets the property with the given key
	 * @param key The key to get the property for
	 * @return The property with the given key or null if no property with the given key exists
	 */
	public Property get(String key) {
		return this.properties.stream().filter(property -> property.getKey().equals(key)).findFirst().orElse(null);
	}
	
	/**
	 * Gets the properties from the given object as a {@link Properties properties} collection.
	 * The object can be a single object or a path to an object separated by dots:
	 * <ul>
	 *     <li>foo - Gets all properties which contains 'foo' in their {@link ObjectProperty#getPath() path} e.g.:<br>
	 *     <ul>
	 *         <li>foo.{@literal <property_key>}</li>
	 *         <li>foo.bar.{@literal <property_key>}</li>
	 *         <li>baz.foo.{@literal <property_key>}</li>
	 *     </ul></li>
	 *     <li>.foo - Gets all properties where 'foo' is a sub object e.g.:
	 *     <ul>
	 *         <li>bar.foo.{@literal <property_key>}</li>
	 *         <li>baz.foo.bar.{@literal <property_key>}</li>
	 *     </ul></li>
	 * 	   </li>
	 *     <li>foo. - Gets all properties for 'foo' where 'foo' contains at least one sub object e.g.:
	 *     <ul>
	 *         <li>foo.bar.{@literal <property_key>}</li>
	 *         <li>bar.foo.baz.{@literal <property_key>}</li>
	 *     </ul></li>
	 *     <li>.foo. - Gets all properties where 'foo' is a sub object and where 'foo' contains at least one sub object e.g.:<br>
	 *     <ul>
	 *         <li>bar.foo.baz.{@literal <property_key>}</li>
	 *         <li>baz.foo.bar.{@literal <property_key>}</li>
	 *     </ul></li>
	 * </ul>
	 * @param object The object to get the properties for
	 * @return The properties from the given object as a {@link Properties properties} collection
	 */
	public Properties getFor(String object) {
		String lowerObject = Objects.requireNonNull(object, "Object must not be null").toLowerCase();
		Properties properties = new Properties();
		for (ObjectProperty property : this.properties.stream().filter(Property::isObject).map(Property::getAsObject).toList()) {
			String key = property.getKey();
			if (!key.contains(lowerObject)) {
				continue;
			}
			if (key.equals(lowerObject)) {
				throw new IllegalArgumentException("Object '" + object + "' refers to a value and not to an object");
			}
			if ((property.getPath() + ".").contains(lowerObject)) {
				properties.add(property.getInnerFrom(this.normalizeObject(object)));
			}
		}
		return properties;
	}
	
	private @NotNull String normalizeObject(String object) {
		Objects.requireNonNull(object, "Object must not be null");
		char first = object.charAt(0);
		char last = object.charAt(object.length() - 1);
		if (first == '.' && last == '.') {
			return object.substring(1, object.length() - 1);
		} else if (first == '.') {
			return object.substring(1);
		} else if (last == '.') {
			return object.substring(0, object.length() - 1);
		}
		return object;
	}
	
	/**
	 * Removes the property with the given key
	 * @param key The key to remove the property for
	 */
	public void remove(String key) {
		this.properties.removeIf(property -> property.getKey().equals(key));
	}
	
	/**
	 * Removes the given {@link Property property} from the properties collection
	 * @param property The property to remove
	 */
	public void remove(Property property) {
		this.properties.remove(property);
	}
	
	/**
	 * Adds the given {@link Property property} to the properties collection
	 * @param property The property to add
	 * @throws NullPointerException If the given property is null
	 * @throws IllegalArgumentException If a property with the same key already exists
	 */
	public void add(Property property) {
		Objects.requireNonNull(property, "Property must not be null");
		if (this.containsKey(property.getKey())) {
			throw new IllegalArgumentException("Property with key " + property.getKey() + " already exists");
		}
		this.properties.add(property);
	}
	
	@Override
	public @NotNull Iterator<Property> iterator() {
		return this.properties.iterator();
	}
	
	@Override
	public void write(File file) {
		PropertyWriter writer = new PropertyWriter(file);
		this.write(writer);
		writer.flushAndClose();
	}
	
	@Override
	public void write(PropertyWriter writer) {
		Objects.requireNonNull(writer, "Writer must not be null");
		this.properties.forEach(writer::write);
	}
}
