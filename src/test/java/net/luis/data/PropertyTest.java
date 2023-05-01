package net.luis.data;

import net.luis.data.properties.ObjectProperty;
import net.luis.data.properties.Properties;
import net.luis.data.properties.Property;
import net.luis.data.properties.PropertyString;
import net.luis.data.properties.config.PropertyConfig;
import net.luis.data.properties.io.PropertyReader;
import net.luis.data.properties.io.PropertyWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class PropertyTest {
	
	public static void main(String[] args) throws IOException {
		PropertyReader reader = new PropertyReader(new File("src/main/resources/test.properties"), '=');
/*		for (Property property : reader) {
			if (property instanceof ObjectProperty object) {
				Property actual = object.getActual();
				System.out.println("ObjectProperty with actual " + actual.getClass().getSimpleName() + ": " + actual);
			} else {
				System.out.println(property.getClass().getSimpleName() + ": " + property);
			}
		}*/
		System.out.println();
		reader.reset();
		Properties properties = reader.toProperties();
		for (Property property : properties.getFor("db")) {
			System.out.println("db " + property.getClass().getSimpleName() + ": " + property);
		}
		System.out.println();
		for (Property property : properties.getFor("db.net")) {
			System.out.println("db.net " + property.getClass().getSimpleName() + ": " + property);
		}
		System.out.println();
		for (Property property : properties.getFor(".user.")) {
			System.out.println(".user. " + property.getClass().getSimpleName() + ": " + property);
		}
		System.out.println();
		for (Property property : properties.getFor(".user.name.")) {
			System.out.println(".user.name. " + property.getClass().getSimpleName() + ": " + property);
		}
		System.out.println();
		for (Property property : properties.getFor(".name")) {
			System.out.println(".name " + property.getClass().getSimpleName() + ": " + property);
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
