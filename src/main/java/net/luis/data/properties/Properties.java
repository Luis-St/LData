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
 *
 * @author Luis-St
 *
 */

public class Properties implements Iterable<Property>, Writable<PropertyWriter> {
	
	private final List<Property> properties;
	
	public Properties() {
		this.properties = Lists.newArrayList();
	}
	
	public Properties(Property... properties) {
		this.properties = Arrays.asList(properties);
	}
	
	public boolean isEmpty() {
		return this.properties.isEmpty();
	}
	
	public boolean contains(Property property) {
		return this.properties.contains(property);
	}
	
	public boolean containsKey(String key) {
		return this.properties.stream().anyMatch(property -> property.getKey().equals(key));
	}
	
	public List<Property> getProperties() {
		return this.properties;
	}
	
	public Property get(String key) {
		return this.properties.stream().filter(property -> property.getKey().equals(key)).findFirst().orElse(null);
	}
	
	public Properties getFor(String object) {
		String normalizedObject = this.normalizeObject(object).toLowerCase();
		Properties properties = new Properties();
		for (ObjectProperty property : this.properties.stream().filter(Property::isObject).map(Property::getAsObject).toList()) {
			String key = property.getKey();
			String objects = key.substring(0, key.lastIndexOf('.')).toLowerCase();
			if (objects.contains(normalizedObject)) {
				properties.add(property.getInnerFrom(normalizedObject));
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
	
	public void remove(String key) {
		this.properties.removeIf(property -> property.getKey().equals(key));
	}
	
	public void remove(Property property) {
		this.properties.remove(property);
	}
	
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
		this.properties.forEach(writer::write);
	}
}
