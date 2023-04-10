package net.luis.data.properties;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.luis.data.common.io.FileHelper;
import net.luis.utils.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

/**
 *
 * @author Luis-St
 *
 */

public class Properties implements Iterable<Property> {
	
	private static final Logger LOGGER = LogManager.getLogger(Properties.class);
	
	private final Set<Property> properties;
	
	public Properties() {
		this.properties = Sets.newTreeSet();
	}
	
	public Properties(@NotNull List<Property> properties) {
		this.properties = Sets.newTreeSet(properties);
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
		if (Character.isWhitespace(delimiter)) {
			throw new IllegalArgumentException("Delimiter cannot be a whitespace character");
		}
		if (delimiter == '#') {
			throw new IllegalArgumentException("Delimiter cannot be the comment character");
		}
		//endregion
		this.properties = read(file, delimiter);
	}
	
	private static @NotNull Set<Property> read(File file, char delimiter) {
		Set<Property> properties = Sets.newTreeSet();
		for (String line : FileHelper.readLines(file)) {
			if (line.startsWith("#")) {
				continue;
			}
			String[] split = line.split(String.valueOf(delimiter));
			if (split.length == 2) {
				properties.add(new Property(split[0].strip(), split[1].strip()));
			} else {
				LOGGER.warn("Invalid property in line '{}'", line);
			}
		}
		return properties;
	}
	
	//region Getters
	public Property getProperty(String key) {
		for (Property property : this.properties) {
			if (property.getKey().equals(key)) {
				return property;
			}
		}
		return null;
	}
	
	public List<Property> getProperties() {
		return new ArrayList<>(this.properties);
	}
	
	public Property getProperty(String object, String key) {
		return this.getProperty(object + "." + key);
	}
	
	public Properties getProperties(String object) {
		List<Property> properties = Lists.newArrayList();
		for (Property property : this.properties.stream().filter(property -> property.getKey().startsWith(object + ".")).toList()) {
			properties.add(new Property(property.getKey().substring(object.length() + 1), property.get()));
		}
		return new Properties(properties);
	}
	//endregion
	
	//region Adders
	public void addProperty(Property property) {
		this.properties.add(property);
	}
	
	public void addProperty(String object, @NotNull Property property) {
		this.addProperty(new Property(object + "." + property.getKey(), property.get()));
	}
	
	public void addProperties(@NotNull List<Property> properties) {
		properties.forEach(this::addProperty);
	}
	
	public void addProperties(@NotNull Properties properties) {
		this.addProperties(properties.getProperties());
	}
	
	public void addProperties(String object, @NotNull List<Property> properties) {
		properties.forEach(property -> {
			if (property.getKey().startsWith(object + ".")) {
				this.addProperty(property);
			} else {
				this.addProperty(new Property(object + "." + property.getKey(), property.get()));
			}
		});
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
		return Lists.newArrayList(this.properties).toString();
	}
	
	public String toShortString() {
		return Utils.mapList(Lists.newArrayList(this.properties), Property::toShortString).toString();
	}
	//endregion
}
