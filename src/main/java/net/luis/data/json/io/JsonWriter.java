package net.luis.data.json.io;

import net.luis.data.common.io.AbstractWriter;
import net.luis.data.json.JsonObject;
import net.luis.data.json.config.JsonConfig;

import java.io.File;
import java.util.Objects;

/**
 * A writer for {@link JsonObject JsonObjects} to write them to a file
 *
 * @see JsonObject
 * @see AbstractWriter
 *
 * @author Luis-St
 */

public class JsonWriter extends AbstractWriter<JsonConfig> {
	
	/**
	 * Constructs a new {@link JsonWriter} with the given file using the {@link JsonConfig#DEFAULT}
	 * @param file The file to write to as a string
	 */
	public JsonWriter(String file) {
		super(file, JsonConfig.DEFAULT);
	}
	
	/**
	 * Constructs a new {@link JsonWriter} with the given file using the {@link JsonConfig#DEFAULT}
	 * @param file The file to write to
	 */
	public JsonWriter(File file) {
		super(file, JsonConfig.DEFAULT);
	}
	
	/**
	 * Constructs a new {@link JsonWriter} with the given file and config
	 * @param file The file to write to as a string
	 * @param config The config to use
	 */
	public JsonWriter(String file, JsonConfig config) {
		super(file, config);
	}
	
	/**
	 * Constructs a new {@link JsonWriter} with the given file and config
	 * @param file The file to write to
	 * @param config The config to use
	 */
	public JsonWriter(File file, JsonConfig config) {
		super(file, config);
	}
	
	@Override
	protected boolean isExtensionNotAllowed(String extension) {
		return extension == null || !extension.equals("json");
	}
	
	/**
	 * Writes the given {@link JsonObject} to the file
	 * @param object The object to write
	 * @throws NullPointerException If the given object is null
	 * @throws RuntimeException If an error occurs while writing the object to the file
	 */
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
