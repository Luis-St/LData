package net.luis.data;

import net.luis.data.properties.ObjectProperty;
import net.luis.data.properties.Properties;
import net.luis.data.properties.Property;
import net.luis.data.properties.config.PropertyConfig;
import net.luis.data.properties.io.PropertyReader;
import net.luis.data.properties.io.PropertyWriter;
import net.luis.data.properties.PropertyString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class PropertyTest {
	
	private static final Logger LOGGER = LogManager.getLogger(PropertyTest.class);
	
	public static void main(String[] args) throws IOException {
		PropertyReader reader = new PropertyReader(new File("src/main/resources/test.properties"), '=');
		for (Property property : reader) {
			if (property instanceof ObjectProperty object) {
				Property actual = object.getActual();
				LOGGER.info("ObjectProperty with getActual {}: {}", actual.getClass().getSimpleName(), actual);
			} else {
				LOGGER.info("{}: {}", property.getClass().getSimpleName(), property);
			}
		}
		System.out.println();
		reader.reset();
		Properties properties = reader.toProperties();
		for (Property property : properties.getFor("db")) {
			LOGGER.info("db {}: {}", property.getClass().getSimpleName(), property);
		}
		System.out.println();
		for (Property property : properties.getFor("db.net")) {
			LOGGER.info("db.net {}: {}", property.getClass().getSimpleName(), property);
		}
		System.out.println();
		for (Property property : properties.getFor(".user.")) {
			LOGGER.info(".user. {}: {}", property.getClass().getSimpleName(), property);
		}
		System.out.println();
		for (Property property : properties.getFor("user.name.")) {
			LOGGER.info("user.name. {}: {}", property.getClass().getSimpleName(), property);
		}
		properties.remove("log");
		File file = new File("test.properties");
		if (!Files.exists(file.toPath())) {
			Files.createFile(file.toPath());
		}
		PropertyWriter writer = new PropertyWriter(file, PropertyConfig.DEFAULT);
		writer.writeComment("# Set the database connection settings");
		properties.write(writer);
		writer.writeComment("# Set the log level");
		writer.write(new PropertyString("log.level", "INFO"));
		writer.flushAndClose();
	}
	
}
