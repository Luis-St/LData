package net.luis.data.json.io;

import net.luis.data.common.io.Writer;
import net.luis.data.json.JsonObject;
import net.luis.data.json.config.JsonConfig;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class JsonWriter implements Writer {
	
	private final JsonConfig config;
	private final BufferedWriter writer;
	
	public JsonWriter(String file) {
		this(new File(file), JsonConfig.DEFAULT);
	}
	
	public JsonWriter(File file) {
		this(file, JsonConfig.DEFAULT);
	}
	
	public JsonWriter(String file, JsonConfig config) {
		this(new File(file), config);
	}
	
	public JsonWriter(File file, JsonConfig config) {
		this.config = config;
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
		if (!file.canWrite()) {
			throw new IllegalArgumentException("File cannot be written to");
		}
		if (!"json".equals(FilenameUtils.getExtension(file.getName())) && !this.config.allowCustomExtensions()) {
			throw new IllegalArgumentException("File extension is not allowed");
		}
		//endregion
		try {
			this.writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile(), false));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void write(@NotNull JsonObject object) {
		String json = object.toJson(this.config);
		try {
			this.writer.write(this.removeLastIndent(json) + System.lineSeparator());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private String removeLastIndent(String json) {
		if (this.config.prettyPrint()) {
			return json.substring(0, json.length() - this.config.indent().length() - 1) + "}";
		}
		return json;
	}
	
	//region IO operations
	@Override
	public void flush() {
		try {
			this.writer.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void close() {
		try {
			this.writer.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	//endregion
}
