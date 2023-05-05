package net.luis.data.internal.io;

/**
 * An interface for writing data to a file
 *
 * @author Luis-St
 */

public interface Writer {
	
	/**
	 * Flushes the writer
	 */
	void flush();
	
	/**
	 * Closes the writer
	 */
	void close();
	
	/**
	 * Flushes and closes the writer
	 */
	default void flushAndClose() {
		this.flush();
		this.close();
	}
}
