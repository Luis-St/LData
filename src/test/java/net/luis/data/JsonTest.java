package net.luis.data;

import net.luis.data.json.Json;
import net.luis.data.json.config.JsonConfig;
import net.luis.data.json.io.JsonReader;
import net.luis.data.json.io.JsonWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Files;

public class JsonTest {
	
	private static final Logger LOGGER = LogManager.getLogger(Main.class);
	
	public static void main(String[] args) throws Exception {
		JsonReader reader = new JsonReader(new File("src/main/resources/test.json"));
		LOGGER.info(reader);
		for (Json element : reader) {
			LOGGER.info(element.getClass().getSimpleName() + ": " + element);
		}
		reader.reset();
		File file = new File("test.json");
		if (!Files.exists(file.toPath())) {
			Files.createFile(file.toPath());
		}
		JsonWriter writer = new JsonWriter(new File("test.json"), JsonConfig.DEFAULT);
		writer.write(reader.toJson());
		writer.flushAndClose();
	}
	
}
