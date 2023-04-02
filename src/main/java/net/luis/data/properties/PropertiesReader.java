package net.luis.data.properties;

import com.google.common.collect.Maps;
import net.luis.data.common.FileHelper;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author Luis-St
 *
 */

public class PropertiesReader {
	
	private final Map<String, String> properties;
	
	public PropertiesReader(String file) {
		this(new File(file), '=');
	}
	
	public PropertiesReader(File file) {
		this(file, '=');
	}
	
	public PropertiesReader(String file, char delimiter) {
		this(new File(file), delimiter);
	}
	
	public PropertiesReader(File file, char delimiter) {
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
		if (!file.canRead()) {
			throw new IllegalArgumentException("File cannot be read");
		}
		//endregion
		this.properties = read(file, delimiter);
	}
	
	private static @NotNull Map<String, String> read(File file, char delimiter) {
		Map<String, String> properties = Maps.newHashMap();
		for (String line : FileHelper.readLines(file)) {
			if (line.startsWith("#")) {
				continue;
			}
			String[] split = line.split(String.valueOf(delimiter));
			if (split.length == 2) {
				properties.put(split[0], split[1]);
			}
		}
		return properties;
	}
	
	//region Read properties
	public Map<String, String> readProperties() {
		return this.properties;
	}
	//endregion
	
	//region Read overloads
	public int readInt(String key) {
		return Integer.parseInt(this.readProperty(key));
	}
	
	public long readLong(String key) {
		return Long.parseLong(this.readProperty(key));
	}
	
	public double readDouble(String key) {
		return Double.parseDouble(this.readProperty(key));
	}
	
	public boolean readBoolean(String key) {
		return Boolean.parseBoolean(this.readProperty(key));
	}
	
	public String[] readStringArray(String key) {
		return this.readProperty(key).replace("[", "").replace("]", "").strip().split(",");
	}
	
	public int[] readIntArray(String key) {
		return Arrays.stream(this.readStringArray(key)).mapToInt(Integer::parseInt).toArray();
	}
	
	public long[] readLongArray(String key) {
		return Arrays.stream(this.readStringArray(key)).mapToLong(Long::parseLong).toArray();
	}
	
	public double[] readDoubleArray(String key) {
		return Arrays.stream(this.readStringArray(key)).mapToDouble(Double::parseDouble).toArray();
	}
	
	public boolean[] readBooleanArray(String key) {
		String[] stringArray = this.readStringArray(key);
		boolean[] booleanArray = new boolean[stringArray.length];
		for (int i = 0; i < stringArray.length; i++) {
			booleanArray[i] = Boolean.parseBoolean(stringArray[i]);
		}
		return booleanArray;
	}
	//endregion
	
	public String readProperty(String key) {
		return this.properties.get(key);
	}
	
	public void close(){
		this.properties.clear();
	}
}
