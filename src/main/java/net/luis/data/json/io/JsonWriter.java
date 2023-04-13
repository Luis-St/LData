package net.luis.data.json.io;

import net.luis.data.common.io.AbstractWriter;
import net.luis.data.json.JsonObject;
import net.luis.data.json.config.JsonConfig;
import org.jetbrains.annotations.NotNull;

import java.io.File;

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
	
	public void write(@NotNull JsonObject object) {
		String json = object.toString(this.config);
		try {
			this.writer.write(this.removeLastIndent(json) + System.lineSeparator());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private String removeLastIndent(String json) {
		return this.config.prettyPrint() ? json.substring(0, json.length() - this.config.indent().length() - 1) + "}" : json;
	}
}
