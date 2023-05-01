package net.luis.data;

import net.luis.data.json.Json;
import net.luis.data.json.JsonObject;
import net.luis.data.json.config.JsonConfig;
import net.luis.data.json.io.JsonReader;
import net.luis.data.json.io.JsonWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class JsonTest {
	
	public static void main(String[] args) throws IOException {
		JsonReader reader = new JsonReader(new File("src/main/resources/test.json"));
		System.out.println(reader);
		for (Json element : reader) {
			System.out.println(element.getClass().getSimpleName() + ": " + element);
		}
		reader.reset();
		File file = new File("test.json");
		if (!Files.exists(file.toPath())) {
			Files.createFile(file.toPath());
		}
		JsonWriter writer = new JsonWriter(file, JsonConfig.DEFAULT);
		writer.write((JsonObject) reader.toJson());
		writer.flushAndClose();
	}
}
