package net.luis.data.common.io;

import net.luis.data.common.config.DataConfig;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Objects;

/**
 * An abstract implementation of {@link Writer writer}
 *
 * @author Luis-St
 */

public abstract class AbstractWriter<T extends DataConfig> implements Writer {
	
	protected final T config;
	protected final BufferedWriter writer;
	
	protected AbstractWriter(String file, T config) {
		this(new File(file), config);
	}
	
	protected AbstractWriter(File file, T config) {
		this.config = config;
		//region Validation
		Objects.requireNonNull(file, "File cannot be null");
		if (file.isDirectory()) {
			throw new IllegalArgumentException("File cannot be a directory");
		}
		if (!file.exists()) {
			throw new IllegalArgumentException("File does not exist");
		}
		if (!file.canWrite()) {
			throw new IllegalArgumentException("File cannot be written to");
		}
		if (this.isExtensionNotAllowed(FilenameUtils.getExtension(file.getName())) && !config.allowCustomExtensions()) {
			throw new IllegalArgumentException("File extension is not allowed in this configuration");
		}
		//endregion
		try {
			this.writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile(), config.allowAppend()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		this.init();
	}
	
	/**
	 * Checks if the extension is allowed in this configuration
	 * @param extension The extension to check
	 * @return True if the extension is not allowed, false otherwise
	 */
	protected abstract boolean isExtensionNotAllowed(String extension);
	
	/**
	 * Called after the writer has been initialized
	 */
	protected void init() {
	
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
