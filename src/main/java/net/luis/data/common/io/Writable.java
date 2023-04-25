package net.luis.data.common.io;

import java.io.File;

/**
 *
 * @author Luis-St
 *
 */

public interface Writable<T extends Writer> {
	
	/**
	 * Writes the object to the given file
	 * @param file The file to write to
	 */
	void write(File file);
	
	/**
	 * Writes the object to the given writer
	 * @param writer The writer to write to
	 */
	void write(T writer);
}
