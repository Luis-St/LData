package net.luis.data.properties;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.luis.data.common.io.FileHelper;
import net.luis.utils.util.Utils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author Luis-St
 *
 */

public class Properties implements Iterable<Property> {
	
	private final Map<String, Property> properties;
	
	public Properties() {
		this.properties = Maps.newTreeMap();
	}
	
	public Properties(@NotNull List<Property> properties) {
		this.properties = new TreeMap<>(properties.stream().collect(Collectors.toMap(Property::getKey, property -> property)));
	}
	
	public Properties(File file, char delimiter) {
		//region Validation
		if (file == null) {
			throw new IllegalArgumentException("File cannot be null");
		}
		if (file.isDirectory()) {
			throw new IllegalArgumentException("File cannot be a directory");
		}
		if (!file.exists()) {
			throw new IllegalArgumentException("File does not exist");
		}
		if (!file.canRead()) {
			throw new IllegalArgumentException("File cannot be read");
		}
		//endregion
		this.properties = read(file, delimiter);
	}
	
	private static @NotNull Map<String, Property> read(File file, char delimiter) {
		Map<String, Property> properties = Maps.newTreeMap();
		for (String line : FileHelper.readLines(file)) {
			if (line.startsWith("#")) {
				continue;
			}
			String[] split = line.split(String.valueOf(delimiter));
			if (split.length == 2) {
				String key = split[0].strip();
				properties.put(key, new Property(key, split[1].strip()));
			}
		}
		return properties;
	}
	
	//region Getters
	public Property getProperty(String key) {
		return this.properties.get(key);
	}
	
	public List<Property> getProperties() {
		return new ArrayList<>(this.properties.values());
	}
	
	public Property getProperty(String object, String key) {
		return this.getProperty(object + "." + key);
	}
	
	public Properties getPropertiesFor(String object) {
		List<Property> properties = Lists.newArrayList();
		for (Map.Entry<String, Property> entry : this.properties.entrySet().stream().filter(entry -> entry.getKey().startsWith(object + ".")).toList()) {
			properties.add(new Property(entry.getKey().substring(object.length() + 1), entry.getValue().get()));
		}
		return new Properties(properties);
	}
	//endregion
	
	//region Adders
	public void addProperty(Property property) {
		this.properties.put(property.getKey(), property);
	}
	
	public void addProperties(@NotNull List<Property> properties) {
		properties.forEach(this::addProperty);
	}
	
	public void addProperties(@NotNull Properties properties) {
		this.addProperties(properties.getProperties());
	}
	
	public void addProperties(String object, @NotNull Property property) {
		this.addProperty(new Property(object + "." + property.getKey(), property.get()));
	}
	
	public void addProperties(String object, @NotNull List<Property> properties) {
		properties.forEach(property -> this.addProperty(new Property(object + "." + property.getKey(), property.get())));
	}
	
	public void addProperties(String object, @NotNull Properties properties) {
		this.addProperties(object, properties.getProperties());
	}
	//endregion
	
	@Override
	public @NotNull Iterator<Property> iterator() {
		return this.getProperties().iterator();
	}
	
	public void clear() {
		this.properties.clear();
	}
	
	//region Object overrides
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Properties that)) return false;
		
		return this.properties.equals(that.properties);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.properties);
	}
	
	@Override
	public String toString() {
		return Utils.mapValue(this.properties, Property::get).toString();
	}
	
	public String toShortString() {
		return this.properties.values().stream().map(Property::toShortString).toList().toString();
	}
	//endregion
}
