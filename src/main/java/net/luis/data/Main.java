package net.luis.data;

import net.luis.data.json.JsonElement;
import net.luis.data.json.io.JsonReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 *
 * @author Luis-St
 *
 */

public class Main {
	
	private static final Logger LOGGER = LogManager.getLogger(Main.class);
	
	public static void main(String[] args) {
		JsonReader reader = new JsonReader(new File("src/main/resources/test.json"));
		System.out.println(reader);
		for (JsonElement element : reader) {
			System.out.println(element.getClass().getSimpleName() + ": " + element);
		}
		reader.reset();
		System.out.println(reader.toJsonObject().toJsonString());
	}
	
}
