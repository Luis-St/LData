package net.luis.data.json.io;

import net.luis.data.common.io.AbstractWriter;
import net.luis.data.json.JsonObject;
import net.luis.data.json.config.JsonConfig;

import java.io.File;
import java.util.Objects;

public class JsonWriter extends AbstractWriter<JsonConfig> {
	
	public JsonWriter(String file) {
		super(file, JsonConfig.DEFAULT);
	}
	
	public JsonWriter(File file) {
		super(file, JsonConfig.DEFAULT);
	}
	
	public JsonWriter(String file, JsonConfig config) {
		super(file, config);
	}
	
	public JsonWriter(File file, JsonConfig config) {
		super(file, config);
	}
	
	@Override
	protected boolean isExtensionNotAllowed(String extension) {
		return extension == null || !extension.equals("json");
	}
	
	public void write(JsonObject object) {
		String json = Objects.requireNonNull(object, "Json object must not be null").toString(this.config);
		try {
			this.writer.write(this.removeLastIndent(json) + System.lineSeparator());
		} catch (Exception e) {
			throw new RuntimeException("Failed to write json object to file", e);
		}
	}
	
	private String removeLastIndent(String json) {
		return this.config.prettyPrint() ? json.substring(0, json.length() - this.config.indent().length() - 1) + "}" : json;
	}
}
