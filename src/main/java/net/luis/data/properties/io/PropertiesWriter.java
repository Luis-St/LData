package net.luis.data.properties.io;

import com.google.common.collect.Maps;
import net.luis.data.properties.Properties;
import net.luis.data.properties.Property;
import net.luis.data.properties.config.PropertiesConfig;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Luis-St
 *
 */

public class PropertiesWriter {

	private static final List<String> ALLOWED_EXTENSIONS = List.of(".props", ".properties", ".cfg", ".conf", ".config", ".configuration", ".ini", ".settings", ".prefs", ".preferences");

	private final Map<String, String> writtenProperties = Maps.newHashMap();
	private final PropertiesConfig config;
	private final BufferedWriter writer;

	public PropertiesWriter(String file) {
		this(new File(file), PropertiesConfig.DEFAULT);
	}

	public PropertiesWriter(File file) {
		this(file, PropertiesConfig.DEFAULT);
	}

	public PropertiesWriter(String file, PropertiesConfig config) {
		this(new File(file), config);
	}

	public PropertiesWriter(File file, PropertiesConfig config) {
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
		if (!ALLOWED_EXTENSIONS.contains("." + FilenameUtils.getExtension(file.getName())) && !this.config.allowCustomExtensions()) {
			throw new IllegalArgumentException("File extension is not allowed");
		}
		//endregion
		try {
			this.writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile(), this.config.allowAppend()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void writeComment(String comment) {
		if (this.config.allowComments()) {
			this.write(comment.startsWith("#") ? comment : "# " + comment);
		} else {
			throw new UnsupportedOperationException("Comments are not allowed in this configuration");
		}
	}

	public void write(@NotNull Properties properties) {
		properties.forEach(this::write);
	}

	//region Write overloads
	public void write(String key, int value) {
		this.write(key, String.valueOf(value));
	}

	public void write(String key, long value) {
		this.write(key, String.valueOf(value));
	}

	public void write(String key, double value) {
		this.write(key, String.valueOf(value));
	}

	public void write(String key, boolean value) {
		this.write(key, String.valueOf(value));
	}

	public void write(String key, int... value) {
		this.write(key, Arrays.toString(value));
	}

	public void write(String key, long... value) {
		this.write(key, Arrays.toString(value));
	}

	public void write(String key, double... value) {
		this.write(key, Arrays.toString(value));
	}

	public void write(String key, boolean... value) {
		this.write(key, Arrays.toString(value));
	}

	public void write(String key, String... value) {
		this.write(key, Arrays.toString(value));
	}
	//endregion

	public void write(@NotNull Property property) {
		this.write(property.getKey(), property.get());
	}

	public void write(String key, String value) {
		String trimmedKey = StringUtils.trimToEmpty(key);
		String trimmedValue = StringUtils.trimToEmpty(value);
		//region Validation
		if (this.writtenProperties.containsKey(trimmedKey)) {
			throw new IllegalArgumentException("Property with key " + trimmedKey + " already exists");
		}
		if (this.writtenProperties.containsValue(trimmedValue) && !this.config.allowDuplicateValues()) {
			throw new IllegalArgumentException("Property with value " + trimmedValue + " already exists and the configuration does not allow duplicate values");
		}
		if (StringUtils.isEmpty(trimmedValue) && !this.config.allowEmptyValues()) {
			throw new IllegalArgumentException("Property value cannot be empty because the configuration does not allow it");
		}
		if (StringUtils.contains(trimmedValue, "\\" + this.config.getDelimiter()) && !this.config.allowEscapedDelimiter()) {
			throw new IllegalArgumentException("Property value cannot contain escaped delimiter because the configuration does not allow it");
		}
		//endregion
		if (this.config.prettyPrint()) {
			this.write(trimmedKey + " " + this.config.getDelimiter() + " " + trimmedValue);
		} else {
			this.write(trimmedKey + this.config.getDelimiter() + trimmedValue);
		}
		this.writtenProperties.put(trimmedKey, trimmedValue);
	}

	private void write(String line) {
		try {
			this.writer.write(line + System.lineSeparator());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	//region IO operations
	public void flush() {
		try {
			this.writer.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void close() {
		try {
			this.writtenProperties.clear();
			this.writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void flushAndClose() {
		this.flush();
		this.close();
	}
	//endregion
}
